package com.trading.service;

import com.trading.model.Stock;
import java.util.Random;

public class MarketSimulator implements Runnable {
    private final MarketService marketService;
    private final Random random = new Random();
    private volatile boolean running = true;

    public MarketSimulator(MarketService marketService) {
        this.marketService = marketService;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(2000); // Updates every 2 seconds
                
                for (Stock stock : marketService.getAllStocks().values()) {
                    // Random fluctuation between -1% and +1%
                    double changeFactor = 1 + (random.nextDouble() * 0.02 - 0.01);
                    
                    // We lock the stock to ensure we don't overwrite a price 
                    // while an order is calculating cost
                    synchronized (stock) {
                        double newPrice = stock.getPrice() * changeFactor;
                        stock.setPrice(newPrice);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Market Simulator interrupted");
            }
        }
    }
}