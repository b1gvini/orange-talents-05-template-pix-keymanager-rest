package br.com.zup.edu.vini.keymanager.compartilhado.grpc

import br.com.zup.b1gvini.CarregaGrpcServiceGrpc
import br.com.zup.b1gvini.ListaGrpcServiceGrpc
import br.com.zup.b1gvini.RegistraGrpcServiceGrpc
import br.com.zup.b1gvini.RemoveGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("pixManager")val channel: ManagedChannel) {

    /*
      ===== mesma coisa que isso =====
        fun registraChave(){
            return RegistraGrpcServiceGrpc.newBlockingStub(channel)
        }
    */
    @Singleton
    fun registraChave() = RegistraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChave() = RemoveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = CarregaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = ListaGrpcServiceGrpc.newBlockingStub(channel)


}