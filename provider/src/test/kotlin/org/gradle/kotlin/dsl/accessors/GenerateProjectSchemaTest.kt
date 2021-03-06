package org.gradle.kotlin.dsl.accessors

import org.gradle.kotlin.dsl.fixtures.AbstractIntegrationTest

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GenerateProjectSchemaTest : AbstractIntegrationTest() {

    @Test
    fun `writes multi-project schema to buildSrc`() {

        withBuildScript("""
            plugins { java }
        """)

        build("kotlinDslAccessorsSnapshot")

        val generatedSchema =
            loadMultiProjectSchemaFrom(
                existing("gradle/project-schema.json"))

        val expectedSchema =
            mapOf(
                ":" to ProjectSchema(
                        extensions = mapOf(
                            "defaultArtifacts" to "org.gradle.api.internal.plugins.DefaultArtifactPublicationSet",
                            "ext" to "org.gradle.api.plugins.ExtraPropertiesExtension",
                            "reporting" to "org.gradle.api.reporting.ReportingExtension"),
                        conventions = mapOf(
                            "base" to "org.gradle.api.plugins.BasePluginConvention",
                            "java" to "org.gradle.api.plugins.JavaPluginConvention")))

        assertThat(
            generatedSchema,
            equalTo(expectedSchema))
    }
}
