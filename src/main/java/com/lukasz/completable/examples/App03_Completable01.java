package com.lukasz.completable.examples;

import com.lukasz.completable.services.StockPriceServiceProvider;
import com.lukasz.completable.services.StockPriceServiceProviderNasdaq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lukasz.completable.utils.Deli.deli;
import static com.lukasz.completable.utils.TimeIt.timed;

public class App03_Completable01 {

    private static final Logger log = LoggerFactory.getLogger(App02_Blocking.class);

    final private StockPriceServiceProvider stockPriceServiceProvider;

    public App03_Completable01(StockPriceServiceProvider stockPriceServiceProvider) {
        this.stockPriceServiceProvider = stockPriceServiceProvider;
    }

    public static void main(String[] args) throws Exception {
        // simple one
        deli("*", 40, log::debug);
        completed();
        deli("*", 40, log::debug);
        supplyAsync();
        deli("*", 40, log::debug);
        supplyAsyncWithCustomExecutor();
        deli("*", 40, log::debug);

    }

    public static void completed() throws Exception {
        final CompletableFuture<Integer> justValue =
                CompletableFuture.completedFuture(999);
        // doesn't block
        final int nineNineNine = justValue.get();
        log.debug("Simple placeholder completable: {}", nineNineNine);
    }

    public static void supplyAsync() throws Exception {
        App03_Completable01 app03_completable01 =
                new App03_Completable01(new StockPriceServiceProviderNasdaq());
        final CompletableFuture<String> GOOGL =
                CompletableFuture.supplyAsync(()->
                    timed("GOOGL by supplyAsync", log::debug,
                            () -> app03_completable01.stockPriceServiceProvider.fetchStockOnline("GOOGL"))
        );
        log.debug("GOOGL by supplyAsync: {}", GOOGL.get());
    }

    public static void supplyAsyncWithCustomExecutor() throws Exception {
        App03_Completable01 app03_completable01 =
                new App03_Completable01(new StockPriceServiceProviderNasdaq());
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final CompletableFuture<String> GOOGL =
                CompletableFuture.supplyAsync(()->
                        timed("GOOGL by supplyAsync", log::debug,
                                () -> app03_completable01.stockPriceServiceProvider.fetchStockOnline("GOOGL")),
                        executorService
                );
        log.debug("GOOGL by supplyAsync with custom executorService: {}", GOOGL.get());
        executorService.shutdown();
    }
}
