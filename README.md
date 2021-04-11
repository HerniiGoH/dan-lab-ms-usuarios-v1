# dan-lab-ms-usuarios-v1
Laboratorio de Desarrollo de Aplicaciones en la Nube, microservicio de Usuarios.

## Pasos para configurar la base de datos (microservicio usuarios)

##### Ingresar a mysql desde el cmd (teniendo el servicio corriendo)
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
```
1. Abrir IntelliJ.
2. Ir a la seccion "Database".
3. Si ya hay un "localhost" creado, modificarlo con las propiedades, caso contrario crear un nuevo "Datasource MySql".
4. Asignar como enlace de conexion: jdbc:mysql://localhost:3306/ms_usuarios_db.
5. Asignar usuario 'user_usuarios' y contraseña '3be43125f05dc87c9b5f27533daf2f01'(sin las comillas).
6. Verificar driver de MysQL 8.21 o superior.
7. Testear la conexion.
8. Ir a la pestaña "schemas" y marcar como visible solo el de "ms_usuarios_db".
```
##### Realizar la migracion desde IntelliJ
```
1. Ir a la pestaña Maven, Plugins, Flyway.
2. Doble click en el goal "Flyway Clean".
3. Doble click en el goal "Flyway Migrate".
4. Alternativamente, se puede realizar un "run" de la aplicacion, pero es mas recomendable con el emtodo anterior.
5. Tambien se pueden ejecutar por consola de IntelliJ los comandos "mvnw flyway:clean" y "mvn flyway:migrate" en ese orden.
```
