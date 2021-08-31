package br.com.zupacademy.marcosOT6.pix.cadastrar

import br.com.zupacademy.marcosOT6.pix.PixServiceGrpc
import br.com.zupacademy.marcosOT6.pix.cadastrar.dto.CadastraChaveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{codigoInterno}/pix")
class CadastraChaveController(val cadastraChaveEndpointGrpc: PixServiceGrpc.PixServiceBlockingStub) {

    val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    @Post
    fun cadastra(codigoInterno:String, @Body @Valid cadastraChaveRequest: CadastraChaveRequest): HttpResponse<Any> {
        
        val requestGrpc = cadastraChaveRequest.toRequestGrpc(codigoInterno)
        val responseGrpc = cadastraChaveEndpointGrpc.cadastrarChave(requestGrpc)
        val uri: URI = HttpResponse.uri("/api/v1/clientes/${requestGrpc.codigoInterno}/pix/${responseGrpc.pixId}")
        return HttpResponse.created(uri)
    }
}