package br.com.zupacademy.marcosOT6.pix.listar.grpc

import br.com.zupacademy.marcosOT6.pix.PixServiceListagemGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class ListaChaveEndpoint {

    @Singleton
    fun blockingStubConfig(@GrpcChannel("servidorGrpc") channel: ManagedChannel):
            PixServiceListagemGrpc.PixServiceListagemBlockingStub {
        return PixServiceListagemGrpc.newBlockingStub(channel)
    }

}