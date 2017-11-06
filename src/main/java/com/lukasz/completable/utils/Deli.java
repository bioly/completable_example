package com.lukasz.completable.utils;

import java.util.function.Consumer;

public class Deli {

    public static void deli(final String symbol, int times){
        deli(symbol, times, System.out::println);
    }

    public static void deli(final String symbol, int times, Consumer<String> output){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < times; i++){
            builder.append(symbol);
        }
        output.accept(builder.toString());
    }
}
