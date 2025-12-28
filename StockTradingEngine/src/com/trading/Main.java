package com.trading;

import com.trading.command.*;
import com.trading.model.Portfolio;
import com.trading.service.MarketService;
import com.trading.service.MarketSimulator;
import com.trading.service.OrderExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Services
        MarketService marketService = new MarketService();
        OrderExecutor orderExecutor = new OrderExecutor();
        Portfolio portfolio = new Portfolio(10000.00); // Start with $10,000

        // 2. Start Background Thread (Market Simulator)
        MarketSimulator simulator = new MarketSimulator(marketService);
        Thread marketThread = new Thread(simulator);
        marketThread.start();

        // 3. Register Commands
        Map<String, Command> commands = new HashMap<>();
        commands.put("buy", new BuyCommand(marketService, orderExecutor, portfolio));
        commands.put("sell", new SellCommand(marketService, orderExecutor, portfolio));
        commands.put("quote", new ShowQuoteCommand(marketService));

        // 4. Main Loop
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== JAVA STOCK EXCHANGE ===");
        System.out.println("Commands: buy <sym> <qty>, sell <sym> <qty>, quote <sym>, portfolio, quit");

        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String commandName = parts[0].toLowerCase();
            String[] commandArgs = Arrays.copyOfRange(parts, 1, parts.length);

            if (commandName.equals("quit")) {
                running = false;
                simulator.stop(); // Stop the background thread
            } else if (commandName.equals("portfolio")) {
                System.out.println(portfolio); // Simple view
            } else if (commands.containsKey(commandName)) {
                commands.get(commandName).execute(commandArgs);
            } else {
                System.out.println("Unknown command.");
            }
        }

        System.out.println("Exiting... Market Closed.");
        try {
            marketThread.join(); // Wait for thread to finish cleanly
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}