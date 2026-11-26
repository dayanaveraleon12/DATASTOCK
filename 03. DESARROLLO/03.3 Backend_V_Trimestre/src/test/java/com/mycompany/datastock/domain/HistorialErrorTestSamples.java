package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HistorialErrorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static HistorialError getHistorialErrorSample1() {
        return new HistorialError().id(1L).nombreError("nombreError1").mensaje("mensaje1");
    }

    public static HistorialError getHistorialErrorSample2() {
        return new HistorialError().id(2L).nombreError("nombreError2").mensaje("mensaje2");
    }

    public static HistorialError getHistorialErrorRandomSampleGenerator() {
        return new HistorialError()
            .id(longCount.incrementAndGet())
            .nombreError(UUID.randomUUID().toString())
            .mensaje(UUID.randomUUID().toString());
    }
}
