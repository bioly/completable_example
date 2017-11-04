package com.lukasz.completable.examples;

import com.lukasz.completable.services.StockPriceService;
import com.lukasz.completable.services.StockPriceServiceProviderNasdaq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lukasz.completable.utils.TimeIt.timed;

public class App01_Hello_Main {

    private static final Logger log = LoggerFactory.getLogger(App01_Hello_Main.class);

    public static void main(String[] args) {
        final String ticker = "SABR";
        StockPriceService stockPriceService = new StockPriceService(new StockPriceServiceProviderNasdaq());
        String value = timed("SABR",
                () -> stockPriceService.fetchStockRecentValue(ticker));
        System.out.println("Stock price for: " + ticker +  ": " + value);
    }
}
