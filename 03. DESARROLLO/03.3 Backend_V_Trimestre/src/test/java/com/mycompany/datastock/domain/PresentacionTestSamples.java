package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PresentacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static Presentacion getPresentacionSample1() {
        return new Presentacion().id(1L).tipoPresentacion("tipoPresentacion1");
    }

    public static Presentacion getPresentacionSample2() {
        return new Presentacion().id(2L).tipoPresentacion("tipoPresentacion2");
    }

    public static Presentacion getPresentacionRandomSampleGenerator() {
        return new Presentacion().id(longCount.incrementAndGet()).tipoPresentacion(UUID.randomUUID().toString());
    }
}
