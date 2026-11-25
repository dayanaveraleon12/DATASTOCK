package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CodigoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static Codigo getCodigoSample1() {
        return new Codigo().id(1L).codigo("codigo1");
    }

    public static Codigo getCodigoSample2() {
        return new Codigo().id(2L).codigo("codigo2");
    }

    public static Codigo getCodigoRandomSampleGenerator() {
        return new Codigo().id(longCount.incrementAndGet()).codigo(UUID.randomUUID().toString());
    }
}
