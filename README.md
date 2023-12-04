## Instalación y ejecución

```sh
# Arranca servicios docker:
node exec_infra.js

# Instalar dependencias:
node install.js;

# Inicia Servicios:
node exec.js
```

## Url de servicios
- Observabilidad: http://localhost:3301/services

- Housekeeping API: http://localhost:8080/swagger-ui/index.html#/

- Pruebas de carga (locust): http://localhost:8089/

- Housekeeping Web:  http://localhost:3000


## Estructura del proyecto

Estructura por Dominio con Subdivisiones Técnicas para tu proyecto. Esta estructura agrupa las clases y archivos basados en su dominio funcional (como "Establishment" o "Reservation"), subdividiéndolos luego en capas técnicas como controladores, servicios, modelos, value objects, y repositorios.


Generación de identificadores de los modelos

//TODO Discutir id TSID por concurencia.

Puedes consultar nuestra [Estructura de ejmplo](./documentation/structure.md)
