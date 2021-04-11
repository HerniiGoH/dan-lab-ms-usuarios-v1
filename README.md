# dan-lab-ms-usuarios-v1
Laboratorio de Desarrollo de Aplicaciones en la Nube, microservicio de Usuarios.

##### Requerimientos:
- Java 8 u 11.
- MySql Server 8 o superior.
- IntelliJ Idea Ultimate (Opcional, pero varios de los pasos son explicados usando este IDE).

## Configuracion de la base de datos

##### Ingresar a mysql desde el cmd
```
> mysql -u root -p
> password: root
```
##### Crear la base de datos del microservicio
```
> create database ms_usuarios_db;
```
##### Crear el usuario para springboot y darle los permisos en la base de datos
```
> create user 'user_usuarios'@'localhost' identified by '3be43125f05dc87c9b5f27533daf2f01';
> grant all privileges on ms_usuarios_db.* to 'user_usuarios'@'localhost';
> flush privileges;
> exit;
```
##### Verificar la conexion con la base de datos

1. Abrir IntelliJ.
2. Ir a la seccion `Database`.
3. Si ya hay un `localhost` creado, modificarlo con las `propiedades`, caso contrario crear un nuevo `Datasource MySql`.
4. Asignar como enlace de conexion: `jdbc:mysql://localhost:3306/ms_usuarios_db`.
5. Asignar usuario `user_usuarios` y contraseña `3be43125f05dc87c9b5f27533daf2f01`.
6. Verificar driver de MysQL `8.21` o superior.
7. Testear la conexion.
8. Ir a la pestaña `schemas` y marcar como visible solo el de `ms_usuarios_db`.

##### Descargar las dependencias de MySql y JPA al [pom.xml](pom.xml)
```
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.21</version>
</dependency>
```
##### Configurar acceso a la base de datos en [application.properties](src/main/resources/application.properties)
```
spring.jpa.hibernate.ddl-auto=validate
spring.datasource.url=jdbc:mysql://localhost:3306/ms_usuarios_db
spring.datasource.username=user_usuarios
spring.datasource.password=3be43125f05dc87c9b5f27533daf2f01
```
## Migracion de base de datos con Flyway
##### Crear el directorio `db/migration` dentro de `resources`
##### Crear dentro del directorio el archivo de migracion con el formato:
1. __Prefijo__: Obligatoriamente `V`.
2. __Versión__: Debe ser única, puede contener tantos `.` y `_` como se quira. Ej: `2`, `2_5_1`, `1.1`.
3. __Separador__: Deben ser `__` (dos guiones bajos).
4. __Descripcion__: Texto separado por tantos `.` y `_` como prefiera. Ej: `CrearEntidadesUsuario`, `Crear_Entidades_Usuario`, `Crear.Entidades.Usuario`
5. __Sufijo__: Si o si `.sql`.
6. Ejemplo completo: [`V1__CrearEntidadesUsuario.sql`](src/main/resources/db/migration/V1__CrearEntidadesUsuario.sql)
##### Añadir las dependencias de Flyway al [pom.xml](pom.xml)
```
<dependencies>
...
        <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-core</artifactId>
        </dependency>
...
</dependencies>
...
<build>
...
        <plugins>
        ...
                  <plugin>
                        <groupId>org.flywaydb</groupId>
                        <artifactId>flyway-maven-plugin</artifactId>
                        <executions>
                          <execution>
                            <phase>generate-sources</phase>
                            <goals>
                              <goal>migrate</goal>
                            </goals>
                          </execution>
                        </executions>
                        <configuration>
                          <driver>com.mysql.cj.jdbc.Driver</driver>
                          <url>jdbc:mysql://localhost:3306/ms_usuarios_db</url>
                          <user>user_usuarios</user>
                          <password>3be43125f05dc87c9b5f27533daf2f01</password>
                          <baselineOnMigrate>true</baselineOnMigrate>
                          <schemas>ms_usuarios_db</schemas>
                          <locations>
                            <location>
                              filesystem:src/main/resources/db/migration
                            </location>
                          </locations>
                        </configuration>
                </plugin>
        ...
        </plugins>
...
</build>
```
Lo ideal es que las etiquetas `<user>` y `<password>` coincidan con el `usuario` y `contraseña` del usuario creado anteriormente. Sino se puede usar el default `root` `root`. La `<url>` es la de la base de datos configurada en [application.properties](src/main/resources/application.properties).
##### Realizar la migracion desde IntelliJ
1. Ir a la pestaña `Maven`, `Plugins`, `Flyway`.
2. Doble click en el goal `Flyway Clean`.
3. Doble click en el goal `Flyway Migrate`.
4. Alternativamente, se puede realizar un `run` de la aplicacion, pero es mas recomendable con el metodo anterior.
5. Tambien se pueden ejecutar por consola de IntelliJ los comandos `mvnw flyway:clean` y `mvnw flyway:migrate` en ese orden.

##### Deshacer una migracion:
Se tiene la opcion de hacer un `mvnw flyway:clean` y luego `mvnw flyway:migrate` o se puede eliminar la fila correspondiente a esa version de la tabla `flyway_schema_history`.
##### Mas informacion de Flyway:
- [Configure Flyway with Spring Boot](https://medium.com/@tejozarkar/configure-flyway-with-spring-boot-9493aebf336b)
- [Flyway Documentation](https://flywaydb.org/documentation/)

## Configuracion Inicial de Jenkins:
##### Agregar las siguientes dependencias al [pom.xml](pom.xml)
```
<build>
      ...
      <plugins>
            ...
                  <plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-pmd-plugin</artifactId>
				<version>3.14.0</version>
			</plugin>

			<plugin>
				<groupId>com.github.spotbugs</groupId>
				<artifactId>spotbugs-maven-plugin</artifactId>
				<version>4.2.2</version>
			</plugin>

			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>findbugs-maven-plugin</artifactId>
				<version>3.0.5</version>
				<configuration>
					<excludeFilterFile>${project.basedir}/exclude-findbugs.xml</excludeFilterFile>
					<skip>true</skip>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-checkstyle-plugin</artifactId>
				<version>3.1.2</version>
			</plugin>

			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.8.6</version>
				<executions>
					<execution>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<execution>
						<id>report</id>
						<phase>test</phase>
						<goals>
							<goal>report</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
                 ...
      </plugins>
      ...
</build>
...
<reporting>
      ...
      <plugins>
            ...
            <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-report-plugin</artifactId>
                  <version>3.0.0-M5</version>
            </plugin>
            ...
      </plugins>
      ...
</reporting>
```
##### Crear el archivo [`Jenkinsfile`](Jenkinsfile) como el que esta de ejemplo.
##### Descargar el archivo `jenkins.war` del [sitio oficial de Jenkins](https://www.jenkins.io/download/).
##### Abrir la consola de comando en el directorio donde se descargo el `jenkins.war` y ejecutar el comando:
```
java -jar jenkins.war --httpPort=9090
```
El puerto puede ser el que uno quiera.
##### Ingresar desde el navegador al sitio `http://localhost:9090` para terminar la instalacion.
1. Copiar la `contraseña` que aparece por consola y pegarla como contraseña de administrador.
2. Saltearse la configuracion de un nuevo usuario, usar admin por defecto.
3. Instalar los plugins recomendados por defecto.
##### Configurar la nueva credencial para `github`:
1. Ir a la seccion `Manage Jenkins`, `Manage Credentials`, seleccionar el dominio `(global)`, `Add Credentials`
2. __Username__: El nombre de `usuario` de tu cuenta de `github`.
3. __Password__: El `Token` que hayas creado para Jenkins. Si no tienes un token creado, sigue [estos pasos](https://docs.github.com/es/github/authenticating-to-github/creating-a-personal-access-token) para crear uno con __todos los permisos__.
4. __Id__: Un identificador para la credencial, puede ser geenrico como `TOKEN_GITHUB` o dejar que lo autocomplete Jenkins.
5. Descripcion: Opcional, un breve comentario sobre para qué es este token.
##### Añadir los plugins necesarios:
1. Volver al `Control Panel`, `Manage Jenkins`, `Manage Plugins`
2. Ir a la seccion `All Plugins` y buscar los siguientes (instalar sin reiniciar):
      - `ChuckNorris Plugin`
      - `HTML Publisher Plugin`
      - `JaCoCo Plugin`
      - `Warnings Next Generation Plugin`
##### Crear una nueva tarea:
1. Volver al `Control Panel` y clickear en la pestaña `New Task`.
2. Ponerle el nombre que uno quiera, por ejemplo `DAN-LAB-MS-Usuarios` y seleccionar la opcion `Multibranch Pipeline`.
3. Opcionalmente, se puede configurar en `Display Name` otro alias para el proyecto, como `Microservicio Usuarios`, y agregar una breve `descripcion`.
4. En la seccion `Branch Sources` dar la opcion `Add Source` y seleccionar `GitHub`.
5. Elegir la `credencial` creada en los pasos anteriores para GitHub, pegar la URL del proyecto GitHub y darle al botón `Validate`.
6. Saltearse el resto de las opciones y clickear `Save`.
7. Esperar a que se terminen de `buildear` todas las ramas del proyecto, esto puede tomar varios minutos.
