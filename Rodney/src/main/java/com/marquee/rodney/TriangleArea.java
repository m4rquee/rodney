package com.marquee.rodney;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.*;
import org.deeplearning4j.nn.conf.distribution.*;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.*;

public class TriangleArea {

    private int batchSize;
    private int numEpochs;

    private MultiLayerConfiguration nnConf;

    public TriangleArea(long seed, double learningRate, double dropOut,
            int batchSize, int numEpochs, int numHiddenLayers, int numHiddenNodes,
            int nIn, int nOut) {
        this.batchSize = batchSize;
        this.numEpochs = numEpochs;

        ListBuilder conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.CONJUGATE_GRADIENT)
                .learningRate(learningRate)
                .updater(Updater.ADAM)
                .dist(new UniformDistribution(-5e-1, 5e-1))
                .dropOut(dropOut)
                .regularization(true)
                .l1(1e-3)
                .l2(1e-6)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(nIn)
                        .nOut(numHiddenNodes)
                        .activation(Activation.RELU)
                        .build()
                );

        for (int i = 1; i <= numHiddenLayers; i++) {
            conf.layer(i, new DenseLayer.Builder()
                    .nIn(numHiddenNodes)
                    .nOut(numHiddenNodes)
                    .activation(Activation.RELU)
                    .build()
            );
        }

        conf.layer(numHiddenLayers + 1, new OutputLayer.Builder(LossFunctions.LossFunction.SQUARED_LOSS)
                .nIn(numHiddenNodes)
                .nOut(nOut)
                .activation(Activation.IDENTITY)
                .build()
        );

        this.nnConf = conf.build();
        
        System.out.println(this.nnConf.toJson());
    }
}
