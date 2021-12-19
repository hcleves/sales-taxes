package com.henrique.salestax;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Product {
    // create properties for product desctiption, price and imported
    private String description;
    private BigDecimal price;
    private boolean imported;
    private BigDecimal tax = new BigDecimal(BigInteger.ZERO, 2);
    private boolean exempted;
    private int quantity;
    private static final List<String> medical = Arrays.asList("pill", "pills", "headache", "pain", "medical");
    private static final Set<String> food = new HashSet<>(Arrays.asList("apple", "apples", "banana", "bananas",
            "chocolate", "chocolates", "dessert", "desserts", "egg", "eggs", "fish", "granola", "ham", "hams",
            "ice cream", "milk", "peanut", "peanuts", "pear", "pears", "pie", "pies", "pizza", "pizzas", "salad",
            "salads", "sausage", "sausages", "soda", "sodas", "soup", "soups", "spaghetti", "spaghettis", "stew",
            "stews", "tuna", "tuna", "water", "waters"));
    private static final List<String> books = Arrays.asList("book", "books");

    // create constructor
    public Product(String description) {
        List<String> descriptionArray = Arrays.asList(description.split(" "));
        this.quantity = Integer.parseInt(descriptionArray.get(0));

        if (this.quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.description = description.split(" at ")[0];
        this.description = this.description.substring(this.description.indexOf(' ') + 1);
        this.price = new BigDecimal(description.split(" at ")[1]);

        if (this.price.compareTo(new BigDecimal("0.00")) < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        this.imported = description.contains("imported");
        this.exempted = false;

        // check if product is medical
        String result = descriptionArray.stream().filter(this.medical::contains).findFirst().orElse(null);
        if (result != null) {
            this.exempted = true;
        }
        // check if product is food
        result = descriptionArray.stream().filter(this.food::contains).findFirst().orElse(null);
        if (result != null) {
            this.exempted = true;
        }
        // check if product is book
        result = descriptionArray.stream().filter(this.books::contains).findFirst().orElse(null);
        if (result != null) {
            this.exempted = true;
        }

        this.calculateTax();
    }

    private static BigDecimal round(BigDecimal value, BigDecimal increment,
            RoundingMode roundingMode) {
        if (increment.signum() == 0) {
            // 0 increment does not make much sense, but prevent division by 0
            return value;
        } else {
            BigDecimal divided = value.divide(increment, 0, roundingMode);
            BigDecimal result = divided.multiply(increment);
            return result;
        }
    }

    private void calculateTax() {
        if (this.imported) {
            this.tax = this.price.multiply(new BigDecimal("0.05"));
        }

        if (!this.exempted) {
            this.tax = this.tax.add(this.price.multiply(new BigDecimal("0.10")));
        }

        this.tax = round(this.tax, new BigDecimal("0.05"), RoundingMode.UP);
    }

    public String toString() {
        return String.format("%d %s: %s", this.quantity, this.description, this.price.add(this.tax));
    }

    public int getQuantity() {
        return this.quantity;
    }

    public boolean isExempted() {
        return this.exempted;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isImported() {
        return imported;
    }

}
