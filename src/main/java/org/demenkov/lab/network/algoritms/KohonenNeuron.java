package org.demenkov.lab.network.algoritms;

import java.util.Arrays;

import static org.demenkov.lab.network.math.VectorMathOperation.*;

public class KohonenNeuron {
    private static final double ALPHA = .1;
    private final int index;
    private double[] weight;


    public KohonenNeuron(int index, double[] initWeight) {
        this.index = index;
        weight = Arrays.copyOf(initWeight, initWeight.length);
    }

    public double calculate(double[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum+=vector[i]*weight[i];
        }
        return sum;
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
