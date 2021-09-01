package br.com.zupacademy.marcosOT6.pix.detalhar

import br.com.zupacademy.marcosOT6.pix.*
import br.com.zupacademy.marcosOT6.pix.detalhar.grpc.DetalhaChaveEndpointGrpc
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.*

@MicronautTest
internal class DetalhaChaveControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var detalhaChaveGrpc: PixServiceDetalhesGrpc.PixServiceDetalhesBlockingStub

    @Test
    fun `Deve detalhar uma chave pix`() {
        val codigoInterno = UUID.randomUUID().toString()
        val idChave = UUID.randomUUID().toString()

        val filtroPorPixId = DetalhesChavePixRequest
                                        .FiltroPorPixId
                                        .newBuilder()
                                        .setPixId(idChave)
                                        .setCodigoInterno(codigoInterno)
                                        .build()
        val requestGrpc = DetalhesChavePixRequest.newBuilder().setKeyManager(filtroPorPixId).build()
        val responseGrpc = responseGrpc(codigoInterno, idChave)
        Mockito.`when`(detalhaChaveGrpc.detalharChave(requestGrpc)).thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("api/v1/clientes/${codigoInterno}/pix/${idChave}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        with(response){
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
        }

    }


    private fun responseGrpc(codigoInterno: String, idChave: String): DetalhesChavePixResponse {
        val conta = Conta.newBuilder()
                        .setTitular("Naruto Uzumaki")
                        .setCPF("05588801278")
                        .setNomeDaInstituicao("ITAÚ UNIBANCO S.A.")
                        .setAgencia("0001")
                        .setNumero("291900")
                        .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
                        .build()

        return DetalhesChavePixResponse
                        .newBuilder()
                        .setPixId(idChave)
                        .setCodigoInterno(codigoInterno)
                        .setTipoDeChave(TipoDeChave.CPF)
                        .setValorDaChave("05588801278")
                        .setConta(conta)
                        .setDataCriacao(LocalDateTime.now().toString())
                        .build()
    }

    @Factory
    @Replaces(factory = DetalhaChaveEndpointGrpc::class)
    internal class ClientGrpc() {
        @Singleton
        fun blockingStubConfig() = Mockito.mock(PixServiceDetalhesGrpc.PixServiceDetalhesBlockingStub::class.java)
    }

}