package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RemisionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static Remision getRemisionSample1() {
        return new Remision().id(1L).cantidadRemitida(1);
    }

    public static Remision getRemisionSample2() {
        return new Remision().id(2L).cantidadRemitida(2);
    }

    public static Remision getRemisionRandomSampleGenerator() {
        return new Remision().id(longCount.incrementAndGet()).cantidadRemitida(intCount.incrementAndGet());
    }
}
