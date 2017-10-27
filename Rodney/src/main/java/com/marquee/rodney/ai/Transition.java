package com.marquee.rodney.ai;

public class Transition {

    private Object state;
    private Object action;
    private Object nextState;

    private double reward;

    public Transition(Object state, Object action, Object nextState, double reward) {
        this.state = state;
        this.action = action;
        this.nextState = nextState;
        this.reward = reward;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Object getNextState() {
        return nextState;
    }

    public void setNextState(Object nextState) {
        this.nextState = nextState;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }
}
