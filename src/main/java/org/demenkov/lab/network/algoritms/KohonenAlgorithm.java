package org.demenkov.lab.network.algoritms;

import java.util.*;

import static org.demenkov.lab.network.math.VectorMathOperation.multiply;
import static org.demenkov.lab.network.math.VectorMathOperation.plus;

public class KohonenAlgorithm {
    // init Weights
    // learn -> normalize
    private static final double INCREMENT = .01;

    private double coefficient = 0;


    public void learn(double[][] data, int numberOfNeurons, long epochs) {
        int length = data[0].length;
        double[] initWeight = new double[length];
        Arrays.fill(initWeight, 1 / Math.sqrt(length));
        List<KohonenNeuron> kohonenNeurons = new ArrayList<>();
        for (int index = 0; index < numberOfNeurons; index++) {
            kohonenNeurons.add(new KohonenNeuron(index, initWeight));
        }
        boolean weightWasAdjusted = false;
        long tick = 0;
        do {
            for (double[] vector : data) {
                double[] stepVector = getStepVector(vector);
                KohonenNeuron currentWinner = kohonenNeurons.stream()
                        .map(kohonenNeuron -> new AbstractMap.SimpleEntry<>(kohonenNeuron.calculate(stepVector), kohonenNeuron))
                        .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getKey))
                        .orElseThrow(() -> new IllegalArgumentException("Something went wrong"))
                        .getValue();
                boolean adjusted = currentWinner.adjustWeight(stepVector);
                if (!weightWasAdjusted) {
                    weightWasAdjusted = adjusted;
                }
            }
            tick++;
            if (coefficient < 1) {
                coefficient += INCREMENT;
            }
        } while ((weightWasAdjusted || coefficient < 1) && epochs > tick);
        kohonenNeurons.forEach(kohonenNeuron -> System.out.println(Arrays.toString(kohonenNeuron.getWeight())));
        //
        double[][] testData = {
                {1,2,3,4,5,6,7,8,9},
                {8,8,7,6,5,4,3,2,1},
                {1,3,1,2,1,2,2,2,1},
                {4,4,4,4,2,3,3,2,1},
                {1,2,4,1,2,3,1,2,3}
        };
        for (double[] vector : testData) {
            KohonenNeuron currentWinner = kohonenNeurons.stream()
                    .map(kohonenNeuron -> new AbstractMap.SimpleEntry<>(kohonenNeuron.calculate(vector), kohonenNeuron))
                    .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getKey))
                    .orElseThrow(() -> new IllegalArgumentException("Something went wrong"))
                    .getValue();
            System.out.println("-----------------");
            System.out.println("currentWinner = " + currentWinner.getIndex());
            System.out.println(Arrays.toString(vector));
        }

        //
    }


    private double[] getStepVector(double[] vector) {
        return plus(multiply(vector, coefficient), (1 - coefficient) / Math.sqrt(vector.length));
    }
}
