
Entorno local.

Para facilitar el desarrlo se ha utilizado el plugin de maven `spring-boot-maven-plugin` añadiendo `jvmArguments` y `maven-dependency-plugin` para descargar el agente openTelemetry.


```xml
<properties>
    <java.version>17</java.version>
    <opentelemetry.version>1.31.0</opentelemetry.version>
    <opentelemetry.endpoint>http://127.0.0.1:4317</opentelemetry.endpoint>
    <opentelemetry.agent.filename>opentelemetry-javaagent.jar</opentelemetry.agent.filename>
</properties>
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <profiles>
                <profile>local</profile>
            </profiles>
            <excludes>
                <exclude>
                    <groupId>org.projectlombok</groupId>
                    <artifactId>lombok</artifactId>
                </exclude>
            </excludes>
            <jvmArguments>
                -Dspring.profiles.active=local
                -javaagent:${project.build.directory}/${opentelemetry.agent.filename}
                -Dotel.exporter.otlp.endpoint=${opentelemetry.endpoint}
                -Dotel.resource.attributes=service.name=${project.artifactId},service.version=1.0.0
                -Dotel.metrics.exporter=otlp
                -Dotel.traces.exporter=otlp
                -Dotel.logs.exporter=otlp
            </jvmArguments>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>copy</goal>
                </goals>
                <phase>process-resources</phase>
                <configuration>
                    <artifactItems>
                        <artifactItem>
                            <groupId>io.opentelemetry.javaagent</groupId>
                            <artifactId>opentelemetry-javaagent</artifactId>
                            <version>${opentelemetry.version}</version>
                            <type>jar</type>
                            <outputDirectory>${project.build.directory}</outputDirectory>
                            <destFileName>
                                ${opentelemetry.agent.filename}</destFileName>
                        </artifactItem>
                    </artifactItems>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```

[Documentación OpenTelemetry](https://opentelemetry.io/docs/instrumentation/java/automatic/)