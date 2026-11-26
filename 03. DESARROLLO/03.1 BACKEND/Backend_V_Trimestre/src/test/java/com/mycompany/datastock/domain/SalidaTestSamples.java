package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SalidaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + 2 * Short.MAX_VALUE);

    public static Salida getSalidaSample1() {
        return new Salida().id(1L).respaldoImagen("respaldoImagen1").cantidadASacar(1).observaciones("observaciones1");
    }

    public static Salida getSalidaSample2() {
        return new Salida().id(2L).respaldoImagen("respaldoImagen2").cantidadASacar(2).observaciones("observaciones2");
    }

    public static Salida getSalidaRandomSampleGenerator() {
        return new Salida()
            .id(longCount.incrementAndGet())
            .respaldoImagen(UUID.randomUUID().toString())
            .cantidadASacar(intCount.incrementAndGet())
            .observaciones(UUID.randomUUID().toString());
    }
}
