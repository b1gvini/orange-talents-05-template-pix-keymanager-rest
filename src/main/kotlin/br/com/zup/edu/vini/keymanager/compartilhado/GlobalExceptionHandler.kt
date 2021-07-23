package br.com.zup.edu.vini.keymanager.compartilhado

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class GlobalExceptionHandler: ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {

        val statusCode = exception.status.code
        val statusDescription = exception.status.description ?: ""

        val(httpStatus, message) = when(statusCode){
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, statusDescription)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, "Dados da requisiçao inválidos")
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, statusDescription)
            Status.INTERNAL.code -> Pair(HttpStatus.BAD_REQUEST, "Problemas com o nosso servidor")
            else -> {
                logger.error("ERRO INESPERADO", exception)
                Pair(HttpStatus.BAD_REQUEST, "Nao foi possível completar requisiçao")
            }
        }

        return  HttpResponse
                    .status<JsonError>(httpStatus)
                    .body(JsonError(message))

    }
}