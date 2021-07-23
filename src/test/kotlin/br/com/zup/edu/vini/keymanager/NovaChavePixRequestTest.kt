package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.RegistraGrpcServiceGrpc
import br.com.zup.b1gvini.RegistraPixResponse
import br.com.zup.edu.vini.keymanager.compartilhado.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class NovaChavePixRequestTest{

    @field:Inject
    lateinit var registraStub: RegistraGrpcServiceGrpc.RegistraGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    class RegistraPixClientFactory {
        @Singleton
        fun stubMock() = Mockito.mock(RegistraGrpcServiceGrpc.RegistraGrpcServiceBlockingStub::class.java)
    }

    @BeforeEach
    internal fun setUp() {
        Mockito.reset(registraStub)
    }

    val pixRequest = NovaChavePixRequest(
        tipoChave = TipoChaveRequest.EMAIL,
        tipoConta = TipoContaRequest.CONTA_CORRENTE,
        chave = "vini@mail.com"
    )

    @Test
    internal fun `deve registrar uma nova chave pix`() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RegistraPixResponse.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .build()

        Mockito.`when`(registraStub.registra(pixRequest.paraGrpc(clientId))).thenReturn(respostaGrpc)


        val request = HttpRequest.POST("/api/v1/clientes/$clientId/pix", pixRequest)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("location"))
        assertTrue(response.header("location")!!.contains(pixId))
    }

    //TODO testes de caminhos alternativos

}