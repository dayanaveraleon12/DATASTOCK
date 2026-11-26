package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrasladoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static Traslado getTrasladoSample1() {
        return new Traslado().id(1L).descripcion("descripcion1");
    }

    public static Traslado getTrasladoSample2() {
        return new Traslado().id(2L).descripcion("descripcion2");
    }

    public static Traslado getTrasladoRandomSampleGenerator() {
        return new Traslado().id(longCount.incrementAndGet()).descripcion(UUID.randomUUID().toString());
    }
}
