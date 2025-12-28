package com.trading.model;

public class Stock {
    private final String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    // Synchronized to ensure thread safety when reading price
    public synchronized double getPrice() {
        return price;
    }

    // Synchronized to ensure thread safety when MarketSimulator updates it
    public synchronized void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f", symbol, price);
    }
}