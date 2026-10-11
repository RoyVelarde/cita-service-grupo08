# Cita Service - Grupo 08

### Configurar las variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

```dotenv
CITA_DB_PASSWORD=UnaClaveSegura
```

### Construir la imagen e iniciar los contenedores

```powershell
docker compose up --build -d
```

### URL base de la API REST

Cuando el microservicio se ejecuta localmente mediante Docker:

```text
http://localhost:8081/api/v1
```