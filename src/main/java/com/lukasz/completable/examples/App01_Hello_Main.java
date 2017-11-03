package com.lukasz.completable.examples;

import com.lukasz.completable.services.StockPriceService;
import com.lukasz.completable.services.StockPriceServiceProviderRandom;

public class App01_Hello_Main {
    public static void main(String[] args) {
//        final String ticker = "GOOGL";
        final String ticker = "SABR";
        StockPriceService stockPriceService = new StockPriceService(new StockPriceServiceProviderRandom());
        System.out.println("Stock price for: " + ticker +  ": "
                + stockPriceService.fetchStockRecentValue(ticker));
    }
}
