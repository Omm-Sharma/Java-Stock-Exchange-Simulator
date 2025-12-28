package com.trading.model;

import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime timestamp;
    private final String symbol;
    private final String type; // BUY or SELL
    private final double price;
    private final int quantity;

    public Transaction(String symbol, String type, double price, int quantity) {
        this.timestamp = LocalDateTime.now();
        this.symbol = symbol;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %d %s @ $%.2f", 
            timestamp, type, quantity, symbol, price);
    }
}