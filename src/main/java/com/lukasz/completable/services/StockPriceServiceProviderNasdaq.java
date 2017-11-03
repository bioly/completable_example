package com.lukasz.completable.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceServiceProviderNasdaq extends StockPriceServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(StockPriceServiceProviderNasdaq.class);
    private static final String STOCK_URL = "http://www.nasdaq.com/symbol/";

    @Override
    public String fetchStockOnline(String ticker) {
        return getStock(ticker);
    }

    private String getStock(String ticker){
        Document document = mostRecentStockValue(ticker, STOCK_URL);
        Element div = document.select("div#qwidget_lastsale").first();
        String tickerValue = div.text();
        log.debug("Ticker pair returned: {}, {}", ticker, tickerValue);
        return tickerValue;
    }
}
