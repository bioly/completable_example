package com.lukasz.completable.utils;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class TimeItTest {
    @Test
    public void timed() throws Exception {
        // given
        final String description = "SABR";
        AtomicReference<String> output = new AtomicReference<>();

        // when
        TimeIt.timed(description, output::set, () -> "18.55");

        // then
        assert (output.get().contains(description));
    }

}