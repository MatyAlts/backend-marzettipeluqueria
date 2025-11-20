# Peluquería Marzetti - Backend API

Backend API REST desarrollado con Spring Boot para la plataforma e-commerce de Peluquería Marzetti.

## 🚀 Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Security con JWT
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- SpringDoc OpenAPI (Swagger)

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+

## ⚙️ Configuración

### 1. Base de Datos PostgreSQL

Crear la base de datos:

```sql
CREATE DATABASE peluqueria_marzetti;
```

### 2. Configurar application.properties

Editar `src/main/resources/application.properties` con tus credenciales de PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/peluqueria_marzetti
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

El servidor se iniciará en `http://localhost:8080`

## 📚 Documentación API

Una vez iniciado el servidor, accede a la documentación interactiva:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 🔐 Autenticación

El sistema usa JWT (JSON Web Tokens) para autenticación de administradores.

### Usuario Admin por Defecto

- **Username**: `admin`
- **Password**: `admin123`

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

Usar el token en headers:
```
Authorization: Bearer <token>
```

## 📡 Endpoints Principales

### Productos

- `GET /api/products` - Obtener todos los productos (con filtros opcionales)
  - Query params: `categoryId`, `minPrice`, `maxPrice`, `search`
- `GET /api/products/{id}` - Obtener producto por ID
- `POST /api/admin/products` - Crear producto (requiere admin)
- `PUT /api/admin/products/{id}` - Actualizar producto (requiere admin)
- `DELETE /api/admin/products/{id}` - Eliminar producto (requiere admin)

### Categorías

- `GET /api/categories` - Obtener todas las categorías
- `POST /api/admin/categories` - Crear categoría (requiere admin)
- `PUT /api/admin/categories/{id}` - Actualizar categoría (requiere admin)
- `DELETE /api/admin/categories/{id}` - Eliminar categoría (requiere admin)

### Carrito de Compras

- `GET /api/cart` - Obtener carrito (requiere header `X-Session-Id`)
- `POST /api/cart/items` - Agregar item al carrito
- `PUT /api/cart/items/{id}` - Actualizar cantidad
- `DELETE /api/cart/items/{id}` - Eliminar item
- `DELETE /api/cart` - Vaciar carrito

### Órdenes

- `POST /api/orders` - Crear orden desde carrito
- `GET /api/orders/{orderNumber}` - Consultar orden por número

## 🛒 Ejemplo de Flujo de Compra

```bash
# 1. Obtener productos
GET http://localhost:8080/api/products

# 2. Agregar al carrito (generar UUID para sessionId)
POST http://localhost:8080/api/cart/items
Headers: X-Session-Id: 550e8400-e29b-41d4-a716-446655440000
Body: {"productId": 1, "quantity": 2}

# 3. Ver carrito
GET http://localhost:8080/api/cart
Headers: X-Session-Id: 550e8400-e29b-41d4-a716-446655440000

# 4. Crear orden
POST http://localhost:8080/api/orders
Headers: X-Session-Id: 550e8400-e29b-41d4-a716-446655440000
Body: {
  "customerName": "Juan Pérez",
  "customerEmail": "juan@example.com",
  "customerPhone": "1234567890"
}
```

## 🗄️ Estructura de Base de Datos

### Tablas Principales

- `categories` - Categorías de productos
- `products` - Productos del catálogo
- `cart_items` - Items en carritos de compra
- `orders` - Órdenes de compra
- `order_items` - Items de las órdenes
- `users` - Usuarios administradores

## 🔧 Configuración CORS

El backend permite peticiones desde:
- `http://localhost:5173` (Frontend principal)
- `http://localhost:5174` (Panel admin)

Para agregar más orígenes, editar `SecurityConfig.java`

## 📝 Notas de Desarrollo

- Los datos de ejemplo se cargan automáticamente desde `data.sql`
- Las contraseñas se encriptan con BCrypt
- El token JWT expira en 24 horas (configurable en `application.properties`)
- Hibernate crea/actualiza las tablas automáticamente (`ddl-auto=update`)

## 🚨 Importante

**Antes de producción:**
1. Cambiar `jwt.secret` en `application.properties`
2. Cambiar contraseña del usuario admin
3. Configurar base de datos PostgreSQL de producción
4. Ajustar configuración de CORS según dominios de producción
5. Cambiar `ddl-auto` a `validate` o usar Flyway/Liquibase

## 📞 Soporte

Para dudas o problemas, contactar al equipo de desarrollo.
