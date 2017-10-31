package com.marquee.ai;

public class NNetwork {

    private int inputSize;
    private int outputSize;
    private int hiddenSize;
    private int hiddenNum;

    public NNetwork(int inputSize, int outputSize, int hiddenSize, int hiddenNum) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.hiddenSize = hiddenSize;
        this.hiddenNum = hiddenNum;
    }
}
