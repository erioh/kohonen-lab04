package org.demenkov.lab.network.algoritms;

import java.util.Arrays;

import static org.demenkov.lab.network.math.VectorMathOperation.*;

public class KohonenNeuron {
    private static final double ALPHA = .01;
    private final int index;
    private double[] weight;


    public KohonenNeuron(int index, double[] initWeight) {
        this.index = index;
        weight = Arrays.copyOf(initWeight, initWeight.length);
    }

    public double calculate(double[] vector) {
        double dist = 0;
        for (int i = 0; i < vector.length; i++) {
            dist += Math.pow(vector[i] - weight[i], 2);
        }
        return dist;
    }

    public double[] getWeight() {
        return weight;
    }

    public boolean adjustWeight(double[] vector) {
        if (Arrays.equals(weight,vector)) {
            return false;
        }
        weight = plus(weight, multiply(minus(vector, weight), ALPHA));
        return true;
    }

    public int getIndex() {
        return index;
    }
}
