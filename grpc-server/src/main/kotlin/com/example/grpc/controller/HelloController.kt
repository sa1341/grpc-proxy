package com.example.grpc.controller

import com.example.grpc.GreeterGrpc
import com.example.grpc.HelloReply
import com.example.grpc.HelloRequest
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.HttpStatus
import com.linecorp.armeria.common.MediaType
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {

    val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 8081)
                                        .usePlaintext()
                                        .build()

    val blockingStub: GreeterGrpc.GreeterBlockingStub = GreeterGrpc.newBlockingStub(channel)

    @GetMapping("/hello/grpc")
    @ResponseBody
    fun greeting(@RequestParam name: String): String {

        val request = HelloRequest.newBuilder()
            .setName(name)
            .build()

        val response = blockingStub.sayHello(request)

        return response.message
    }
}
