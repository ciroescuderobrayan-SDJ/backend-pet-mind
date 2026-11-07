# Integración de PetMind: frontend, backend y Neon

Fecha de la revisión: 30 de octubre de 2026.

Este documento registra el despliegue de prueba y la integración inicial del catálogo público de mascotas. Los datos y estados descritos pueden cambiar; compruébalos antes de tomar decisiones posteriores.

## 1. Componentes

| Componente | Tecnología | Ubicación o dirección |
|---|---|---|
| Frontend | React y Vite | `Proyecto Integrador/04-frontend-petmind` |
| Backend | Spring Boot y Java 21 | `Proyecto Integrador/01-backend-pet-mind` |
| Backend desplegado | Render, servicio web Docker gratuito | `https://backend-pet-mind.onrender.com` |
| Base de datos de prueba | PostgreSQL en Neon | Proyecto `pet-mind-backend`, rama `production`, base `neondb` |

El usuario indicó que los datos de Neon son de prueba. Aunque la rama se llame `production`, no se deduce de su nombre que los registros sean reales. El frontend se comunica con el backend por HTTP; el backend se conecta a PostgreSQL.

## 2. Despliegue del backend

- Render usa el repositorio del backend, la rama `main`, el plan gratuito y el archivo `./dockerfile`, escrito en minúsculas.
- El Dockerfile usa Java 21 para compilar y ejecutar. La actualización se incorporó mediante el PR #58.
- Las variables `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` se configuraron en Render importando el archivo `.env` del backend. Sus valores no se incluyen en este documento.
- En Render se configuró `/v3/api-docs` como ruta de comprobación. La documentación interactiva está en `https://backend-pet-mind.onrender.com/swagger-ui.html`.
- Se activó el despliegue automático desde `main`. Render mostró el PR #59, commit `52b0ac9`, como `Live` después de incorporar CORS.
- En el plan gratuito, Render puede suspender el servicio por inactividad. En la prueba, la primera respuesta tardó y una recarga inmediata respondió rápidamente. Esto apunta al tiempo de arranque, pero no mide por separado todas las causas de latencia.

Antes se intentó desplegar en Back4App, donde dos intentos no llegaron a iniciar la compilación porque el servicio informó que no podía iniciar una máquina de build. Railway mostró que el registro estaba deshabilitado en ese momento. Esos resultados no demuestran un defecto del código.

## 3. Variables y CORS

En la raíz del frontend se creó `.env.local` con:

```env
VITE_API_URL=https://backend-pet-mind.onrender.com/api
```

Vite necesita reiniciarse después de cambiar este archivo. Las variables `VITE_` se incluyen en el código que recibe el navegador: la URL es pública, por lo que nunca deben colocarse contraseñas ni credenciales allí. Para un despliegue del frontend hay que configurar también la variable en su plataforma de hosting.

El backend incorpora `CorsConfig`, que permite llamadas a `/api/**` desde `http://localhost:5173`. La prueba desde el navegador primero mostró un bloqueo de CORS y, después del despliegue del PR #59, la misma consulta devolvió `[]`. Cuando el frontend tenga un dominio desplegado, habrá que añadir su origen autorizado y volver a desplegar el backend.

CORS controla el acceso desde navegadores; no implementa autenticación ni protege por sí solo los endpoints.

## 4. Integración de mascotas

En el frontend se prepararon estos archivos:

- `src/services/api.js`: lee `VITE_API_URL` y realiza consultas GET, indicando errores HTTP.
- `src/services/mascotasService.js`: consulta mascotas publicadas y disponibles.
- `src/services/mascotaMapper.js`: adapta los datos de la API a los nombres y valores usados por la interfaz.
- `src/hooks/useMascotasPublicas.js`: gestiona carga, error, reintento y resultado de la consulta.
- `src/views/adopcion/PerfilMascotaPublica.jsx`: muestra el detalle público de una mascota del backend.

El listado de Inicio y la página Adoptar usan la consulta pública. La interfaz muestra carga, error o lista vacía sin reemplazar una respuesta vacía por mascotas inventadas. El detalle público utiliza una ruta separada: `/adoptar/backend/:id`.

La consulta usa:

```http
GET /api/mascotas/por-estado?estadoPublicacion=PUBLICADA&estadoAdopcion=DISPONIBLE
```

La tarjeta y los filtros usan campos adaptados, por ejemplo `nombre` a `name`, `edadMeses` a `ageMonths` y `PEQUENO` a `Pequeño`. Los IDs de la API se convierten a texto para compararlos con los parámetros de ruta.

El panel de fundación conserva operaciones locales de demostración; crear, editar o eliminar allí todavía no equivale a guardar cambios en el backend. Las fotografías, los datos públicos de la fundación, los favoritos y la solicitud de adopción para mascotas del backend siguen pendientes de integración.

## 5. Pruebas observadas

1. El endpoint público respondió `[]` desde el navegador y el servicio del frontend también devolvió `[]`.
2. La página Adoptar mostró su estado de carga y, al terminar, el mensaje de que no había mascotas disponibles. Neon mostraba la tabla `mascotas` sin filas antes de la prueba.
3. Con autorización del usuario se creó desde Swagger una mascota ficticia llamada `PRUEBA - No adoptar`, asociada a la fundación activa con ID `1`. El POST respondió `201` y devolvió el ID de mascota `1`, con estado `PUBLICADA` y `DISPONIBLE`.
4. El frontend mostró la tarjeta con el nombre, edad, tamaño y ciudad recibidos. No mostró una fotografía, que sigue pendiente.
5. El filtro `Pequeño` mantuvo la tarjeta visible y `Mediano` la ocultó. El detalle `/adoptar/backend/1` mostró los datos de prueba.
6. Se inició desde Swagger `DELETE /api/mascotas/1` para retirar la mascota de prueba. **La última captura muestra la petición cargando; no confirma el resultado.** No debe repetirse hasta comprobar si ya terminó. El servicio implementa borrado lógico, por lo que una eliminación exitosa marca el registro como inactivo.

Para confirmar la limpieza, revisar el código de respuesta del DELETE en Swagger y consultar el listado público. Si la respuesta no quedó disponible, comprobar el estado del registro con una consulta de lectura antes de decidir si hace falta reintentar. El ID `1` es el de la mascota de prueba; no confundirlo con el ID de fundación.

## 6. Verificaciones y cambios locales

Según el resultado comunicado por Codex en el proyecto frontend, `npm run lint`, `npm run build` y `git diff --check` pasaron después de corregir caracteres dañados. También informó comprobaciones locales del adaptador. Esos comandos no se repitieron al escribir este documento.

La prueba visual del catálogo, los filtros y el detalle se realizó en el navegador del usuario con el registro de prueba. Eso no valida todavía creación de mascotas desde el panel, fotografías, inicio de sesión ni solicitudes de adopción.

Al redactar este documento, el frontend tenía cambios locales sin commit, incluidos cambios previos en `package.json` y `package-lock.json` y los archivos de esta integración. Deben revisarse y conservarse antes de preparar commits o un PR. Este documento también es un cambio local; no se ha publicado automáticamente.

## 7. Pendientes recomendados

1. Confirmar el resultado del DELETE de la mascota de prueba y que no aparezca en el catálogo.
2. Revisar los cambios del frontend, hacer commits según el acuerdo del equipo y preparar el PR correspondiente.
3. Conectar los datos y fotografías reales de mascotas y fundaciones, con estados de carga y permisos adecuados.
4. Implementar autenticación y autorización antes de utilizar datos reales. En la revisión anterior, el inicio de sesión del frontend era una demostración local y el backend no tenía implementada la protección de acceso necesaria; también debe corregirse el almacenamiento de contraseñas.
5. Completar solicitudes de adopción, favoritos y las operaciones del panel mediante endpoints y reglas de negocio reales.
6. Cuando se despliegue el frontend, configurar `VITE_API_URL`, autorizar su origen exacto en CORS y repetir las pruebas de navegador.

El backend se inicia con `spring.jpa.hibernate.ddl-auto=update` y contiene un inicializador de datos de ejemplo. Antes de apuntarlo a otra base, revisar estos comportamientos y proteger cualquier información existente.
