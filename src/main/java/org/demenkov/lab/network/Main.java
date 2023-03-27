package org.demenkov.lab.network;

import org.demenkov.lab.network.algoritms.KohonenAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String stringData = Files.readString(Path.of("data/Data.txt"));
        List<List<Double>> data1 = Arrays.stream(stringData.split("\r\n"))
                .map(vector -> Arrays.stream(vector.split(","))
                        .map(Double::valueOf)
                        .toList())
                .toList();
        double[][] data = new double[data1.size()][data1.get(0).size()];
        for (int i = 0; i < data1.size(); i++) {
            List<Double> vector = data1.get(i);
            for (int j = 0; j < vector.size(); j++) {
                data[i][j] = vector.get(j);
            }
        }
        KohonenAlgorithm algorithm = new KohonenAlgorithm();

        algorithm.learn(data, 5, 10000);
    }
}
