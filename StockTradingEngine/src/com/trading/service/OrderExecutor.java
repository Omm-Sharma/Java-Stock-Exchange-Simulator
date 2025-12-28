package com.trading.service;

import com.trading.model.Portfolio;
import com.trading.model.Stock;
import com.trading.model.Transaction;

public class OrderExecutor {

    public void processBuy(Portfolio portfolio, Stock stock, int quantity) {
        synchronized (stock) { // Lock stock to guarantee price doesn't change during math
            double price = stock.getPrice();
            double cost = price * quantity;

            if (portfolio.getCashBalance() >= cost) {
                portfolio.debit(cost);
                portfolio.addStock(stock.getSymbol(), quantity);
                portfolio.addTransaction(new Transaction(stock.getSymbol(), "BUY", price, quantity));
                System.out.printf("[SUCCESS] Bought %d %s at $%.2f. Remaining Cash: $%.2f%n", 
                    quantity, stock.getSymbol(), price, portfolio.getCashBalance());
            } else {
                System.out.printf("[ERROR] Insufficient funds. Cost: $%.2f, You have: $%.2f%n", 
                    cost, portfolio.getCashBalance());
            }
        }
    }

    public void processSell(Portfolio portfolio, Stock stock, int quantity) {
        synchronized (stock) {
            int owned = portfolio.getQuantity(stock.getSymbol());
            if (owned >= quantity) {
                double price = stock.getPrice();
                double value = price * quantity;
                
                portfolio.removeStock(stock.getSymbol(), quantity);
                portfolio.credit(value);
                portfolio.addTransaction(new Transaction(stock.getSymbol(), "SELL", price, quantity));
                System.out.printf("[SUCCESS] Sold %d %s at $%.2f. New Balance: $%.2f%n", 
                    quantity, stock.getSymbol(), price, portfolio.getCashBalance());
            } else {
                System.out.printf("[ERROR] You only own %d shares of %s.%n", owned, stock.getSymbol());
            }
        }
    }
}