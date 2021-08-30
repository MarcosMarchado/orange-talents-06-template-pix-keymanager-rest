package br.com.zupacademy.marcosOT6.pix.exception

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler::class)
class ApplicationExceptionHandler() : ExceptionHandler<ConstraintViolationException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>, exception: ConstraintViolationException): HttpResponse<Any> {
        val mensagensDeError: List<String> = exception.constraintViolations.map { "${it.propertyPath.last()} ${it.message}" }
        return HttpResponse.status<ErrorPadrao>(HttpStatus.BAD_REQUEST).body(ErrorPadrao(mensagensDeError))
    }

}