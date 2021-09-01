package br.com.zupacademy.marcosOT6.pix.listar.dto

import br.com.zupacademy.marcosOT6.pix.Chave

class ChaveResponse(chaveResponseGrpc: Chave){
    val pixId: String = chaveResponseGrpc.pixId
    val tipoDeChave: String = chaveResponseGrpc.tipoDeChave.name
    val valorDaChave: String = chaveResponseGrpc.valorDaChave
    val tipoDeConta: String = chaveResponseGrpc.tipoDeConta.name
    val dataCriacao: String = chaveResponseGrpc.dataCriacao
}