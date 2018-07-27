/*
 * Copyright (c) 2018 Petr Žáčík. All rights reserved.
 */
package com.zacikp.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Payment Tracker - Application
 */
public class PaymentTracker {

    public static void main(String[] args) {
        Payments payments = new Payments();

        if (args.length > 0) {
            readDataFromFile(args[0], payments);
        }
        new PaymentsClient(payments).start();
    }

    /**
     * Loads Payments from File and stores them into Payment object.
     * Errors will be printed out in stderr output and program will continue without corrupted records.
     *
     * @param fileName Name of file to load.
     * @param payments Successfully loaded Payments will be stored into this object.
     */
    private static void readDataFromFile(String fileName, Payments payments) {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            System.out.println("Reading file " + fileName);
            lines.filter(line -> PaymentsClient.CSV_PATTERN.matcher(line).matches())
                    .map(line -> line.trim().split(" "))
                    .forEach(strings -> payments.add(strings[0], Integer.valueOf(strings[1])));
        } catch (Exception e) {
            System.err.println(String.format("Cannot import file %s, exception: %s", fileName, e));
        }
    }
}
