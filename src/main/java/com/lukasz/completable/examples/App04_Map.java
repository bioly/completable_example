package com.lukasz.completable.examples;

import com.lukasz.completable.services.StockPriceServiceProvider;
import com.lukasz.completable.services.StockPriceServiceProviderMktWatch;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lukasz.completable.utils.Deli.deli;
import static com.lukasz.completable.utils.TimeIt.timed;

public class App04_Map {
    private static final Logger log = LoggerFactory.getLogger(App04_Map.class);
    private final static ExecutorService executorService = Executors.newSingleThreadExecutor();
    final private StockPriceServiceProvider stockPriceServiceProvider;

    public App04_Map(StockPriceServiceProvider stockPriceServiceProvider) {
        this.stockPriceServiceProvider = stockPriceServiceProvider;
    }

    public static void main(String[] args) throws Exception {
        deli("#", 40, log::debug);
        oldWayOfComputingStock();
        deli("#", 40, log::debug);
        callbackExample();
        deli("#", 40, log::debug);
        thenApplyExample();
        deli("#", 40, log::debug);
        thenApplyExample();
        deli("#", 40, log::debug);

        executorService.shutdown();
    }

    public static void oldWayOfComputingStock() throws Exception {
        App04_Map  app04_map =
                new App04_Map(new StockPriceServiceProviderMktWatch());
        final CompletableFuture<Document> GOOGL =
                CompletableFuture.supplyAsync(()->
                                timed("GOOGL by supplyAsync", log::debug,
                                        () -> app04_map.stockPriceServiceProvider.mostRecentStockValue("GOOGL",
                                                "https://www.marketwatch.com/investing/stock/")),
                        executorService
                );

        final Document document = GOOGL.get();
        final Element element = document.select("body").get(0);
        final String title = element.text();
        final int length = title.length();

        log.debug("Title: {}, length: {}", title, length);
    }

    public static void callbackExample() throws Exception {
        App04_Map  app04_map =
                new App04_Map(new StockPriceServiceProviderMktWatch());
        final CompletableFuture<Document> GOOGL =
                CompletableFuture.supplyAsync(()->
                                timed("GOOGL by supplyAsync", log::debug,
                                        () -> app04_map.stockPriceServiceProvider.mostRecentStockValue("SABR",
                                                "https://www.marketwatch.com/investing/stock/")),
                        executorService
                );

        GOOGL.thenAccept((document) -> log.debug("Document content after thenAccept: {}", document));
    }

    public static void thenApplyExample() throws Exception {
        App04_Map  app04_map =
                new App04_Map(new StockPriceServiceProviderMktWatch());
        final CompletableFuture<Document> GOOGL =
                CompletableFuture.supplyAsync(()->
                                timed("GOOGL by supplyAsync", log::debug,
                                        () -> app04_map.stockPriceServiceProvider.mostRecentStockValue("SABR",
                                                "https://www.marketwatch.com/investing/stock/")),
                        executorService
                );

        final CompletableFuture<Element> titleElement =
                GOOGL.thenApply((Document document) ->
                    document.select("body").get(0));

        final CompletableFuture<String> titleText =
                titleElement.thenApply(Element::text);

        final CompletableFuture<Integer> length =
                titleText.thenApply(String::length);

        log.debug("Length is: {}", length.get());
    }

    public static void thenApplyChainingExample() throws Exception {

        App04_Map  app04_map =
                new App04_Map(new StockPriceServiceProviderMktWatch());

        final CompletableFuture<Document> GOOGL =
                CompletableFuture.supplyAsync(()->
                                timed("GOOGL by supplyAsync", log::debug,
                                        () -> app04_map.stockPriceServiceProvider.mostRecentStockValue("SABR",
                                                "https://www.marketwatch.com/investing/stock/")),
                        executorService
                );

        final CompletableFuture<Integer> length =
                GOOGL.thenApply(doc -> doc.select("body").get(0))
                        .thenApply(Element::text)
                        .thenApply((String::length));

        log.debug("Length after chaining: {}", length.get());

    }
}
