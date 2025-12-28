package com.trading.service;

import com.trading.model.Stock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MarketService {
    // ConcurrentHashMap is crucial here for thread safety
    private final Map<String, Stock> market = new ConcurrentHashMap<>();

    public MarketService() {
        // Seed Data
        market.put("AAPL", new Stock("AAPL", 150.00));
        market.put("GOOG", new Stock("GOOG", 2800.00));
        market.put("TSLA", new Stock("TSLA", 750.00));
        market.put("AMZN", new Stock("AMZN", 3400.00));
    }

    public Stock getStock(String symbol) {
        return market.get(symbol);
    }

    public Map<String, Stock> getAllStocks() {
        return market;
    }
}