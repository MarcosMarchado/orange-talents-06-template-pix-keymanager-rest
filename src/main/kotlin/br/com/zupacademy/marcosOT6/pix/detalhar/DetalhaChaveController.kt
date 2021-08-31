package br.com.zupacademy.marcosOT6.pix.detalhar

import br.com.zupacademy.marcosOT6.pix.DetalhesChavePixRequest
import br.com.zupacademy.marcosOT6.pix.PixServiceDetalhesGrpc
import br.com.zupacademy.marcosOT6.pix.detalhar.dto.DetalhaChaveResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/v1/clientes/{codigoInterno}/pix/{idChave}")
class DetalhaChaveController(val detalhaChaveEndpointGrpc: PixServiceDetalhesGrpc.PixServiceDetalhesBlockingStub) {

    private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    @Get
    fun detalhar(codigoInterno: String, idChave: String): HttpResponse<DetalhaChaveResponse> {
        val filtroPorPixId = DetalhesChavePixRequest
                                    .FiltroPorPixId
                                    .newBuilder()
                                    .setCodigoInterno(codigoInterno)
                                    .setPixId(idChave)
                                    .build()

        val requestGrpc = DetalhesChavePixRequest
                                    .newBuilder()
                                    .setKeyManager(filtroPorPixId)
                                    .build()

        val responseGrpc = detalhaChaveEndpointGrpc.detalharChave(requestGrpc)
        return HttpResponse.ok(DetalhaChaveResponse(responseGrpc))
    }

}