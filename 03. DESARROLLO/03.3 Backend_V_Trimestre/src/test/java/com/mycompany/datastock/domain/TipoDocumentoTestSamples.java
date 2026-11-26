package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TipoDocumentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static TipoDocumento getTipoDocumentoSample1() {
        return new TipoDocumento().id(1L).tipoDocumento("tipoDocumento1");
    }

    public static TipoDocumento getTipoDocumentoSample2() {
        return new TipoDocumento().id(2L).tipoDocumento("tipoDocumento2");
    }

    public static TipoDocumento getTipoDocumentoRandomSampleGenerator() {
        return new TipoDocumento().id(longCount.incrementAndGet()).tipoDocumento(UUID.randomUUID().toString());
    }
}
