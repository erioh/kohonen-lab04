package org.demenkov.lab.network;

import org.demenkov.lab.network.algoritms.KohonenAlgorithm;

import java.io.IOException;

import static org.demenkov.lab.network.files.FileModificator.read;

public class Main {
    public static void main(String[] args) throws IOException {
        double[][] learnData = read("data/LearnData.txt");
        KohonenAlgorithm algorithm = new KohonenAlgorithm();
        algorithm.learn(learnData, 5, 10000);
        double[][] testData = read("data/TestData.txt");
        algorithm.check(testData);
    }
}
