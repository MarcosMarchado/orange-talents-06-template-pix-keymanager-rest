package br.com.zupacademy.marcosOT6.pix.listar

import br.com.zupacademy.marcosOT6.pix.ListarChavesPixRequest
import br.com.zupacademy.marcosOT6.pix.PixServiceListagemGrpc
import br.com.zupacademy.marcosOT6.pix.listar.dto.ListaChaveResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/v1/clientes/{codigoInterno}/pix")
class ListaChaveController(val listaChaveEndpointGrpc: PixServiceListagemGrpc.PixServiceListagemBlockingStub) {

    private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java)

    @Get
    fun lista(codigoInterno: String): HttpResponse<ListaChaveResponse> {

        val requestGrpc = ListarChavesPixRequest
                                .newBuilder()
                                .setCodigoInterno(codigoInterno)
                                .build()

        val responseGrpc = listaChaveEndpointGrpc.listarChaves(requestGrpc)
        val response = ListaChaveResponse.toResponse(responseGrpc.chavesList)
        return HttpResponse.ok(response)
    }

}