package com.trading.command;

import com.trading.model.Portfolio;
import com.trading.model.Stock;
import com.trading.service.MarketService;
import com.trading.service.OrderExecutor;

public class BuyCommand implements Command {
    private final MarketService marketService;
    private final OrderExecutor orderExecutor;
    private final Portfolio portfolio;

    public BuyCommand(MarketService marketService, OrderExecutor orderExecutor, Portfolio portfolio) {
        this.marketService = marketService;
        this.orderExecutor = orderExecutor;
        this.portfolio = portfolio;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: buy <symbol> <quantity>");
            return;
        }
        String symbol = args[0].toUpperCase();
        
        try {
            int qty = Integer.parseInt(args[1]);
            Stock stock = marketService.getStock(symbol);
            if (stock != null) {
                orderExecutor.processBuy(portfolio, stock, qty);
            } else {
                System.out.println("Stock not found: " + symbol);
            }
        } catch (NumberFormatException e) {
            System.out.println("Quantity must be a number.");
        }
    }
}