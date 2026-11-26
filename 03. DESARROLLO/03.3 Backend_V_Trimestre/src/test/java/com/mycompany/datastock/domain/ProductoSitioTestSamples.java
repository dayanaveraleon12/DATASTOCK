package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductoSitioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static ProductoSitio getProductoSitioSample1() {
        return new ProductoSitio().id(1L).cantidadSitio(1);
    }

    public static ProductoSitio getProductoSitioSample2() {
        return new ProductoSitio().id(2L).cantidadSitio(2);
    }

    public static ProductoSitio getProductoSitioRandomSampleGenerator() {
        return new ProductoSitio().id(longCount.incrementAndGet()).cantidadSitio(intCount.incrementAndGet());
    }
}
