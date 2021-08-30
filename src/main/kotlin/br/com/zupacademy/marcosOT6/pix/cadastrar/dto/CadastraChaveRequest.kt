package br.com.zupacademy.marcosOT6.pix.cadastrar.dto

import br.com.zupacademy.marcosOT6.pix.CadastraChavePixRequest
import br.com.zupacademy.marcosOT6.pix.TipoDeChave
import br.com.zupacademy.marcosOT6.pix.TipoDeConta
import br.com.zupacademy.marcosOT6.pix.validacao.ChavePix
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ChavePix
@Introspected
data class CadastraChaveRequest(

    @field:Size(max = 77)
    val valorDaChave: String?,

    @field:NotNull
    val tipoDeChave: TipoDeChave?,

    @field:NotNull
    val tipoDeConta: TipoDeConta?
) {

    fun toRequestGrpc(codigoInterno: String): CadastraChavePixRequest {
        return CadastraChavePixRequest
            .newBuilder()
            .setCodigoInterno(codigoInterno)
            .setValorDaChave(this.valorDaChave ?: "")
            .setTipoDeChave(tipoDeChave ?: TipoDeChave.UNKNOW_TIPO_CHAVE)
            .setTipoDeConta(tipoDeConta ?: TipoDeConta.UNKNOW_TIPO_CONTA)
            .build()
    }
}