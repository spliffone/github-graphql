plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    // Apollo GraphQL code generation
    id("com.apollographql.apollo3") version "3.3.0"
    
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Wrap coroutines in CompleteableFuture
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")

    // Use Apollo client 
    implementation("com.apollographql.apollo3:apollo-runtime:3.3.0")
    implementation("com.apollographql.apollo3:apollo-adapters:3.3.0")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("com.apollographql.apollo3:apollo-testing-support:3.3.0")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest()
        }
    }
}

apollo {
    packageName.set("com.github.spliffone.client.generated")
    // Enable test builder generation
    generateTestBuilders.set(true)

    //mapScalar("GitTimestamp", "java.util.Date", "com.apollographql.apollo3.adapter.DateAdapter")
    //mapScalar("DateTime", "java.util.Date")

    introspection {
        endpointUrl.set("https://api.github.com/graphql")
        // The path is interpreted relative to the current project here, no need to prepend 'app'
        schemaFile.set(file("src/main/graphql/com/github/spliffone/client/schema.graphqls"))
    }
}