/*
 * Copyright (c) 2018 Petr Žáčík. All rights reserved.
 */
package com.zacikp.example;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestPayments {

    @Test
    public void testAdd() {
        Payments payments = new Payments();
        payments.add("AAA", 100);
        payments.add("aaA", 100);
        assertEquals(200, payments.getPayments().get("AAA").intValue());
    }

    @Test
    public void testAddZero() {
        Payments payments = new Payments();
        payments.add("EUR", 0);
        assertNull(payments.getPayments().get("EUR"));
    }

    @Test
    public void testAddCurrLength() {
        Payments payments = new Payments();
        payments.add("EURO", 21);
        payments.add("E", 22);
        assertEquals(0, payments.getPayments().size());
    }

    @Test
    public void testRemoveValue() {
        Payments payments = new Payments();
        payments.add("BBB", 100);
        payments.add("BBB", -100);
        assertNull(payments.getPayments().get("BBB"));
    }

    @Test
    public void getFormattedLineWithoutRate() {
        Payments payments = new Payments(new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
        String actual = payments.getFormattedLine("EUR", 200);
        assertEquals("EUR 200", actual);
    }

    @Test
    public void getFormattedLineWithRate() {
        ConcurrentHashMap<String, Double> usdRates = new ConcurrentHashMap<>();
        usdRates.put("EUR", 1.171234);
        Payments payments = new Payments(usdRates, new ConcurrentHashMap<>());
        String formattedWithRate = payments.getFormattedLine("EUR", 3);
        assertEquals("EUR 3 (USD 3.51)", formattedWithRate);

        // Other Currencies are not formatted with rate.
        String formattedWithoutRate = payments.getFormattedLine("NOP", 54321);
        assertEquals("NOP 54321", formattedWithoutRate);
    }
}
