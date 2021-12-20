package com.henrique.salestax;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ReceiptTest {
    @Test
    public void SimpleReceipt() {
        Receipt receipt = new Receipt(
                "1 book at 12.49\n" +
                        "1 music CD at 14.99\n" +
                        "1 chocolate bar at 0.85");
        BigDecimal totalPrice = new BigDecimal("29.83");
        BigDecimal totalTax = new BigDecimal("1.50");
        assertEquals(totalPrice.stripTrailingZeros(), receipt.getTotalPrice().stripTrailingZeros());
        assertEquals(totalTax.stripTrailingZeros(), receipt.getTotalTax().stripTrailingZeros());
        assertEquals("1 book: 12.49\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "Sales Taxes: 1.50\n" +
                "Total: 29.83", receipt.toString());
    }

    @Test
    public void ReceiptWithMissingItem() {
        try {
            Receipt receipt = new Receipt(
                    "1 book at 12.49\n" +
                            "1 music CD at 14.99\n" +
                            "1 chocolate bar at 0.85\n" +
                            " \n" +
                            "1 imported box of chocolates at 10.00\n" +
                            "1 imported bottle of perfume at 47.50");
            assertTrue(false);
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ReceiptWithTwoAts() {
        try {
            Receipt receipt = new Receipt(
                    "1 book at 12.49\n" +
                            "1 music CD at 14.99 1 imported box of chocolates at 10.00\n" +
                            "1 imported bottle of perfume at 47.50\n" +
                            "1 book at 12.49\n" +
                            "1 music CD at 14.99\n" +
                            "1 chocolate bar at 0.85");
            assertTrue(false);
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ReceiptWithNoAts() {
        try {
            Receipt receipt = new Receipt(
                    "1 book at 12.49\n" +
                            "1 music CD 14.99\n" +
                            "1 chocolate bar at 0.85\n" +
                            "1 imported box of chocolates at 10.00\n" +
                            "1 imported bottle of perfume at 47.50");
            assertTrue(false);
        } catch (ArrayIndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ReceiptWithZeroQuantityItem() {
        try {
            Receipt receipt = new Receipt(
                    "1 book at 12.49\n" +
                            "1 music CD at 14.99\n" +
                            "1 chocolate bar at 0.85\n" +
                            "1 imported box of chocolates at 10.00\n" +
                            "1 imported bottle of perfume at 47.50\n" +
                            "0 imported bottle of perfume at 47.50");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            assertEquals(e.getMessage(), "Quantity must be greater than 0");
        }
    }

    @Test
    public void ReceiptWithDuplicateItem() {
        Receipt receipt = new Receipt(
                "1 book at 12.49\n" +
                        "1 music CD at 14.99\n" +
                        "1 chocolate bar at 0.85\n" +
                        "1 imported box of chocolates at 10.00\n" +
                        "1 imported bottle of perfume at 47.50\n" +
                        "1 imported bottle of perfume at 47.50");
        BigDecimal totalPrice = new BigDecimal("149.63");
        BigDecimal totalTax = new BigDecimal("16.30");
        assertEquals(totalPrice.stripTrailingZeros(), receipt.getTotalPrice().stripTrailingZeros());
        assertEquals(totalTax.stripTrailingZeros(), receipt.getTotalTax().stripTrailingZeros());
        assertEquals("1 book: 12.49\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "1 imported box of chocolates: 10.50\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "Sales Taxes: 16.30\n" +
                "Total: 149.63", receipt.toString());

    }

    @Test
    public void ReceiptOne() {
        Receipt receipt = new Receipt(
                "1 book at 12.49\n" +
                        "1 music CD at 14.99\n" +
                        "1 chocolate bar at 0.85");
        BigDecimal totalPrice = new BigDecimal("29.83");
        BigDecimal totalTax = new BigDecimal("1.50");
        assertEquals(totalPrice.stripTrailingZeros(), receipt.getTotalPrice().stripTrailingZeros());
        assertEquals(totalTax.stripTrailingZeros(), receipt.getTotalTax().stripTrailingZeros());
        assertEquals("1 book: 12.49\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "Sales Taxes: 1.50\n" +
                "Total: 29.83", receipt.toString());
    }

    @Test
    public void ReceiptTwo() {
        Receipt receipt = new Receipt(
                "1 imported box of chocolates at 10.00\n" +
                        "1 imported bottle of perfume at 47.50");
        BigDecimal totalPrice = new BigDecimal("65.15");
        BigDecimal totalTax = new BigDecimal("7.65");
        assertEquals(totalPrice.stripTrailingZeros(), receipt.getTotalPrice().stripTrailingZeros());
        assertEquals(totalTax.stripTrailingZeros(), receipt.getTotalTax().stripTrailingZeros());
        assertEquals("1 imported box of chocolates: 10.50\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "Sales Taxes: 7.65\n" +
                "Total: 65.15", receipt.toString());
    }

    @Test
    public void ReceiptThree() {
        Receipt receipt = new Receipt(
                "1 imported bottle of perfume at 27.99\n" +
                        "1 bottle of perfume at 18.99\n" +
                        "1 packet of headache pills at 9.75\n" +
                        "1 imported box of chocolates at 11.25");
        BigDecimal totalPrice = new BigDecimal("74.68");
        BigDecimal totalTax = new BigDecimal("6.70");
        assertEquals(totalPrice.stripTrailingZeros(), receipt.getTotalPrice().stripTrailingZeros());
        assertEquals(totalTax.stripTrailingZeros(), receipt.getTotalTax().stripTrailingZeros());
        assertEquals("1 imported bottle of perfume: 32.19\n" +
                "1 bottle of perfume: 20.89\n" +
                "1 packet of headache pills: 9.75\n" +
                "1 imported box of chocolates: 11.85\n" +
                "Sales Taxes: 6.70\n" +
                "Total: 74.68", receipt.toString());
    }
}
