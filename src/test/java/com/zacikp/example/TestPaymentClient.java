/*
 * Copyright (c) 2018 Petr Žáčík. All rights reserved.
 */
package com.zacikp.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class TestPaymentClient {

    private ConcurrentHashMap<String, Double> usdRates;
    private ConcurrentHashMap<String, Integer> paymentsMap;
    private Payments payments;
    private PaymentsClient paymentsClient;

    @Before
    public void setup() {
        usdRates = new ConcurrentHashMap<>();
        paymentsMap = new ConcurrentHashMap<>();
        payments = new Payments(usdRates, paymentsMap);
        paymentsClient = new PaymentsClient(payments);
    }

    @Test
    public void testWrongInput() {
        paymentsClient.addCSVPayment("Wrong text");
        paymentsClient.addCSVPayment("AAA 23.44");
        paymentsClient.addCSVPayment(" ");
        paymentsClient.addCSVPayment("");
        assertEquals(0, paymentsMap.size());

    }

    @Test
    public void testAddCSVInput() {
        paymentsClient.addCSVPayment("AAA 333");
        paymentsClient.addCSVPayment("Bbb 444");

        assertEquals(2, paymentsMap.size());
    }

}
