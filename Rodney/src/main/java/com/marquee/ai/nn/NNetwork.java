package com.marquee.ai.nn;

import jama.*;
import com.marquee.ai.*;

public class NNetwork {

    private final int inputSize;
    private final int outputSize;
    private final int hiddenSize;
    private final int hiddenNum;

    private Matrix[] weights;

    public NNetwork(int inputSize, int outputSize, int hiddenSize, int hiddenNum) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.hiddenSize = hiddenSize;
        this.hiddenNum = hiddenNum;

        this.weights = new Matrix[hiddenNum + 1];

        this.weights[0] = Matrix.random(inputSize, hiddenSize);

        for (int i = 1; i <= hiddenNum; i++)
            this.weights[i] = Matrix.random(hiddenSize, hiddenSize);

        this.weights[hiddenNum] = Matrix.random(hiddenSize, outputSize);
    }

    public Matrix foward(Matrix x) {
        Matrix ret = x.times(this.weights[0]);

        ret = ret.applyFunc(Functions::relu);

        return ret;
    }
}
