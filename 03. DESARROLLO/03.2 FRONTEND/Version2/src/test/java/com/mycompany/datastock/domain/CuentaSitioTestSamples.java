package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CuentaSitioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static CuentaSitio getCuentaSitioSample1() {
        return new CuentaSitio().id(1L);
    }

    public static CuentaSitio getCuentaSitioSample2() {
        return new CuentaSitio().id(2L);
    }

    public static CuentaSitio getCuentaSitioRandomSampleGenerator() {
        return new CuentaSitio().id(longCount.incrementAndGet());
    }
}
