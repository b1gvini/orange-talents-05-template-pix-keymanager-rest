package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.RemoveGrpcServiceGrpc
import br.com.zup.b1gvini.RemovePixRequest
import br.com.zup.b1gvini.RemovePixResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.LoggerFactory

@Controller("/api/v1/clientes/{clientId}")
class RemoveChavePixController(private val removeChavePixClient: RemoveGrpcServiceGrpc.RemoveGrpcServiceBlockingStub) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Delete("pix/{pixId}")
    fun delete(clientId: String, pixId: String): HttpResponse<Any>{
        logger.info("tentnado remover chavepix")
        removeChavePixClient.remove(RemovePixRequest.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .build())
        logger.info("chave pix removida com sucesso")
        return HttpResponse.ok()
    }
}