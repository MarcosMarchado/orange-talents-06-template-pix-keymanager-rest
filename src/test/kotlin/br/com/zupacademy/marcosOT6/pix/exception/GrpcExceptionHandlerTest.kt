package br.com.zupacademy.marcosOT6.pix.exception

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class GrpcExceptionHandlerTest {


    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    fun `Deve retornar um UNPROCESSABLE_ENTITY quando a chave ja existir`(){

        val mensagem: StatusRuntimeException = Status.ALREADY_EXISTS
            .augmentDescription("A chave já está cadastrada.")
            .asRuntimeException()

        val error: HttpResponse<Any> = GrpcExceptionHandler().handle(requestGenerica, mensagem)

        with(error){
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem.status.description, (body() as JsonError).message)
        }

    }

    @Test
    fun `Deve retornar um BAD_REQUEST quando for passada alguma propriedade invalida`(){

        val mensagem: StatusRuntimeException = Status.INVALID_ARGUMENT
            .augmentDescription("Código do cliente obrigatório.")
            .asRuntimeException()

        val error: HttpResponse<Any> = GrpcExceptionHandler().handle(requestGenerica, mensagem)

        with(error){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem.status.description, (body() as JsonError).message)
        }
    }

    @Test
    fun `Deve retornar um NOT_FOUND quando a conta nao for encontrada`(){

        val mensagem: StatusRuntimeException = Status.NOT_FOUND
            .augmentDescription("Conta não encontrada.")
            .asRuntimeException()

        val error: HttpResponse<Any> = GrpcExceptionHandler().handle(requestGenerica, mensagem)

        with(error){
            Assertions.assertEquals(HttpStatus.NOT_FOUND, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem.status.description, (body() as JsonError).message)
        }
    }

    @Test
    fun `Deve retornar um INTERNAL_SERVER_ERROR quando ocorrer um error desconhecido`(){

        val mensagem: StatusRuntimeException = Status.INTERNAL
            .augmentDescription("Erro inesperado.")
            .asRuntimeException()

        val error: HttpResponse<Any> = GrpcExceptionHandler().handle(requestGenerica, mensagem)

        with(error){
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem.status.description, (body() as JsonError).message)
        }
    }
}