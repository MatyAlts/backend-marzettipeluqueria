# Notas Importantes - Backend

## Instalación del Backend Spring Boot

El backend está completamente implementado pero requiere configuración manual debido a PostgreSQL.

### Requisitos Previos

1. **Java 17 o superior**
2. **Maven 3.6+**
3. **PostgreSQL 12+** instalado y corriendo

### Pasos de Configuración

#### 1. Crear la Base de Datos

Abre pgAdmin o psql y ejecuta:

```sql
CREATE DATABASE peluqueria_marzetti;
```

#### 2. Configurar Credenciales

Edita `backend/src/main/resources/application.properties` y ajusta:

```properties
spring.datasource.username=TU_USUARIO_POSTGRES
spring.datasource.password=TU_CONTRASEÑA_POSTGRES
```

#### 3. Ejecutar el Backend

Desde el directorio `backend`:

```bash
mvn clean install
mvn spring-boot:run
```

O usa el script de Windows:

```bash
.\start.bat
```

El servidor estará disponible en `http://localhost:8080`

### Datos Iniciales

El sistema carga automáticamente:
- 6 categorías
- 8 productos de ejemplo
- Usuario admin: `admin` / `admin123`

### Endpoints Importantes

- API REST: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

### Problemas Comunes

**PostgreSQL no Connection**
- Verificar que PostgreSQL esté corriendo
- Verificar puerto correcto (5432)
- Verificar credenciales en application.properties

**Port 8080 already in use**
- Cambiar puerto en application.properties: `server.port=8081`

**JPA/Hibernate errors**
- Asegurar que la base de datos existe
- Verificar que el usuario tenga permisos

---

## Próximos Pasos

Una vez que el backend esté corriendo:

1. Actualizar frontend para conectarse al backend
2. Iniciar admin panel
3. Probar funcionalidad completa de e-commerce
