# demo2
demo app with spring-boot 2

## Packages
* Spring Boot 2.2.0
* thymeleaf
* devtools
* lombok
* jpa
* h2


## Webjars
* jquery 3.3.1
* bootstrap 4.0.0
* datatables 1.10.20

## 실행하기
```bash
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8787 -Dserver.port=9090 -Dpath.to.config.dir=/var/data/my/config/dir"
```

## css, js minify 처리하기 위해서.
```xml
<plugin>
  <groupId>com.samaxes.maven</groupId>
  <artifactId>minify-maven-plugin</artifactId>
  <version>1.7.6</version>
  <executions>
    <execution>
      <id>minify</id>
      <phase>process-resources</phase>
      <goals>
        <goal>minify</goal>
      </goals>
      <configuration>
        <charset>utf-8</charset>
        <skipMerge>true</skipMerge>
        <nosuffix>true</nosuffix>
        <jsEngine>CLOSURE</jsEngine>

        <webappSourceDir>${basedir}/src/main/resources/static/custom</webappSourceDir>
        <webappTargetDir>${project.build.directory}/classes/static</webappTargetDir>
        <jsSourceDir>.</jsSourceDir>
        <jsTargetDir>/custom</jsTargetDir>
        <jsSourceIncludes>
          <jsSourceInclude>**/*.js</jsSourceInclude>
        </jsSourceIncludes>
        <jsSourceExcludes>
          <jsSourceExclude>**/*.min.js</jsSourceExclude>
        </jsSourceExcludes>

        <cssSourceDir>.</cssSourceDir>
        <cssTargetDir>/custom</cssTargetDir>
        <cssSourceIncludes>
          <cssSourceInclude>**/*.css</cssSourceInclude>
        </cssSourceIncludes>
        <cssSourceExcludes>
          <cssSourceExclude>**/*.min.css</cssSourceExclude>
        </cssSourceExcludes>

      </configuration>
    </execution>
  </executions>
</plugin>
```