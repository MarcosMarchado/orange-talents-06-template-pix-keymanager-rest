package br.com.zupacademy.marcosOT6.pix.remover

import br.com.zupacademy.marcosOT6.pix.PixServiceRemoverGrpc
import br.com.zupacademy.marcosOT6.pix.RemoverChavePixRequest
import br.com.zupacademy.marcosOT6.pix.RemoverChavePixResponse
import br.com.zupacademy.marcosOT6.pix.remover.grpc.RemoveChaveEndpointGrpc
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class RemoveChaveControllerTest{

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var removeChaveGrpc: PixServiceRemoverGrpc.PixServiceRemoverBlockingStub

    @Test
    fun `Deve remover uma chave pix`(){
        val codigoInterno = UUID.randomUUID().toString()
        val idChave = UUID.randomUUID().toString()

        val requestGrpc = RemoverChavePixRequest.newBuilder()
            .setPixId(idChave)
            .setCodigoInterno(codigoInterno)
            .build()

        val responseGrpc = RemoverChavePixResponse.newBuilder()
            .setPixId(idChave)
            .setCodigoInterno(codigoInterno)
            .build()

        Mockito.`when`(removeChaveGrpc.removerChave(requestGrpc)).thenReturn(responseGrpc)
        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/${codigoInterno}/pix/${idChave}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = RemoveChaveEndpointGrpc::class)
    internal class ClientGrpc() {
        @Singleton
        fun blockingStubConfig() = Mockito.mock(PixServiceRemoverGrpc.PixServiceRemoverBlockingStub::class.java)
    }


}