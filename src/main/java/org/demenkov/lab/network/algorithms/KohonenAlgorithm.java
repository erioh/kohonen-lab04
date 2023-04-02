package org.demenkov.lab.network.algorithms;

import java.io.IOException;
import java.util.*;

import static org.demenkov.lab.network.files.FileModificator.read;
import static org.demenkov.lab.network.files.FileModificator.write;
import static org.demenkov.lab.network.math.VectorMathOperation.multiply;
import static org.demenkov.lab.network.math.VectorMathOperation.plus;

public class KohonenAlgorithm {
    // init Weights
    // learn -> normalize
    private static final double INCREMENT = .01;
    public static final String DATA_RESULT_TXT = "data/result.txt";

    private double coefficient = 0;


    public void learn(double[][] data, int numberOfNeurons, long epochs) throws IOException {
        int length = data[0].length;
        List<KohonenNeuron> kohonenNeurons = new ArrayList<>();
        for (int index = 0; index < numberOfNeurons; index++) {
            kohonenNeurons.add(new KohonenNeuron(index, length));
        }
        boolean weightWasAdjusted;
        long tick = 0;
        do {
            if (coefficient < 1) {
                coefficient += INCREMENT;
                if (coefficient > 1) {
                    coefficient = 1;
                }
            }
            weightWasAdjusted = false;
            for (double[] vector : data) {
                double[] stepVector = getStepVector(vector);
                KohonenNeuron currentWinner = kohonenNeurons.stream()
                        .map(kohonenNeuron -> new AbstractMap.SimpleEntry<>(kohonenNeuron.calculate(stepVector), kohonenNeuron))
                        .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getKey))
                        .orElseThrow(() -> new IllegalArgumentException("Something went wrong"))
                        .getValue();
                boolean adjusted = currentWinner.adjustWeight(stepVector);
                if (!weightWasAdjusted) {
                    weightWasAdjusted = adjusted;
                }
            }
            tick++;
        } while ((weightWasAdjusted || coefficient != 1) && epochs > tick);

        double[][] toBePersisted = kohonenNeurons.stream()
                .map(KohonenNeuron::getWeight)
                .toArray(double[][]::new);
        write(DATA_RESULT_TXT, toBePersisted);
    }

    public void check(double[][] data) throws IOException {
        double[][] weights = read(DATA_RESULT_TXT);
        List<KohonenNeuron> kohonenNeurons = new ArrayList<>();
        for (int index = 0; index < weights.length; index++) {
            kohonenNeurons.add(new KohonenNeuron(index, weights[index]));
        }
        for (double[] vector : data) {
            KohonenNeuron currentWinner = kohonenNeurons.stream()
                    .map(kohonenNeuron -> new AbstractMap.SimpleEntry<>(kohonenNeuron.calculate(normalize(vector)), kohonenNeuron))
                    .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getKey))
                    .orElseThrow(() -> new IllegalArgumentException("Something went wrong"))
                    .getValue();
            System.out.println("Selected neuron = " + currentWinner.getIndex() + " " + Arrays.toString(vector));
        }
    }

    private double[] getStepVector(double[] vector) {
        double[] normalizedVector = normalize(vector);
        return plus(multiply(normalizedVector, coefficient), (1 - coefficient) / Math.sqrt(normalizedVector.length));
    }

    private double[] normalize(double[] input) {
        double vectorLength = 0;
        double[] output = new double[input.length];
        for (double value : input) {
            vectorLength += value * value;
        }
        vectorLength = Math.sqrt(vectorLength);
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i] / vectorLength;
        }
        return output;
    }
}
