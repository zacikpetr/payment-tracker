/*
 * Copyright (c) 2018 Petr Žáčík. All rights reserved.
 */
package com.zacikp.example;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client Payment Tracking application
 */
public class PaymentsClient {

    /**
     * Pattern to extract/deserialize values stored in CSV format.
     */
    public static Pattern CSV_PATTERN = Pattern.compile("([a-zA-Z]{3}) ([-+]?\\d+)");

    private static final String QUIT_COMMAND = "quit";

    private Payments payments;

    public PaymentsClient(Payments payments) {
        this.payments = payments;
    }

    /**
     * Starts main processing that is responsible for:
     * <ul><li>printing of all payments in defined period of time</li>
     * <li>providing CLI for user interactions</li></ul>
     */
    public void start() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(payments::print, 0, 1, TimeUnit.MINUTES);

        Scanner inputScanner = new Scanner(System.in);
        String nextLine = inputScanner.nextLine();
        while (!QUIT_COMMAND.equals(nextLine)) {
            try {
                addCSVPayment(nextLine);
            } catch (Exception e) {
                System.err.println("Error: " + e);
            } finally {
                nextLine = inputScanner.nextLine();
            }
        }
        executorService.shutdownNow();
        inputScanner.close();
    }

    void addCSVPayment(String nextLine) {
        Matcher matcher = CSV_PATTERN.matcher(nextLine);
        if (matcher.matches()) {
            int value = Integer.valueOf(matcher.group(2));
            payments.add(matcher.group(1), value);
        } else {
            System.err.println("Cannot parse input value.");
        }
    }


}
