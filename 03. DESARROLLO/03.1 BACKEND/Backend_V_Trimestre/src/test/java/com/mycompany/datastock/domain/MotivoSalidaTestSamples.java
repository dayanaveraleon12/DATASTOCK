package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MotivoSalidaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static MotivoSalida getMotivoSalidaSample1() {
        return new MotivoSalida().id(1L).tipoMotivo("tipoMotivo1");
    }

    public static MotivoSalida getMotivoSalidaSample2() {
        return new MotivoSalida().id(2L).tipoMotivo("tipoMotivo2");
    }

    public static MotivoSalida getMotivoSalidaRandomSampleGenerator() {
        return new MotivoSalida().id(longCount.incrementAndGet()).tipoMotivo(UUID.randomUUID().toString());
    }
}
