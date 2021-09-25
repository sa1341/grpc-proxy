package com.example.grpc.server

import com.example.grpc.GreeterGrpcKt
import com.example.grpc.HelloReply
import com.example.grpc.HelloRequest
import io.grpc.Server
import io.grpc.ServerBuilder

class HelloServer(private val port: Int) {
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(HelloService())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class HelloService: GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest): HelloReply {
            return HelloReply.newBuilder().setMessage("Hello ${request.name}").build()
        }

    }
}

/*
fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = HelloServer(port)
    server.start()
    server.blockUntilShutdown()
}
*/
