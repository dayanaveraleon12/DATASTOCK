package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SitioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static Sitio getSitioSample1() {
        return new Sitio().id(1L).nombre("nombre1");
    }

    public static Sitio getSitioSample2() {
        return new Sitio().id(2L).nombre("nombre2");
    }

    public static Sitio getSitioRandomSampleGenerator() {
        return new Sitio().id(longCount.incrementAndGet()).nombre(UUID.randomUUID().toString());
    }
}
