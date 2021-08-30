package br.com.zupacademy.marcosOT6.pix.cadastrar.grpc

import br.com.zupacademy.marcosOT6.pix.PixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class CadastraChaveEndpointGrpc {

    @Singleton
    fun blockingStubConfig(@GrpcChannel("servidorGrpc") channel: ManagedChannel): PixServiceGrpc.PixServiceBlockingStub {
        return PixServiceGrpc.newBlockingStub(channel)
    }

}