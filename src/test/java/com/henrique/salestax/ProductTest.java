package com.henrique.salestax;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ProductTest {
    @Test
    public void validNotImportedBook() {
        Product product = new Product("1 book at 12.49");
        assertEquals("book", product.getDescription());
        assertEquals(12.49, product.getPrice(), 0.001);
        assertFalse(product.isImported());
        assertEquals(0.0, product.getTax(), 0.001);
        assertTrue(product.isExempted());
        assertEquals(1, product.getQuantity());
    }

    @Test
    public void validImportedBook() {
        Product product = new Product("2 imported book at 12.49");
        assertEquals("imported book", product.getDescription());
        assertEquals(12.49, product.getPrice(), 0.001);
        assertTrue(product.isImported());
        assertEquals(0.65, product.getTax(), 0.001);
        assertEquals(2, product.getQuantity());
    }

    @Test
    public void itemWithTwoAts() {
        try {
            Product product = new Product("1 imported at book at 12.49");
            assertEquals("imported book", product.getDescription());
            assertEquals(12.49, product.getPrice(), 0.001);
            assertTrue(product.isImported());
            assertEquals(0.0, product.getTax(), 0.001);
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void itemWithNoAts() {
        try {
            Product product = new Product("1 imported book 12.49");
            assertEquals("imported book", product.getDescription());
            assertEquals(12.49, product.getPrice(), 0.001);
            assertTrue(product.isImported());
            assertEquals(0.0, product.getTax(), 0.001);
        } catch (ArrayIndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ValidNonExemptedItem() {
        Product product = new Product("3 music CD at 14.99");
        assertEquals("music CD", product.getDescription());
        assertEquals(14.99, product.getPrice(), 0.001);
        assertFalse(product.isImported());
        assertEquals(1.50, product.getTax(), 0.001);
        assertFalse(product.isExempted());
        assertEquals(3, product.getQuantity());
    }

    @Test
    public void ItemWithZeroPrice() {
        try {
            Product product = new Product("5 music CD at 0.00");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Price must be greater than 0");
        }
    }

    @Test
    public void ItemWithNegativePrice() {
        try {
            Product product = new Product("5 music CD at -1.00");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Price must be greater than 0");
        }
    }

    @Test
    public void ItemWithMissingPrice() {
        try {
            Product product = new Product("5 music CD at");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ValidNonExemptedImportedItem() {
        Product product = new Product("3 imported music CD at 14.99");
        assertEquals("imported music CD", product.getDescription());
        assertEquals(14.99, product.getPrice(), 0.001);
        assertTrue(product.isImported());
        assertEquals(2.25, product.getTax(), 0.001);
        assertFalse(product.isExempted());
        assertEquals(3, product.getQuantity());
    }

    @Test
    public void ItemWithZeroQuantity() {
        try {
            Product product = new Product("0 music CD at 14.99");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Quantity must be greater than 0");
        }
    }

    @Test
    public void ItemWithNegativeQuantity() {
        try {
            Product product = new Product("-1 music CD at 14.99");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Quantity must be greater than 0");
        }
    }

    @Test
    public void ItemWithMissingQuantity() {
        try {
            Product product = new Product("music CD at 14.99");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void ValidExemptedMedicalItem() {
        Product product = new Product("1 pill at 10.00");
        assertEquals("pill", product.getDescription());
        assertEquals(10.00, product.getPrice(), 0.001);
        assertFalse(product.isImported());
        assertEquals(0.00, product.getTax(), 0.001);
        assertTrue(product.isExempted());
        assertEquals(1, product.getQuantity());
    }

    @Test
    public void ValidImportedMedicalItem() {
        Product product = new Product("1 imported pill at 10.00");
        assertEquals("imported pill", product.getDescription());
        assertEquals(10.00, product.getPrice(), 0.001);
        assertTrue(product.isImported());
        assertEquals(0.50, product.getTax(), 0.001);
        assertTrue(product.isExempted());
        assertEquals(1, product.getQuantity());
    }

    @Test
    public void ValidExemptedFoodItem() {
        Product product = new Product("1 chocolate bar at 0.85");
        assertEquals("chocolate bar", product.getDescription());
        assertEquals(0.85, product.getPrice(), 0.001);
        assertFalse(product.isImported());
        assertEquals(0.00, product.getTax(), 0.001);
        assertTrue(product.isExempted());
        assertEquals(1, product.getQuantity());
    }

    @Test
    public void ValidImportedFoodItem() {
        Product product = new Product("1 imported pack of granola bar at 0.85");
        assertEquals("imported pack of granola bar", product.getDescription());
        assertEquals(0.85, product.getPrice(), 0.001);
        assertTrue(product.isImported());
        assertEquals(0.05, product.getTax(), 0.001);
        assertTrue(product.isExempted());
        assertEquals(1, product.getQuantity());
    }
}