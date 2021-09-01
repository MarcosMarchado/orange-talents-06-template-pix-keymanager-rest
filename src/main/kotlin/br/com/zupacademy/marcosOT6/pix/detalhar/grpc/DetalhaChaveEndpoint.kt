package br.com.zupacademy.marcosOT6.pix.detalhar.grpc

import br.com.zupacademy.marcosOT6.pix.PixServiceDetalhesGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class DetalhaChaveEndpointGrpc {

    @Singleton
    fun blockingStubConfig(@GrpcChannel("servidorGrpc") channel: ManagedChannel):
            PixServiceDetalhesGrpc.PixServiceDetalhesBlockingStub {
        return PixServiceDetalhesGrpc.newBlockingStub(channel)
    }

}