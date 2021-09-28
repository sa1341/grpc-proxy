package com.example.grpc.service

import com.linecorp.armeria.common.*
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.grpc.GrpcService
import io.reactivex.BackpressureStrategy


class CustomService {

    @Get("/hello")
    fun methodA(): HttpResponse {
        return HttpResponse.of(
            HttpStatus.OK,
            MediaType.HTML_UTF_8,
            "<h1>Hello Custom Service...!</h1>"
        )
    }

    @Get("/page/:number")
    fun methodB(@Param("number") number: Int): HttpResponse {
        return HttpResponse.of(
            HttpStatus.OK,
            MediaType.HTML_UTF_8,
            "<h1>Hello $number...!</h1>"
        )
    }

    @Get("/reactive")
    fun methodC(): HttpResponse {
        val dataStream = io.reactivex.Observable.just("a", "b", "c", "d")
        val httpDataStream = dataStream.map(HttpData::ofUtf8)

        val httpHeaders = ResponseHeaders.of(HttpStatus.OK)

        val responseStream = io.reactivex.Observable.concat(io.reactivex.Observable.just(httpHeaders), httpDataStream)

        return HttpResponse.of(responseStream.toFlowable(BackpressureStrategy.BUFFER))
    }
}


fun main() {
    val server = Server.builder()
        .http(8081)
        .annotatedService(CustomService())
        .service(GrpcService.builder().addService(HelloService()).build())
        .service("/hello/rest") {ctx, req ->  HttpResponse.of("Hello World!")}
        .serviceUnder("/docs", DocService())
        .build()

    val future = server.start()
    future.join()
}
