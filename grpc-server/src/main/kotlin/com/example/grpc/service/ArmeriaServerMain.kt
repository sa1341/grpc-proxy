package com.example.grpc.service

import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.HttpStatus
import com.linecorp.armeria.common.MediaType
import com.linecorp.armeria.server.Server

fun main() {
    var server = Server.builder()
        .http(8081)
        .service("/hello") { ctx, res ->
            HttpResponse.of(
                HttpStatus.OK,
                MediaType.HTML_UTF_8,
                "<h1>Hello Armeria...!</h1>"
            )
        }
    .build()

    val future = server.start()
    future.join()
}
