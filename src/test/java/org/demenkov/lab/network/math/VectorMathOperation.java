package org.demenkov.lab.network.math;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public final class VectorMathOperation {
    private VectorMathOperation() {
    }

    public static double[] plus(double[] vector, double value) {
        return DoubleStream.of(Arrays.copyOf(vector, vector.length))
                .map(element -> element + value)
                .toArray();
    }

    public static double[] plus(double[] first, double[] second) {
        double[] result = new double[first.length];
        for (int i = 0; i < first.length; i++) {
            result[i] = first[i] + second[i];
        }
        return result;
    }

    public static double[] minus(double[] first, double[] second) {
        double[] result = new double[first.length];
        for (int i = 0; i < first.length; i++) {
            result[i] = first[i] - second[i];
        }
        return result;
    }

    public static double[] multiply(double[] vector, double value) {
        return DoubleStream.of(Arrays.copyOf(vector, vector.length))
                .map(element -> element * value)
                .toArray();
    }
}
