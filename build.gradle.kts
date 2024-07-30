import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
//import com.sun.tools.javac.jvm.ByteCodes.new_
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
//import sun.jvmstat.monitor.MonitoredVmUtil.mainClass

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("io.ebean") version "14.1.0"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.5.9"
val junitJupiterVersion = "5.9.1"
val retrofitversion ="2.9.0"
val retrofitconvertor = "2.9.0"
val ebeanVersion = "12.12.1"
val queryBeanVersion = "12.12.1"
val mysqlConnectorVersion = "8.0.28"
val hikariCPVersion = "4.0.3"
val vertexauthjwt = "4.3.4"
val vertexauthcommon = "4.3.4"
val ebeanagent = "12.11.1"
val jjwt_Api ="0.11.5"
val jjwt_IMPL = "0.11.5"
val jackson = "0.11.5"
val jbcrypt ="0.4"
val jwtauth="4.3.3"

val mainVerticleName = "com.example.new_.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClass.set(launcherClassName)
//  mainClass.set(mainVerticleName)
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-core")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
  implementation("com.squareup.retrofit2:retrofit:$retrofitversion")
  implementation("com.squareup.retrofit2:converter-gson:$retrofitconvertor")
  implementation("com.google.code.gson:gson:2.8.8")
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-web-client")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.x.y")
  implementation ("com.google.code.gson:gson:2.9.0")
  implementation ("com.squareup.okhttp3:okhttp:4.9.0")
  implementation("io.ebean:ebean:$ebeanVersion")
  implementation("io.ebean:ebean-querybean:$queryBeanVersion")
  implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
  implementation ("com.zaxxer:HikariCP:$hikariCPVersion")
  implementation ("io.vertx:vertx-auth-jwt:$vertxVersion")
  implementation ("org.mindrot:jbcrypt:$jbcrypt")
  implementation ("io.vertx:vertx-auth-jwt:$jwtauth")
  implementation ("io.vertx:vertx-auth-common:$vertexauthcommon")
  runtimeOnly ("io.ebean:ebean-agent:$ebeanagent")
  implementation ("io.jsonwebtoken:jjwt-api:$jjwt_Api")
  runtimeOnly ("io.jsonwebtoken:jjwt-impl:$jjwt_IMPL")
  runtimeOnly ("io.jsonwebtoken:jjwt-jackson:$jackson")

  compileOnly("org.projectlombok:lombok:1.18.24")
  annotationProcessor("org.projectlombok:lombok:1.18.24")


}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}


tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}
