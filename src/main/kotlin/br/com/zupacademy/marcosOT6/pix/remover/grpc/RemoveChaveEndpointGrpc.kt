package br.com.zupacademy.marcosOT6.pix.remover.grpc

import br.com.zupacademy.marcosOT6.pix.PixServiceGrpc
import br.com.zupacademy.marcosOT6.pix.PixServiceRemoverGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class RemoveChaveEndpointGrpc {

    @Singleton
    fun blockingStubConfig(@GrpcChannel("servidorGrpc") channel: ManagedChannel): PixServiceRemoverGrpc.PixServiceRemoverBlockingStub {
        return PixServiceRemoverGrpc.newBlockingStub(channel)
    }

}