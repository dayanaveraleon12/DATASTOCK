package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static Lote getLoteSample1() {
        return new Lote()
            .id(1L)
            .codigoLote("codigoLote1")
            .descripcion("descripcion1")
            .facturaProveedor("facturaProveedor1")
            .cantidadIngresada(1);
    }

    public static Lote getLoteSample2() {
        return new Lote()
            .id(2L)
            .codigoLote("codigoLote2")
            .descripcion("descripcion2")
            .facturaProveedor("facturaProveedor2")
            .cantidadIngresada(2);
    }

    public static Lote getLoteRandomSampleGenerator() {
        return new Lote()
            .id(longCount.incrementAndGet())
            .codigoLote(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .facturaProveedor(UUID.randomUUID().toString())
            .cantidadIngresada(intCount.incrementAndGet());
    }
}
