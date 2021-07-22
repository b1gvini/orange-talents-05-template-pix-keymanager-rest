package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.RegistraGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clientId}")
class RegistraChavePixController(private val registraChavePixClient: RegistraGrpcServiceGrpc.RegistraGrpcServiceBlockingStub) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun create(clientId: String, @Valid @Body request: NovaChavePixRequest) : HttpResponse<Any> {

        logger.info("criando nova pix request $request")
        val grpcResponse = registraChavePixClient.registra(request.paraGrpc(clientId))

        return HttpResponse.created(location(clientId, grpcResponse.pixId))
    }

    private fun location(clientId: String, pixId: String) = HttpResponse.uri("/api/v1/clientes/$clientId/pix/${pixId}")
}