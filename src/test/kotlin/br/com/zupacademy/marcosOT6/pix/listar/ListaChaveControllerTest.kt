package br.com.zupacademy.marcosOT6.pix.listar

import br.com.zupacademy.marcosOT6.pix.*
import br.com.zupacademy.marcosOT6.pix.listar.grpc.ListaChaveEndpoint
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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.*

@MicronautTest
internal class ListaChaveControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var listaChaveGrpc: PixServiceListagemGrpc.PixServiceListagemBlockingStub

    @Test
    fun `Deve listar as chaves cadastradas de um usuario`() {
        val codigoInterno = UUID.randomUUID().toString()
        val requestGrpc = ListarChavesPixRequest.newBuilder().setCodigoInterno(codigoInterno).build()
        val responseGrpc = responseGrpc(codigoInterno)

        Mockito.`when`(listaChaveGrpc.listarChaves(requestGrpc)).thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("api/v1/clientes/${codigoInterno}/pix/")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertNotNull(response.body())
        assertEquals(HttpStatus.OK, response.status)
    }


    private fun responseGrpc(codigoInterno: String): ListarChavesPixResponse {
        val idChave = UUID.randomUUID().toString()
        val chaves = listOf<Chave>(
            Chave
                .newBuilder()
                .setCodigoInterno(codigoInterno)
                .setPixId(idChave)
                .setTipoDeChave(TipoDeChave.CPF)
                .setValorDaChave("00077825678")
                .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                .setDataCriacao(LocalDateTime.now().toString())
                .build(),
            Chave
                .newBuilder()
                .setCodigoInterno(codigoInterno)
                .setPixId(idChave)
                .setTipoDeChave(TipoDeChave.EMAIL)
                .setValorDaChave("frodo_bolseiro@bolsista.com.br")
                .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                .setDataCriacao(LocalDateTime.now().toString())
                .build(),
            Chave
                .newBuilder()
                .setCodigoInterno(codigoInterno)
                .setPixId(idChave)
                .setTipoDeChave(TipoDeChave.CHAVE_ALEATORIA)
                .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                .setDataCriacao(LocalDateTime.now().toString())
                .build(),
        )

        return ListarChavesPixResponse
                    .newBuilder()
                    .addAllChaves(chaves)
                    .build()

    }

    @Factory
    @Replaces(factory = ListaChaveEndpoint::class)
    internal class ClientGrpc() {
        @Singleton
        fun blockingStubConfig() = Mockito.mock(PixServiceListagemGrpc.PixServiceListagemBlockingStub::class.java)
    }
}