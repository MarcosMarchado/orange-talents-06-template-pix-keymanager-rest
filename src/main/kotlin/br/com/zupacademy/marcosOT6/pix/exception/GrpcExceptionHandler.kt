package br.com.zupacademy.marcosOT6.pix.exception

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Singleton
class GrpcExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        return when (exception.status.code) {
            Status.ALREADY_EXISTS.code -> {
                HttpResponse.status<JsonError>(HttpStatus.UNPROCESSABLE_ENTITY).body(JsonError(exception.status.description))
            }
            Status.INVALID_ARGUMENT.code -> {
                HttpResponse.status<JsonError>(HttpStatus.BAD_REQUEST).body(JsonError(exception.status.description))
            }
            Status.NOT_FOUND.code -> {
                HttpResponse.status<JsonError>(HttpStatus.NOT_FOUND).body(JsonError(exception.status.description))
            }
            else -> HttpResponse.status<JsonError>(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonError("Erro inesperado."))
        }
    }

}