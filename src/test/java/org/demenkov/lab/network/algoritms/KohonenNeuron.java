package org.demenkov.lab.network.algoritms;

import java.util.Arrays;

import static org.demenkov.lab.network.math.VectorMathOperation.*;

public class KohonenNeuron {
    private static final double INIT_COEFFICIENT = .01;
    private static final double INCREMENT = .01;
    private static final double ALPHA = .1;
    private final int index;
    private double[] weight;
    private double coefficient;


    public KohonenNeuron(int index, double[] initWeight) {
        this.index = index;
        weight = Arrays.copyOf(initWeight, initWeight.length);
        coefficient = INIT_COEFFICIENT;
    }

    public double calculate(double[] vector) {
        double[] stepVector = getStepVector(vector);
        double dist = 0;
        for (int i = 0; i < stepVector.length; i++) {
            dist += Math.pow(stepVector[i] - weight[i], 2);
        }
        return dist;
    }

    public double[] getWeight() {
        return weight;
    }

    public boolean adjustWeight(double[] vector) {
        double[] stepVector = getStepVector(vector);
        if (stepVector == weight) {
            return false;
        }
        weight = plus(weight, multiply(minus(stepVector, weight), ALPHA));
        if (coefficient < 1) {
            coefficient += INCREMENT;
        }
        return true;
    }

    public int getIndex() {
        return index;
    }

    private double[] getStepVector(double[] vector) {
        return plus(multiply(vector, coefficient), (1 - coefficient) / Math.sqrt(vector.length));
    }
}
