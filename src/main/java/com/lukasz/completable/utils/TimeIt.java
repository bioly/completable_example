package com.lukasz.completable.utils;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimeIt {

    public static <T> T timed(String description,
                              Supplier<T> code){
        Consumer<String> defaultOutput = System.out::println;
        return timed(description, defaultOutput, code);
    }

    public static <T> T timed(String description,
                              Consumer<String> output,
                              Supplier<T> code){
        final Date before = new Date();
        T result = code.get();
        final Long duration =
                new Date().getTime() - before.getTime();
        output.accept(description + " took " + duration + " [milliseconds] ");
        return result;
    }
}
