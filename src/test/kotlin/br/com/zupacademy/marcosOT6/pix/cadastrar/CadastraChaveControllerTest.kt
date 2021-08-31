package br.com.zupacademy.marcosOT6.pix.cadastrar

import br.com.zupacademy.marcosOT6.pix.CadastraChavePixResponse
import br.com.zupacademy.marcosOT6.pix.PixServiceGrpc
import br.com.zupacademy.marcosOT6.pix.TipoDeChave
import br.com.zupacademy.marcosOT6.pix.TipoDeConta
import br.com.zupacademy.marcosOT6.pix.cadastrar.dto.CadastraChaveRequest
import br.com.zupacademy.marcosOT6.pix.cadastrar.grpc.CadastraChaveEndpointGrpc
import br.com.zupacademy.marcosOT6.pix.exception.ErrorPadrao
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.ConstraintViolationException

@MicronautTest
internal class CadastraChaveControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var cadastraChaveGrpc: PixServiceGrpc.PixServiceBlockingStub

    val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `Deve cadastrar um email como chave pix`() {

        val codigoInterno = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val cadastraChaveRequest = CadastraChaveRequest(
            "marcos@gmail.com",
            TipoDeChave.EMAIL,
            TipoDeConta.CONTA_CORRENTE
        )
        val cadastraChaveResponseGrpc = CadastraChavePixResponse.newBuilder().setPixId(pixId).build()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenReturn(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val response = this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("location"))
            assertTrue(response.header("Location")!!.contains(pixId))
        }
    }

    @Test
    fun `Deve cadastrar uma chave aleatoria como chave pix`() {
        val codigoInterno = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.CHAVE_ALEATORIA,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = null
        )
        val cadastraChaveResponseGrpc = CadastraChavePixResponse.newBuilder().setPixId(pixId).build()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenReturn(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val response = this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("location"))
            assertTrue(response.header("Location")!!.contains(pixId))
        }
    }

    @Test
    fun `Deve cadastrar um CPF como chave pix`() {
        val codigoInterno = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.CPF,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = "00088877754"
        )
        val cadastraChaveResponseGrpc = CadastraChavePixResponse.newBuilder().setPixId(pixId).build()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenReturn(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val response = this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("location"))
            assertTrue(response.header("Location")!!.contains(pixId))
        }
    }

    @Test
    fun `Deve cadastrar um Telefone como chave pix`() {
        val codigoInterno = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.TELEFONE,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = "+5598980001254"
        )
        val cadastraChaveResponseGrpc = CadastraChavePixResponse.newBuilder().setPixId(pixId).build()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenReturn(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val response = this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("location"))
            assertTrue(response.header("Location")!!.contains(pixId))
        }
    }

    /*Canários de erros*/
    @Test
    fun `Deve dar error ao tentar cadastrar uma chave pix existente`() {
        val codigoInterno = UUID.randomUUID().toString()
        val chave = "+5598980001254"

        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.TELEFONE,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = chave
        )

        val cadastraChaveResponseGrpc =
            Status.ALREADY_EXISTS.augmentDescription("A chave ${chave} já está cadastrada.").asRuntimeException()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenThrow(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status)
            assertEquals("A chave ${chave} já está cadastrada.", error.message)
        }

    }

    @Test
    fun `Deve dar error ao tentar selecionar um tipo de chave desconhecida`() {
        val codigoInterno = UUID.randomUUID().toString()
        val chave = "+5598980001254"

        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.UNKNOW_TIPO_CHAVE,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = chave
        )

        val cadastraChaveResponseGrpc =
            Status.ALREADY_EXISTS.augmentDescription("cadastraChaveRequest Tipo de chave desconhecida.")
                .asRuntimeException()
        val cadastraChaveRequestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)

        `when`(cadastraChaveGrpc.cadastrarChave(cadastraChaveRequestGrpc)).thenThrow(cadastraChaveResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)
        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
        }
    }

    @Test
    fun `Deve dar error ao passar uma chave invalida para o tipo CPF`() {
        val codigoInterno = UUID.randomUUID().toString()

        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.CPF,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = "80001254"
        )

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)

        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
            assertTrue(response.header("location").isNullOrBlank())
        }
    }

    @Test
    fun `Deve dar error ao passar uma chave invalida para o tipo EMAIL`() {
        val codigoInterno = UUID.randomUUID().toString()

        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.EMAIL,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = "marcos.com.br"
        )

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)

        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
            assertTrue(response.header("location").isNullOrBlank())
        }
    }

    @Test
    fun `Deve dar error ao passar uma chave invalida para o tipo TELEFONE`() {
        val codigoInterno = UUID.randomUUID().toString()

        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.TELEFONE,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = "80001254" /*Telefone inválido*/
        )

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)

        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
            assertTrue(response.header("location").isNullOrBlank())
        }
    }

    @Test
    fun `Deve dar error ao passar uma chave invalida para o tipo ALEATORIA`() {
        val codigoInterno = UUID.randomUUID().toString()
        val chaveAleatoria = UUID.randomUUID().toString()
        val cadastraChaveRequest = CadastraChaveRequest(
            tipoDeChave = TipoDeChave.TELEFONE,
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            valorDaChave = chaveAleatoria /*Valor precisa ser em branco*/
        )

        val request = HttpRequest.POST("/api/v1/clientes/${codigoInterno}/pix", cadastraChaveRequest)

        val error = assertThrows<HttpClientResponseException> {
            this.client.toBlocking().exchange(request, CadastraChaveRequest::class.java)
        }

        with(error) {
            assertEquals(HttpStatus.BAD_REQUEST, status)
            assertTrue(response.header("location").isNullOrBlank())
        }
    }


    @Factory
    @Replaces(factory = CadastraChaveEndpointGrpc::class)
    internal class ClientGrpc() {
        @Singleton
        fun blockingStubConfig() = Mockito.mock(PixServiceGrpc.PixServiceBlockingStub::class.java)
    }


}