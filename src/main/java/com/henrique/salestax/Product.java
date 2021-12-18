package com.henrique.salestax;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Product {
    // create properties for product desctiption, price and imported
    private String description;
    private double price;
    private boolean imported;
    private double tax = 0.0;
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
        this.price = Double.parseDouble(description.split(" at ")[1]);

        if (this.price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
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

    private void calculateTax() {
        if (this.imported) {
            this.tax = this.price * 0.05;
        }

        if (!this.exempted) {
            this.tax = this.tax + this.price * 0.10;
        }

        this.tax = Math.ceil(this.tax * 20.0) / 20.0;

    }

    public int getQuantity() {
        return this.quantity;
    }

    public boolean isExempted() {
        return this.exempted;
    }

    public double getTax() {
        return this.tax;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isImported() {
        return imported;
    }

}
