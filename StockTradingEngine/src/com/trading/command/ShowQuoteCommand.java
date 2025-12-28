package com.trading.command;

import com.trading.model.Stock;
import com.trading.service.MarketService;

public class ShowQuoteCommand implements Command {
    private final MarketService marketService;

    public ShowQuoteCommand(MarketService marketService) {
        this.marketService = marketService;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: quote <symbol> OR quote all");
            return;
        }
        String symbol = args[0].toUpperCase();
        
        if (symbol.equals("ALL")) {
            marketService.getAllStocks().values().forEach(System.out::println);
        } else {
            Stock stock = marketService.getStock(symbol);
            if (stock != null) {
                System.out.println(stock);
            } else {
                System.out.println("Stock not found.");
            }
        }
    }
}