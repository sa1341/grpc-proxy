package com.example.grpc.service

import com.example.grpc.GreeterGrpcKt
import com.example.grpc.HelloReply
import com.example.grpc.HelloRequest

class HelloService: GreeterGrpcKt.GreeterCoroutineImplBase() {

    override suspend fun sayHello(request: HelloRequest): HelloReply {
        println("request: ${request.name}")
        return HelloReply.newBuilder()
            .setMessage("Hello ===> ${request.name}")
            .build()
    }
}
