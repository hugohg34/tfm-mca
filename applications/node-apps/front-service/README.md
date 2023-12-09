# Front Service - Node.js

## Introducción

 **Descripción**: Este módulo actúa como la interfaz web entre el cliente y los servicios backend, encargándose de la orquestación de las solicitudes y respuestas.

**Funcionalidades**:

- Servidor HTTP para la interacción con el cliente.

- Manejo y enrutamiento de peticiones siguiendo el patrón CQRS (Command Query Responsibility Segregation).

  - Enrutar peticiones de consulta `Query` a base de datos Redis. 

  - Enrutar peticiones de  comando `Command` a backend house-keeping-service.

- **Stack Tecnológico**: Node.js.


### Interfaz Web

Interfaz web implementado en Vue.js para ofrecer una visión preliminar de la interfaz de usuario.

Se encuentra en la carpeta `frontend` del repositorio.

### Observabilidad con OpenTelemetry

- **Descripción**: Integración de OpenTelemetry para proporcionar observabilidad completa desde el front-end hasta base de datos Redis, y desde el front-end hasta el backend y PostgreSQL.

## Configuración y Ejecución en entorno local

**Para iniciar la aplicación:**

```sh
# Instalar dependencias:
node ./install.js

# Inicia App para desarrollo:
nodemon ./src/app.js

# iniciar aplicación auto instrumentada
node --require ./src/instrumentation.cjs ./src/app.js


```

**Para la interfaz web de Vue.js:**

```sh
cd frontend

# Instalar dependencias:
npm install

# Compilar para produccion
npm run build

# Compilar y auto recarga para desarrollo
npm run serve

```

### Url de servicios

- Web:  http://localhost:3000

- Web Vue.js de desarrollo: http://localhost:8081/ 


## Estructura del proyecto

La estructura del repositorio es la siguiente:

- **`applications/`:** Contiene aplicaciones desarrolladas en Spring Boot y Node.js.
- **`shared-libraries/`:** Librerías compartidas entre las aplicaciones.
- **`infrastructure/`:**: Scripts y configuraciones para la infraestructura.
- **`scripts/`:**: Scripts para automatización y gestión del proyecto.
- **`documentation/`:**: Documentación, incluyendo este README.

Puedes consultar nuestra [Estructura de ejemplo](./documentation/structure.md)

```
├── frontend            # Directorio raíz de Vue.js
│   ├── dist            # Código de producción
│   ├── public          # Ficheros publicos
│   └── src
│   │   ├── App.vue     # Inicio de aplicación
│   │   ├── components                  # Componentes/modulos web
│   │   │   ├── DateRack.vue            # Reloj de fecha y hora
│   │   │   └── EstablishmentRack.vue   # Rack de habitaciones
│   │   └── ...
└── src
    ├── app.js                  # Inicio de aplicación
    ├── config
    ├── http                       # Servidor express.
    │   ├── expressServer.js       # Instancia y configuración 
    │   ├── roomRackController.js  # Maneja y convierte json
    │   └── router.js              # Disapcher urls a controladores.
    │
    ├── instrumentation.cjs     # Instrumentación de OpenTelemetry.
    ├── kafka                   # Conexión con broker Kafka.
    ├── redis                   # Conexión de DB Redis.
    └── services                # Implementa lógica de negocio.

```

## Técnologias utilizadas

**Base de datos en memoria - Redis**

Libreria `ioredis`. Cliente conexión con Redis, permite el uso de pipelines es eficiente y ayuda a reducir la sobrecarga de realizar múltiples idas y vueltas a DB, especialmente cuando se recuperan o actualizan múltiples registros.


## Requisitos

Herramientas y software donde se ha probado el proyecto:

- node v20.10.0

- npm 10.2.4

- docker 24.0.7

### Configuración del Entorno

Es necesario arrancar los servicios de infraestructura, desde la raíz del repositorio:

```sh
node ./exec_infra.js
```




