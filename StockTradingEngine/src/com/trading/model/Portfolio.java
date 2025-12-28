package com.trading.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private double cashBalance;
    private final Map<String, Integer> holdings; // Symbol -> Quantity
    private final List<Transaction> history;

    public Portfolio(double initialCash) {
        this.cashBalance = initialCash;
        this.holdings = new HashMap<>();
        this.history = new ArrayList<>();
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void debit(double amount) {
        this.cashBalance -= amount;
    }

    public void credit(double amount) {
        this.cashBalance += amount;
    }

    public void addStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void removeStock(String symbol, int quantity) {
        if (!holdings.containsKey(symbol)) return;
        int currentQty = holdings.get(symbol);
        if (currentQty <= quantity) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, currentQty - quantity);
        }
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public void addTransaction(Transaction t) {
        history.add(t);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- PORTFOLIO ---\nCash: $%.2f\nStocks:\n", cashBalance));
        holdings.forEach((k, v) -> sb.append(String.format("  - %s: %d shares\n", k, v)));
        return sb.toString();
    }
}