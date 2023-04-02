package org.demenkov.lab.network;

import org.demenkov.lab.network.algorithms.KohonenAlgorithm;

import java.io.IOException;

import static org.demenkov.lab.network.files.FileModificator.read;

public class Main {
    public static void main(String[] args) throws IOException {
        KohonenAlgorithm algorithm = new KohonenAlgorithm();
        if (args.length > 0 && args[0].equals("train")) {
            double[][] learnData = read("data/LearnData.txt");
            algorithm.learn(learnData, 5, 10000);
            System.out.println("network is trained");
        } else {
            double[][] testData = read("data/TestData.txt");
            algorithm.check(testData);
        }
    }
}
