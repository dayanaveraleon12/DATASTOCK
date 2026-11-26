package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EvidenciaSalidaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static EvidenciaSalida getEvidenciaSalidaSample1() {
        return new EvidenciaSalida().id(1L).archivoExcel("archivoExcel1").nombreExcel("nombreExcel1");
    }

    public static EvidenciaSalida getEvidenciaSalidaSample2() {
        return new EvidenciaSalida().id(2L).archivoExcel("archivoExcel2").nombreExcel("nombreExcel2");
    }

    public static EvidenciaSalida getEvidenciaSalidaRandomSampleGenerator() {
        return new EvidenciaSalida()
            .id(longCount.incrementAndGet())
            .archivoExcel(UUID.randomUUID().toString())
            .nombreExcel(UUID.randomUUID().toString());
    }
}
