package com.lukasz.completable.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class StockPriceServiceProviderRandom extends StockPriceServiceProvider {
    private static final Logger log = LoggerFactory.getLogger(StockPriceServiceProviderRandom.class);

    @Override
    public String fetchStockOnline(String ticker) {
        return getStock(ticker);
    }

    private String getStock(String ticker){
        Random random = new Random();
        String tickerValue = String.valueOf(random.nextInt(1000));
        log.debug("Ticker pair returned: {}, {}", ticker, tickerValue);
        return tickerValue;
    }
}
