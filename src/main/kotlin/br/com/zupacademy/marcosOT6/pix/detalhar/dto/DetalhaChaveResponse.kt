package br.com.zupacademy.marcosOT6.pix.detalhar.dto

import br.com.zupacademy.marcosOT6.pix.DetalhesChavePixResponse
import io.micronaut.core.annotation.Introspected

@Introspected
class DetalhaChaveResponse(responseGrpc: DetalhesChavePixResponse) {
    val pixId: String = responseGrpc.pixId
    val codigoInterno: String = responseGrpc.codigoInterno
    val tipoDeChave: String = responseGrpc.tipoDeChave.name
    val valorDaChave: String = responseGrpc.valorDaChave
    val conta: Conta = Conta(responseGrpc.conta)
    val dataCriacao: String = responseGrpc.dataCriacao
}

