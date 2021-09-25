package com.example.grpc.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Configuration
@EnableJpaAuditing
class GrpcDatasourceConfig {

    @Bean
    fun dataSource(
        properties: GrpcDataSourceProperties
    ): DataSource {
        return DataSourceBuilder.create()
            .username(properties.username)
            .password(properties.password)
            .url(properties.jdbcUrl)
            .type(HikariDataSource::class.java)
            .driverClassName(properties.driverClassName)
            .build()
    }

    @Component
    @ConfigurationProperties("grpc.datasource")
    @PropertySources(
        PropertySource(value = ["classpath:/datasource/grpc-datasource-\${spring.profiles.active}.properties"])
    )
    class GrpcDataSourceProperties: HikariConfig()
}
