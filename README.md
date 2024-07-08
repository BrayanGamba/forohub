# ForoHub

## Descripción
Esta aplicación proporciona un API REST para gestionar tópicos de un foro, donde los usuarios pueden registrar, actualizar, listar y desactivar tópicos relacionados con cursos.

## Tecnologías Utilizadas
	- Java
	- Spring Boot
	- Maven
	- Hibernate / Spring Data JPA
	- MySQL (o tu base de datos preferida)
	- Swagger (para documentación de la API)
	- Spring Security (para la seguridad)

## Instalación y Uso

### Requisitos Previos
	- Java JDK: versión 17 en adelante
	- Maven: versión 4 en adelante
	- Spring Boot: versión 3 en adelante
	- MySQL: versión 8 en adelante u otra base de datos compatible

### Ejecución
Clona el repositorio:

    ```bash
    git clone https://github.com/BrayanGamba/forohub.git
    ```

2. Importa el proyecto en tu IDE favorito como un proyecto Maven.

3. Configura la base de datos MySQL:
    - Asegúrate de tener MySQL instalado y configurado en tu máquina.
    - Crea una base de datos llamada `db_forohub` (o el nombre que desees) en MySQL.
    - Actualiza las configuraciones de conexión a la base de datos en el archivo `application.properties` dentro de `src/main/resources`:

     ```properties
     spring.datasource.url=jdbc:mysql://${DB_HOST}/${DB_NAME}
	 spring.datasource.username=${DB_USER}
	 spring.datasource.password=${DB_PASSWORD}
     ```
	 
4. Asegúrate de tener las dependencias necesarias en tu entorno de desarrollo.
	- Lombok
	- Spring Web
	- Spring Boot DevTools
	- Spring Data JPA
	- Flyway Migration
	- MySQL Driver
	- Validation
	- Spring Security
5. Ejecuta el proyecto desde tu IDE ejecutando la clase `ForohubApplication`.
6. La aplicación se iniciará en `http://localhost:8080`.

### Funcionalidades
La aplicación soporta las siguientes operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los tópicos:

1. **Registrar un Tópico**
    - Método: `POST`
    - Endpoint: `/topicos`
    - Descripción: Crea un nuevo tópico con los datos proporcionados.

2. **Listar Tópicos Activos**
    - Método: `GET`
    - Endpoint: `/topicos`
    - Descripción: Obtiene una lista de todos los tópicos activos disponibles.

3. **Obtener Detalles de un Tópico**
    - Método: `GET`
    - Endpoint: `/topicos/{id}`
    - Descripción: Obtiene los detalles de un tópico específico por su ID.

4. **Actualizar un Tópico**
    - Método: `PUT`
    - Endpoint: `/topicos`
    - Descripción: Actualiza los detalles de un tópico existente.

5. **Eliminar un Tópico**
    - Método: `DELETE`
    - Endpoint: `/topicos/{id}`
    - Descripción: Elimina un tópico específico por su ID.

6. **Desactivar un Tópico**
    - Método: `DELETE`
    - Endpoint: `/topicos/desactivar-{id}`
    - Descripción: Cambia el estado de un tópico a inactivo.

### Documentación de la API
    - Accede a Swagger UI para explorar y probar los endpoints API:
    - `http://localhost:8080/swagger-ui.html`
  
## Seguridad

La aplicación utiliza seguridad básica mediante tokens JWT (JSON Web Token) para autenticación. Los endpoints están protegidos y requieren un token válido en el encabezado `Authorization` para ser accesibles.

## Contribución

¡Contribuciones son bienvenidas! Si deseas contribuir, sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz un commit (`git commit -am 'Agrega nueva característica'`).
4. Haz push a la rama (`git push origin feature/nueva-caracteristica`).
5. Abre un pull request.
