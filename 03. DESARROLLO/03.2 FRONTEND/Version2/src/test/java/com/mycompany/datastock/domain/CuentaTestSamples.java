package com.mycompany.datastock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CuentaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    public static Cuenta getCuentaSample1() {
        return new Cuenta()
            .id(1L)
            .numeroDocumento("numeroDocumento1")
            .primerNombre("primerNombre1")
            .segundoNombre("segundoNombre1")
            .primerApellido("primerApellido1")
            .segundoApellido("segundoApellido1")
            .numeroCelular("numeroCelular1")
            .eps("eps1")
            .contactoEmergencia("contactoEmergencia1")
            .parentezcoEmergencia("parentezcoEmergencia1")
            .nombreEmergencia("nombreEmergencia1");
    }

    public static Cuenta getCuentaSample2() {
        return new Cuenta()
            .id(2L)
            .numeroDocumento("numeroDocumento2")
            .primerNombre("primerNombre2")
            .segundoNombre("segundoNombre2")
            .primerApellido("primerApellido2")
            .segundoApellido("segundoApellido2")
            .numeroCelular("numeroCelular2")
            .eps("eps2")
            .contactoEmergencia("contactoEmergencia2")
            .parentezcoEmergencia("parentezcoEmergencia2")
            .nombreEmergencia("nombreEmergencia2");
    }

    public static Cuenta getCuentaRandomSampleGenerator() {
        return new Cuenta()
            .id(longCount.incrementAndGet())
            .numeroDocumento(UUID.randomUUID().toString())
            .primerNombre(UUID.randomUUID().toString())
            .segundoNombre(UUID.randomUUID().toString())
            .primerApellido(UUID.randomUUID().toString())
            .segundoApellido(UUID.randomUUID().toString())
            .numeroCelular(UUID.randomUUID().toString())
            .eps(UUID.randomUUID().toString())
            .contactoEmergencia(UUID.randomUUID().toString())
            .parentezcoEmergencia(UUID.randomUUID().toString())
            .nombreEmergencia(UUID.randomUUID().toString());
    }
}
