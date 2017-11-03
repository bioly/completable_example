package com.lukasz.completable.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceServiceProviderMktWatch extends StockPriceServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(StockPriceServiceProviderMktWatch.class);
    private static final String STOCK_URL = "https://www.marketwatch.com/investing/stock/";

    @Override
    public String fetchStockOnline(String ticker) {
        return getStock(ticker);
    }

    private String getStock(String ticker){
        Document document = mostRecentStockValue(ticker, STOCK_URL);
        Elements metaTags = document.getElementsByTag("meta");
        String tickerValue = String.valueOf(0.0);
        for (Element metaTag : metaTags) {
            if(metaTag.attr("name").equalsIgnoreCase("price")){
                tickerValue = metaTag.attr("content");
                break;
            }
        }
        log.debug("Ticker pair returned: {}, {}", ticker, tickerValue);
        return tickerValue;
    }

}
