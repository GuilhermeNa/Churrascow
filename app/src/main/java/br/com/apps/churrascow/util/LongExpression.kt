package br.com.apps.churrascow.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

fun Long.toLocalDateTime() =

         Instant.ofEpochMilli(this).atZone(ZoneId.of("America/Sao_Paulo"))
             .withZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
             .toLocalDateTime()


