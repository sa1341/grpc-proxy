package com.example.grpc.client

import com.example.grpc.GreeterGrpcKt
import com.example.grpc.HelloRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class HelloClient(private val channel: ManagedChannel) : Closeable {
    private val stub: GreeterGrpcKt.GreeterCoroutineStub = GreeterGrpcKt.GreeterCoroutineStub(channel)

    suspend fun greet(name: String) {
        val request = HelloRequest.newBuilder().setName(name).build()
        val response = stub.sayHello(request)
        println("Received: ${response.message}")
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

suspend fun main(args: Array<String>) {
    val port = 6565
    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()

    val client = HelloClient(channel)

    val user = args.singleOrNull() ?: "world"
    client.greet(user)
}
