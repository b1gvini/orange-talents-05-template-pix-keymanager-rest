package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.CarregaPixResponse
import br.com.zup.b1gvini.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


class DetalhesChavePixResponse(chaveResponse: CarregaPixResponse) {

    val pixId = chaveResponse.pixId
    val tipo = chaveResponse.chave.tipo
    val chave = chaveResponse.chave.chave

    val criadaEm = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC).toString()
    }

    val tipoConta = when(chaveResponse.chave.conta.tipo){
        TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "DESCONHECIDA"
    }

    val conta = mapOf(
        Pair("tipo", tipoConta),
        Pair("instituicao", chaveResponse.chave.conta.instituicao),
        Pair("titularNome", chaveResponse.chave.conta.titularNome),
        Pair("titularCpf", chaveResponse.chave.conta.titularCpf),
        Pair("agencia", chaveResponse.chave.conta.agencia),
        Pair("numeroConta", chaveResponse.chave.conta.numeroConta)
    )
}