# Comandos Útiles para Deployment - KuichiWeb

## 🐳 Docker Commands

### Construir imagen
```bash
docker build -t <usuario>/kuichiweb:latest .
```

### Listar imágenes locales
```bash
docker images | grep kuichiweb
```

### Probar localmente (con H2)
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  <usuario>/kuichiweb:latest
```

### Probar localmente (simulando producción)
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e TIDB_HOST=tu-host \
  -e TIDB_PORT=4000 \
  -e TIDB_DATABASE=kuichidb \
  -e TIDB_USER=tu-usuario \
  -e TIDB_PASSWORD=tu-password \
  <usuario>/kuichiweb:latest
```

### Login Docker Hub
```bash
docker login
```

### Push a Docker Hub
```bash
docker push <usuario>/kuichiweb:latest
```

### Ver logs de contenedor
```bash
docker logs <container-id>
```

### Eliminar imagen local
```bash
docker rmi <usuario>/kuichiweb:latest
```

---

## 📦 Maven Commands

### Compilar sin tests
```bash
./mvnw clean package -DskipTests
```

### Correr con profile dev (local)
```bash
./mvnw spring-boot:run
```

### Correr con profile prod (simular cloud)
```bash
export TIDB_HOST=tu-host
export TIDB_PORT=4000
export TIDB_DATABASE=kuichidb
export TIDB_USER=tu-usuario
export TIDB_PASSWORD=tu-password
export SPRING_PROFILES_ACTIVE=prod

./mvnw spring-boot:run
```

---

## 🔍 Verificar Deployment

### Test Health Check
```bash
curl https://tu-app.koyeb.app/actuator/health
```

**Respuesta esperada:**
```json
{"status":"UP"}
```

### Test API REST
```bash
# Listar clínicas (público)
curl https://tu-app.koyeb.app/api/clinics

# Registrar usuario
curl -X POST https://tu-app.koyeb.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "password": "password123",
    "fullName": "Test User",
    "email": "test@test.com"
  }'
```

---

## 🗄️ TiDB Database Commands

### Conectar desde terminal (si tienes mysql client)
```bash
mysql -h <TIDB_HOST> -P 4000 -u <TIDB_USER> -p <TIDB_DATABASE>
```

### Ver tablas
```sql
USE kuichidb;
SHOW TABLES;
```

### Ver usuarios creados
```sql
SELECT id, username, full_name, role FROM app_user;
```

### Ver clínicas
```sql
SELECT id, name, address, email FROM clinic;
```

### Limpiar datos (reset)
```sql
DROP DATABASE kuichidb;
CREATE DATABASE kuichidb;
```

---

## 🚀 Quick Deploy Checklist

```bash
# 1. Build imagen
docker build -t usuario/kuichiweb:latest .

# 2. Test local
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev usuario/kuichiweb:latest

# 3. Push a Docker Hub
docker login
docker push usuario/kuichiweb:latest

# 4. Ir a Koyeb y desplegar desde Docker Hub imagen
# (Configurar variables de entorno en Koyeb UI)

# 5. Verificar
curl https://tu-app.koyeb.app/actuator/health
```

---

## 📝 Variables de Entorno para Koyeb

Copiar y pegar en Koyeb Environment Variables:

```
SPRING_PROFILES_ACTIVE=prod
TIDB_HOST=gateway01.us-west-2.prod.aws.tidbcloud.com
TIDB_PORT=4000
TIDB_DATABASE=kuichidb
TIDB_USER=<tu_usuario>
TIDB_PASSWORD=<tu_password>
```

---

## 🔧 Troubleshooting

### Ver logs en Koyeb
1. Koyeb Dashboard
2. Click en tu app
3. Pestaña "Logs"
4. Ver últimos 100 logs

### Rebuild imagen sin cache
```bash
docker build --no-cache -t usuario/kuichiweb:latest .
```

### Force pull nueva imagen en Koyeb
1. Koyeb → Settings → Redeploy
2. Click "Redeploy"
