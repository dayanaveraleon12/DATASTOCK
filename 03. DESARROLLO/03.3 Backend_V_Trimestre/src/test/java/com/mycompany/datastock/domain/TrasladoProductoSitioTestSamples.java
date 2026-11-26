package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TrasladoProductoSitioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static TrasladoProductoSitio getTrasladoProductoSitioSample1() {
        return new TrasladoProductoSitio().id(1L).cantidadTraslado(1);
    }

    public static TrasladoProductoSitio getTrasladoProductoSitioSample2() {
        return new TrasladoProductoSitio().id(2L).cantidadTraslado(2);
    }

    public static TrasladoProductoSitio getTrasladoProductoSitioRandomSampleGenerator() {
        return new TrasladoProductoSitio().id(longCount.incrementAndGet()).cantidadTraslado(intCount.incrementAndGet());
    }
}
