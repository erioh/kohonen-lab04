package org.demenkov.lab.network.algoritms;

import java.util.*;

public class KohonenAlgorithm {
    // init Weights
    // learn -> normalize


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
//        double[][] normalizedData = Arrays.stream(data)
//                .map(this::normalize)
//                .toArray(double[][]::new);
        do {
            for (double[] vector : data) {
                KohonenNeuron currentWinner = kohonenNeurons.stream()
                        .map(kohonenNeuron -> new AbstractMap.SimpleEntry<>(kohonenNeuron.calculate(vector), kohonenNeuron))
                        .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getKey))
                        .orElseThrow(() -> new IllegalArgumentException("Something went wrong"))
                        .getValue();
                boolean adjusted = currentWinner.adjustWeight(vector);
                if (!weightWasAdjusted) {
                    weightWasAdjusted = adjusted;
                }
            }
            tick++;
        } while (weightWasAdjusted && epochs > tick);
        kohonenNeurons.forEach(kohonenNeuron -> System.out.println(Arrays.toString(kohonenNeuron.getWeight())));
        //

        for (double[] vector : data) {
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


//    private double[] normalize(double[] input) {
//        double sumOfValues = 0;
//        for (double value : input) {
//            sumOfValues += value;
//        }
//        for (int i = 0; i < input.length; i++) {
//            input[i] /= sumOfValues;
//        }
//        return input;
//    }
}
