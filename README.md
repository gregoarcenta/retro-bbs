# Retro BBS

API backend para una comunidad estilo foro retro: autenticacion, topicos, respuestas, votos y ranking mensual.

## Vista rapida

- Estado: en desarrollo activo, con backend funcional para el flujo principal
- Objetivo: construir una experiencia tipo BBS moderna con dinamica de comunidad
- Stack: Java 21, Spring Boot, PostgreSQL, Redis, Docker Compose
- Frontend: pendiente (estructura creada)

## Que ya esta funcionando

- Registro e inicio de sesion con JWT
- CRUD de topicos con restricciones por autor
- Respuestas por topico y marcado de solucion
- Votos UP y DOWN para topicos y respuestas
- Ranking mensual en Redis con Top 10 y consulta por usuario

## Arquitectura

Arquitectura orientada a puertos y adaptadores (hexagonal), separada por capas:

- dominio: modelos, reglas, excepciones y puertos
- aplicacion: casos de uso (servicios)
- infraestructura: controladores REST, seguridad JWT, adaptadores JPA y Redis

Esto permite mantener la logica de negocio desacoplada de frameworks y detalles de persistencia.

## Stack tecnico

Backend:

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Security (stateless + JWT)
- Spring Data JPA
- Spring Data Redis
- Jakarta Validation
- Maven Wrapper

Datos e infraestructura:

- PostgreSQL 16
- Redis 7
- Docker Compose
- Redis Commander

## Estructura del repositorio

```text
retro-bbs/
  backend/            # API Spring Boot
  frontend/           # UI (pendiente)
  docker-compose.yml  # Postgres + Redis + Redis Commander
  .env.example        # Variables base para contenedores
```

## Quickstart local

### 1) Preparar variables

Crear archivo .env en la raiz tomando como referencia .env.example:

- POSTGRES_DB
- POSTGRES_USER
- POSTGRES_PASSWORD

Variables requeridas por el backend:

- DB_URL
- DB_USERNAME
- DB_PASSWORD
- JWT_SECRET (base64)
- JWT_EXPIRATION (milisegundos)

Ejemplo en PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/retrobbs_db"
$env:DB_USERNAME="retrobbs_user"
$env:DB_PASSWORD="retrobbs_pass"
$env:JWT_SECRET="TU_SECRET_BASE64_AQUI"
$env:JWT_EXPIRATION="86400000"
```

### 2) Levantar infraestructura

```bash
docker compose up -d
```

Servicios:

- PostgreSQL: localhost:5432
- Redis: localhost:6379
- Redis Commander: <http://localhost:8081>

### 3) Ejecutar backend

En Windows (desde backend):

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux/macOS:

```bash
./mvnw spring-boot:run
```

API en:

- <http://localhost:8080>

## API disponible

Base path: /api

Auth:

- POST /auth/register
- POST /auth/login

Topicos:

- GET /topicos
- GET /topicos/{id}
- POST /topicos (auth)
- PUT /topicos/{id} (auth, autor)
- DELETE /topicos/{id} (auth, autor)

Respuestas:

- GET /topicos/{topicoId}/respuestas
- POST /topicos/{topicoId}/respuestas (auth)
- PATCH /topicos/{topicoId}/respuestas/{respuestaId}/solucion (auth, autor del topico)
- DELETE /topicos/{topicoId}/respuestas/{respuestaId} (auth, autor de la respuesta)

Votos:

- POST /votos/{targetId} (auth)
- DELETE /votos/{targetId}?targetType=TOPICO|RESPUESTA (auth)

Ranking:

- GET /ranking
- GET /ranking?mes=YYYY-MM
- GET /ranking/me (auth)

## Sistema de puntos

Reglas actuales:

- crear topico: +10
- crear respuesta: +5
- voto UP: +2
- voto DOWN: -1
- respuesta marcada como solucion: +15

Niveles:

- NOVATO
- REGULAR (>= 50)
- EXPERTO (>= 200)
- LEYENDA (>= 500)

## Datos iniciales

Se cargan categorias base al iniciar:

- General
- Hardware
- Reviews
- Ayuda
- Off-Topic

## Estado de avance

Hecho:

- autenticacion JWT y seguridad stateless
- topicos, respuestas y votos
- ranking mensual en Redis
- healthchecks en contenedores de Postgres y Redis

En progreso:

- mejora de validaciones y manejo de errores
- ampliacion de pruebas automatizadas

Pendiente:

- frontend web
- documentacion OpenAPI/Swagger
- CI/CD
- observabilidad (metricas y trazas)

## Licencia

Este proyecto esta licenciado bajo Apache License 2.0. Revisa el archivo LICENSE para mas detalles.
