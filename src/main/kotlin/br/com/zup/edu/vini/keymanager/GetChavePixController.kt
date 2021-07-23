package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.CarregaGrpcServiceGrpc
import br.com.zup.b1gvini.CarregaPixRequest
import br.com.zup.b1gvini.ListaGrpcServiceGrpc
import br.com.zup.b1gvini.ListaPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory

@Controller("/api/v1/clientes/{clientId}")
class GetChavePixController(
    private val carregaChavePixClient: CarregaGrpcServiceGrpc.CarregaGrpcServiceBlockingStub,
    private val listaChavePixClient: ListaGrpcServiceGrpc.ListaGrpcServiceBlockingStub
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun carrega(clientId: String, pixId: String): HttpResponse<Any>{

        val response = carregaChavePixClient.carrega(CarregaPixRequest.newBuilder()
            .setPixId(CarregaPixRequest.FiltroPorPixId.newBuilder()
                .setPixId(pixId)
                .setClientId(clientId)
                .build())
            .build())

        return HttpResponse.ok(DetalhesChavePixResponse(response))
    }

    @Get("/pix/")
    fun lista(clientId: String) :HttpResponse<Any>{
        val pix = listaChavePixClient.lista(ListaPixRequest.newBuilder().setClientId(clientId).build())
        val listapix = pix.chavesList.map{ListaChavePixResponse(it)}

        return HttpResponse.ok(listapix)
    }
}