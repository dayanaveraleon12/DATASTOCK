package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EstadoProductoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static EstadoProducto getEstadoProductoSample1() {
        return new EstadoProducto().id(1L).tipoEstado("tipoEstado1");
    }

    public static EstadoProducto getEstadoProductoSample2() {
        return new EstadoProducto().id(2L).tipoEstado("tipoEstado2");
    }

    public static EstadoProducto getEstadoProductoRandomSampleGenerator() {
        return new EstadoProducto().id(longCount.incrementAndGet()).tipoEstado(UUID.randomUUID().toString());
    }
}
