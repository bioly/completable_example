package com.lukasz.completable.examples;


import com.lukasz.completable.services.StockPriceServiceProvider;
import com.lukasz.completable.services.StockPriceServiceProviderMktWatch;
import com.lukasz.completable.services.StockPriceServiceProviderNasdaq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static com.lukasz.completable.utils.TimeIt.timed;

public class App02_Blocking {
    private static final Logger log = LoggerFactory.getLogger(App02_Blocking.class);

    final private StockPriceServiceProvider stockPriceServiceProvider;

    public App02_Blocking(StockPriceServiceProvider stockPriceServiceProvider) {
        this.stockPriceServiceProvider = stockPriceServiceProvider;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        simpleBlocking();
        deli();
        simpleBlocking2();
        deli();
        simpleBlockingExecutor();
        deli();
        simpleBlockingExecutorWithTimeout();

        // next examples will be about non blocking reactive code :) the code above is not right
        // for current tech requirements
    }

    private static void deli() {
        System.out.println("********************************************************************");
    }

    public static void simpleBlocking() {
        App02_Blocking app02_blocking = new App02_Blocking(new StockPriceServiceProviderNasdaq());

        final String SABRE = "SABR";
        String stockPrice;

        // blocking execution
        stockPrice = timed("SABR", log::debug,
                () -> app02_blocking.stockPriceServiceProvider.fetchStockOnline(SABRE));
        log.debug("Stocking price for {} : {}", SABRE, stockPrice);
    }

    public static void simpleBlocking2() {
        App02_Blocking app02_blocking = new App02_Blocking(new StockPriceServiceProviderMktWatch());

        final String SABRE = "SABR";
        String stockPrice;

        // blocking execution
        stockPrice = timed("SABR", log::debug,
                () -> app02_blocking.stockPriceServiceProvider.fetchStockOnline(SABRE));
        log.debug("Stocking price for {} : {}", SABRE, stockPrice);
    }

    public static void simpleBlockingExecutor() throws ExecutionException, InterruptedException {
        App02_Blocking app02_blocking = new App02_Blocking(new StockPriceServiceProviderMktWatch());

        final String SABRE = "SABR";
        final ExecutorService executorService =
                Executors.newSingleThreadExecutor();
        final Callable<String> task = () -> app02_blocking.stockPriceServiceProvider.fetchStockOnline(SABRE);
        final Future<String> stockFuture = executorService.submit(task);

        log.debug("SABR price: {}", stockFuture.get());

        executorService.shutdown();
    }

    public static void simpleBlockingExecutorWithTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        App02_Blocking app02_blocking = new App02_Blocking(new StockPriceServiceProviderMktWatch());

        final String SABRE = "SABR";
        final ExecutorService executorService =
                Executors.newSingleThreadExecutor();
        final Callable<String> task = () -> app02_blocking.stockPriceServiceProvider.fetchStockOnline(SABRE);
        final Future<String> stockFuture = executorService.submit(task);

        log.debug("SABR price: {}", stockFuture.get(2, TimeUnit.SECONDS));

        executorService.shutdown();
    }

}
