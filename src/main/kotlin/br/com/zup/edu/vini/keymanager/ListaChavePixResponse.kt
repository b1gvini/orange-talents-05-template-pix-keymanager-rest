package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.ListaPixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavePixResponse(chavePix: ListaPixResponse.ChavePix) {

    val id = chavePix.pixId
    val chave = chavePix.chave
    val tipo = chavePix.tipoChave
    val tipoConta = chavePix.tipoConta
    val criadaEm = chavePix.criadaEm.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC).toString() }
}