package com.henrique.salestax;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Receipt {
    private List<Product> products = new ArrayList<>();
    private BigDecimal totalTax = new BigDecimal("0.00");
    private BigDecimal totalPrice = new BigDecimal("0.00");

    public Receipt(String text) {
        List<String> lines = Arrays.asList(text.split("\n"));
        for (String line : lines) {
            Product product = new Product(line);
            products.add(product);
        }
        calculateTaxAndPrices();
    }

    private void calculateTaxAndPrices() {
        for (Product product : this.products) {
            this.totalTax = this.totalTax.add(product.getTax());
            this.totalPrice = this.totalPrice.add(product.getPrice());
        }

        this.totalPrice = totalPrice.add(this.totalTax);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : this.products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Sales Taxes: ").append(this.totalTax.toString()).append("\n");
        sb.append("Total: ").append(this.totalPrice.toString());
        return sb.toString();
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
