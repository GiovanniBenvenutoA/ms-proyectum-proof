# 📦 ms-proyectum-product-neg

Microservicio en **Java 17 / Spring Boot** para la gestión de productos.  
Incluye base de datos en memoria **H2**, inicialización con `data.sql`, y documentación interactiva mediante **Swagger UI**.

---

## 🚀 Tecnologías utilizadas
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (memoria)
- Hibernate
- Lombok
- Swagger / OpenAPI

---

## ⚙️ Acceder a la aplicación

``` Acceder a la aplicación
Swagger UI
👉 http://localhost:8080/api-product-proyectum/swagger-ui/index.html

Consola H2
👉 http://localhost:8080/api-product-proyectum/h2-console

JDBC URL: jdbc:h2:mem:productsdb

Usuario: sa

Password: (vacío)
```

## ⚙️ Acceder a la aplicación
```
Para acceder a los recursos de la api primero debes consumir este servicio 

http://localhost:8080/api-product-proyectum/auth/login

el usuario y password deben ir en un formato json como ejemplo

{ "username": "admin", "password": "admin123" }

esto generara un token de acceso que durara 1 hora.

```
```
## ⚙️ Endpoints principales

``` Endpoints principales

Todos los endpoints están agrupados bajo el recurso Gestión de Productos en Swagger.

Método	Endpoint
GET /auth/login Obtencion de token 
GET	/products	Listar todos los productos (paginado y filtrado opcional)	/products?page=0&size=10
GET	/products/{id}	Obtener un producto por su ID	
POST	/products	Crear un nuevo producto	json { "nameProduct": "Laptop", "priceProduct": 750000, "stockProduct": 15, "descriptionProduct": "Laptop de alto rendimiento" }
PUT	/products/{id}	Actualizar un producto existente	json { "nameProduct": "Laptop Gamer", "priceProduct": 950000, "stockProduct": 10, "descriptionProduct": "Laptop optimizada para juegos" }
DELETE	/products/{id}	Eliminar un producto por ID	/products/1
```
## ▶️ Ejecución

``` Endpoints principales
🔹 Con IntelliJ IDEA

Abrir el proyecto

File > Open... y seleccionar la carpeta raíz del proyecto.

Configurar JDK

File > Project Structure > Project

Seleccionar Project SDK = Java 17

Seleccionar Language level = 17

Verificar dependencias Maven

Abrir el panel Maven en IntelliJ

Clic en Reload All Maven Projects

Configurar ejecución de Spring Boot

Run > Edit Configurations...

Agregar configuración Spring Boot

Seleccionar la clase principal:
cl.proyectum.product.neg.MsProyectumProductNegApplication

Ejecutar

Clic en el botón verde ▶️ (Run)


🔹 Con Maven (línea de comandos)

Abrir una terminal en la carpeta raíz del proyecto:

cd ms-proyectum-product-neg


Compilar el proyecto:

mvn clean install


Ejecutar la aplicación:

mvn spring-boot:run
