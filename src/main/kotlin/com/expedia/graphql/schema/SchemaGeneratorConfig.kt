package com.expedia.graphql.schema

import com.expedia.graphql.schema.hooks.NoopSchemaGeneratorHooks
import com.expedia.graphql.schema.hooks.SchemaGeneratorHooks

/**
 * Settings for generating the schema.
 */
data class SchemaGeneratorConfig(
    val supportedPackages: String,
    val topLevelQueryName: String = "TopLevelQuery",
    val topLevelMutationName: String = "TopLevelMutation",
    val hooks: SchemaGeneratorHooks = NoopSchemaGeneratorHooks()
)