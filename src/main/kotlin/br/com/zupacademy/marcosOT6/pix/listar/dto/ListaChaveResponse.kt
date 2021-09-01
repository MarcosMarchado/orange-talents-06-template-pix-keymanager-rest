package br.com.zupacademy.marcosOT6.pix.listar.dto

import br.com.zupacademy.marcosOT6.pix.Chave

data class ListaChaveResponse(
    val chaves: List<ChaveResponse>
){

    companion object {
        fun toResponse(responseGrpc: MutableList<Chave>): ListaChaveResponse {
            return ListaChaveResponse(responseGrpc.map { ChaveResponse(it) })
        }
    }

}