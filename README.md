# Micronaut Application

Aplicación Micronaut creada con Java 21 y Maven.

## Requisitos

- JDK 21 o superior
- Maven 3.6 o superior

## Ejecutar la aplicación

Para ejecutar la aplicación:

```bash
mvn mn:run
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints

### Health Check

```bash
curl http://localhost:8080/health
```

Devuelve:
- `name`: Nombre de la aplicación
- `version`: Versión de la aplicación
- `environment`: Entorno de ejecución
- `status`: Estado de la aplicación (UP)
- `timestamp`: Marca de tiempo ISO 8601

### Swagger UI

La documentación interactiva de la API está disponible en Swagger UI:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI Spec (YAML)**: http://localhost:8080/swagger/micronaut-application-0.0.1.yml

## Ejecutar tests

```bash
mvn test
```

## Construir la aplicación

```bash
mvn package
```

