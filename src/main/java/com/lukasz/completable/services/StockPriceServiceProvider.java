package com.lukasz.completable.services;

import com.google.common.base.Throwables;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class StockPriceServiceProvider {
    public abstract String fetchStockOnline(final String ticker);

    public Document mostRecentStockValue(final String ticker, final String url) {
        try{
            /* emulate exception just for php tag */
            if("FAIL".compareToIgnoreCase(ticker) == 0){
                throw new RuntimeException("BAD DAY MY LORD");
            }

            return Jsoup
                    .connect(url + ticker)
                    .get();
        } catch (IOException e){
            throw Throwables.propagate(e);
        }
    }
}
