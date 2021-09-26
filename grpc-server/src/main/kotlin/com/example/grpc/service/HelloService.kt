package com.example.grpc.service

import com.example.grpc.GreeterGrpcKt
import com.example.grpc.HelloReply
import com.example.grpc.HelloRequest
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class HelloService: GreeterGrpcKt.GreeterCoroutineImplBase() {

    override suspend fun sayHello(request: HelloRequest): HelloReply {
        println("request: ${request.name}")
        return HelloReply.newBuilder()
            .setMessage("Hello ===> ${request.name}")
            .build()
    }
}
