/*
 * Copyright 2020 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.graphql.spring

import com.expediagroup.graphql.execution.KotlinDataFetcherFactoryProvider
import com.expediagroup.graphql.spring.exception.KotlinDataFetcherExceptionHandler
import com.expediagroup.graphql.spring.execution.ContextWebFilter
import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import com.expediagroup.graphql.spring.execution.DefaultContextFactory
import com.expediagroup.graphql.spring.execution.EmptyDataLoaderRegistryFactory
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import com.expediagroup.graphql.spring.execution.SpringKotlinDataFetcherFactoryProvider
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.execution.DataFetcherExceptionHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * The root configuration class that other configurations can import to get the basic
 * beans required to then create an exetuable GraphQL schema object.
 */
@Configuration
@EnableConfigurationProperties(GraphQLConfigurationProperties::class)
@Import(JacksonAutoConfiguration::class)
class GraphQLExecutionConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun dataFetcherFactoryProvider(objectMapper: ObjectMapper, applicationContext: ApplicationContext): KotlinDataFetcherFactoryProvider =
        SpringKotlinDataFetcherFactoryProvider(objectMapper, applicationContext)

    @Bean
    @ConditionalOnMissingBean
    fun exceptionHandler(): DataFetcherExceptionHandler = KotlinDataFetcherExceptionHandler()

    @Bean
    @ConditionalOnMissingBean
    fun dataLoaderRegistryFactory(): DataLoaderRegistryFactory = EmptyDataLoaderRegistryFactory()

    @Bean
    @ConditionalOnMissingBean
    fun graphQLContextFactory(): GraphQLContextFactory<*> = DefaultContextFactory

    @Bean
    @ConditionalOnMissingBean
    fun contextWebFilter(
        config: GraphQLConfigurationProperties,
        graphQLContextFactory: GraphQLContextFactory<*>
    ): ContextWebFilter<*> = ContextWebFilter(config, graphQLContextFactory)
}
