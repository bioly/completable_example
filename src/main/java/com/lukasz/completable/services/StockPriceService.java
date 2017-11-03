package com.lukasz.completable.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class StockPriceService {

    public static final Logger log = LoggerFactory.getLogger(StockPriceService.class);

    private Optional<StockPriceServiceProvider> stockPriceServiceProviderOpt;

    public StockPriceService(StockPriceServiceProvider stockPriceServiceProvider) {
        this.stockPriceServiceProviderOpt = Optional.of(stockPriceServiceProvider);
    }

    public String fetchStockRecentValue(String ticker){
        if(stockPriceServiceProviderOpt.isPresent()){
            return stockPriceServiceProviderOpt.get().fetchStockOnline(ticker);
        }
        log.error("Fetch stock price failed for symbol: {}", ticker);
        return String.valueOf(0.0);
    }
}
