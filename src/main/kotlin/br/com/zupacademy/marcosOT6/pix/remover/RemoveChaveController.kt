package br.com.zupacademy.marcosOT6.pix.remover

import br.com.zupacademy.marcosOT6.pix.PixServiceRemoverGrpc
import br.com.zupacademy.marcosOT6.pix.RemoverChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete

@Controller("/api/v1/clientes/{codigoInterno}/pix/{idChave}")
class RemoveChaveController(val removeChaveEndpointGrpc: PixServiceRemoverGrpc.PixServiceRemoverBlockingStub) {

    @Delete
    fun remover(codigoInterno: String, idChave: String): HttpResponse<Any> {
        val requestGrpc = RemoverChavePixRequest
            .newBuilder()
            .setCodigoInterno(codigoInterno)
            .setPixId(idChave)
            .build()

        removeChaveEndpointGrpc.removerChave(requestGrpc)
        return HttpResponse.ok()
    }


}