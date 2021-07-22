package br.com.zup.edu.vini.keymanager.compartilhado.grpc

import br.com.zup.b1gvini.RegistraGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("pixManager")val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = RegistraGrpcServiceGrpc.newBlockingStub(channel)

/*
  ===== mesma coisa que isso =====
    fun registraChave(){
        return RegistraGrpcServiceGrpc.newBlockingStub(channel)
    }
*/

}