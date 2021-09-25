package com.example.grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class GrpcProxyServerApplication

fun main(args: Array<String>) {
    runApplication<GrpcProxyServerApplication>(*args)
}

