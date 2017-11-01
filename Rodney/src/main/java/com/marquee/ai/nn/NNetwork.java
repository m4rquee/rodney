package com.marquee.ai.nn;

public class NNetwork {

    private final int inputSize;
    private final int outputSize;
    private final int hiddenSize;
    private final int hiddenNum;

    public NNetwork(int inputSize, int outputSize, int hiddenSize, int hiddenNum) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.hiddenSize = hiddenSize;
        this.hiddenNum = hiddenNum;
    }
}
