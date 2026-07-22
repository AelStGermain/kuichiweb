# 🚀 Guía de Configuración de GitHub Pages para KuichiWeb

Esta guía explica cómo activar y configurar **GitHub Pages** en tu repositorio para que cualquier visitante pueda acceder a la demo interactiva de **KuichiWeb**.

---

## 📌 ¿Por qué una Demo Interactiva Estática?

GitHub Pages es un servicio para alojar contenido estático (HTML, CSS y JavaScript). Dado que el backend principal de KuichiWeb está desarrollado en Java/Spring Boot (que requiere un servidor activo), hemos creado una **versión Demo completa y funcional en el frontend** que incluye:

- 🏥 **Buscador y Filtro de Clínicas**: Por comuna, nombre y urgencias 24/7.
- ⭐ **Ficha de Clínicas y Reseñas**: Visualización de mapas, teléfono y publicación de opiniones con estrellas.
- 🏷️ **Catálogo de Ofertas**: Cupones de descuento interactivos con copia al portapapeles.
- 🐶 **Mis Mascotas**: Fichas sanitarias con persistencia en `localStorage`.
- 🛡️ **Simulador de Roles**: Cambio dinámico entre *Invitado*, *Usuario* y *Administrador*.

---

## ⚙️ Pasos para Configurar GitHub Pages en GitHub

Existen **dos formas simples** de desplegar la página en GitHub Pages:

### Opción A: Despliegue Automático mediante GitHub Actions (Recomendado)

El repositorio incluye un archivo de flujo automatizado en `.github/workflows/deploy.yml`.

1. Entra a tu repositorio en **GitHub.com**.
2. Ve a la pestaña **Settings** (Configuración) en la barra superior.
3. En el menú lateral izquierdo, haz clic en **Pages** (dentro de la sección *Code and automation*).
4. En **Build and deployment** -> **Source**, selecciona:
   - **GitHub Actions**
5. Guarda los cambios.
6. ¡Listo! Cada vez que hagas `git push` a la rama `main` o `master`, la acción desplegará la última versión automáticamente.

---

### Opción B: Despliegue Directo desde la Rama (Branch)

Si prefieres no usar GitHub Actions:

1. Ve a **Settings** -> **Pages**.
2. En **Build and deployment** -> **Source**, selecciona:
   - **Deploy from a branch**
3. En **Branch**, selecciona `main` (o `master`) y carpeta `/(root)`.
4. Haz clic en **Save**.
5. En 1-2 minutos la página estará activa en la URL:
   `https://aelstgermain.github.io/kuichiweb/`

---

## 🌐 Despliegue del Backend Java (Opcional para Producción Completa)

Si deseas alojar la aplicación Spring Boot completa con base de datos MySQL (TiDB Cloud):

1. **Render.com / Railway.app / Fly.io**:
   - Dockerfile incluido en la raíz (`Dockerfile`).
   - Comando de compilación: `./mvnw clean package -DskipTests`
   - Comando de ejecución: `java -jar target/KuichiWeb-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`
2. **Variables de Entorno**:
   - `SPRING_DATASOURCE_URL`: Tu cadena JDBC de TiDB / MySQL.
   - `SPRING_DATASOURCE_USERNAME`: Usuario de la base de datos.
   - `SPRING_DATASOURCE_PASSWORD`: Contraseña.
