package com.marquee.rodney;

import java.util.*;
import java.util.concurrent.*;
import org.nd4j.linalg.factory.*;
import org.nd4j.linalg.dataset.*;
import org.deeplearning4j.nn.api.*;
import org.deeplearning4j.ui.api.*;
import org.deeplearning4j.nn.conf.*;
import org.nd4j.linalg.activations.*;
import org.nd4j.linalg.api.ndarray.*;
import org.deeplearning4j.ui.stats.*;
import org.nd4j.linalg.lossfunctions.*;
import org.deeplearning4j.ui.storage.*;
import org.deeplearning4j.nn.weights.*;
import org.deeplearning4j.api.storage.*;
import org.deeplearning4j.nn.multilayer.*;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.*;
import org.nd4j.linalg.dataset.api.iterator.*;
import org.deeplearning4j.datasets.iterator.impl.*;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.*;

public class TriangleArea {

    private final int MAX_SIDE = 10;
    public static int MIN_RANGE = 0;
    public static int MAX_RANGE = 3;

    private final int batchSize;
    private final int numEpochs;

    private final MultiLayerConfiguration nnConf;

    private final MultiLayerNetwork nn;

    private final Map<Double[], Double> data;
    private INDArray areas;

    private DataSetIterator iterator;

    private final UIServer uiServer;
    private final StatsStorage statsStorage;

    public TriangleArea(long seed, double learningRate, double dropOut,
            int batchSize, int numEpochs, int numHiddenLayers, int numHiddenNodes,
            int nIn, int nOut, int iterations) {
        this.data = new HashMap<>();

        this.batchSize = batchSize;
        this.numEpochs = numEpochs;

        ListBuilder conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .optimizationAlgo(OptimizationAlgorithm.CONJUGATE_GRADIENT)
                .learningRate(learningRate)
                .updater(Updater.ADAM)
                .weightInit(WeightInit.XAVIER)
                //.dropOut(dropOut)
                //.regularization(true)
                //.l1(1e-3)
                //.l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(nIn)
                        .nOut(numHiddenNodes)
                        .activation(Activation.TANH)
                        .build()
                );

        for (int i = 1; i < numHiddenLayers; i++) {
            conf.layer(i, new DenseLayer.Builder()
                    .nIn(numHiddenNodes)
                    .nOut(numHiddenNodes)
                    .activation(Activation.TANH)
                    .build()
            );
        }

        conf.layer(numHiddenLayers, new OutputLayer.Builder(LossFunctions.LossFunction.SQUARED_LOSS)
                .nIn(numHiddenNodes)
                .nOut(nOut)
                .activation(Activation.IDENTITY)
                .build()
        );

        this.nnConf = conf.build();

        this.nn = new MultiLayerNetwork(this.nnConf);
        this.nn.init();

        this.uiServer = UIServer.getInstance();
        this.statsStorage = new InMemoryStatsStorage();
        this.uiServer.attach(this.statsStorage);

        this.nn.setListeners(new StatsListener(this.statsStorage));
    }

    public void train() {
        this.iterator.reset();
        System.out.println(this.areas);
        System.out.println(eval());

        for (int i = 0, j = 0; i < this.numEpochs; i++) {
            this.iterator.reset();
            this.nn.fit(this.iterator);
        }

        this.iterator.reset();
        System.out.println(this.areas);
        System.out.println(eval());
    }

    public INDArray eval(double a, double b, double c) {
        INDArray input = Nd4j.create(new double[]{a, b, c}, new int[]{1, 3});

        return this.nn.output(input, true);
    }

    public INDArray eval() {
        return this.nn.output(this.iterator, true);
    }

    public INDArray getAreas() {
        return this.areas;
    }

    public double area(double a, double b, double c) {
        double p = (a + b + c) / 2;

        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public double area(Double[] sides) {
        double p = (sides[0] + sides[1] + sides[2]) / 2;

        return Math.sqrt(p * (p - sides[0]) * (p - sides[1]) * (p - sides[2]));
    }

    public void generateRandomData() {
        this.data.clear();

        Double[] abc = new Double[3];
        for (int i = 0; i < this.batchSize; i++) {
            abc[0] = ThreadLocalRandom.current().nextDouble(1e-1, MAX_SIDE + 1);
            abc[1] = ThreadLocalRandom.current().nextDouble(1e-1, MAX_SIDE + 1);
            abc[2] = ThreadLocalRandom.current().nextDouble(Math.abs(abc[0] - abc[1]), abc[0] + abc[1]);

            this.data.put(abc.clone(), area(abc));
        }

        INDArray sides = Nd4j.create(this.batchSize, 3);
        this.areas = Nd4j.create(this.batchSize, 1);

        Double[][] keys = this.data.keySet().toArray(new Double[0][]);
        Double[] values = this.data.values().toArray(new Double[0]);

        for (int i = 0; i < this.batchSize; i++) {
            sides.put(i, 0, keys[i][0]);
            sides.put(i, 1, keys[i][1]);
            sides.put(i, 2, keys[i][2]);

            this.areas.put(i, 0, values[i]);
        }

        List<DataSet> listDs = new DataSet(sides, this.areas).asList();
        this.iterator = new ListDataSetIterator(listDs, this.batchSize);
    }
}
