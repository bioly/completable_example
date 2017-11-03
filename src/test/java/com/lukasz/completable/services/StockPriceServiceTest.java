package com.lukasz.completable.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockPriceServiceTest {

    @Mock
    StockPriceServiceProvider stockPriceServiceProvider;

    StockPriceService stockPriceService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        stockPriceService = new StockPriceService(stockPriceServiceProvider);
    }

    @Test
    public void fetchStockRecentValue() throws Exception {
        // when
        when(stockPriceService.fetchStockRecentValue("SABR")).thenReturn("100");
        // then
        String value = stockPriceService.fetchStockRecentValue("SABR");
        // when
        assertEquals("100", value);
        verify(stockPriceServiceProvider, times(1)).fetchStockOnline(anyString());
    }

}