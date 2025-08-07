# Inventario fantasma

**Nivel:** Fácil

## Descripción
Comparar listas de productos entre dos almacenes y reportar diferencias de stock.

## Objetivo
Implementa una solución en Java que cumpla con la lógica descrita. Usa la plantilla en `src/com/walmarttech/Main.java` para comenzar.

## Cómo empezar
1. Clona este repositorio.
2. Dirígete a la carpeta `challenges/week-02-inventory-check`.
3. Abre `Main.java` y escribe tu solución.
4. ¡Comparte tu solución con la comunidad!
---
# Reto 02 - week-02-inventory-check: Solución Técnica

## Introducción

Esta solución aborda el "Reto 02 - Inventario Fantasma" propuesto por Walmart-Tech-Mexico, implementando una arquitectura modular que simula microservicios en un solo proyecto, utilizando Spring Boot (Java 17). La solución compara inventarios entre dos almacenes, identifica diferencias en stock y productos faltantes, y proporciona una API RESTful documentada con Swagger. Se aplican principios SOLID, el patrón de diseño Strategy y pruebas unitarias para garantizar calidad y mantenibilidad.

El código está alojado en el fork: [https://github.com/xsoto-developer/Challenge-inventory-check](https://github.com/xsoto-developer/Challenge-inventory-check). Este README está dirigido al equipo técnico de Walmart-Tech-Mexico para explicar los cambios realizados, la arquitectura, y cómo probar y contribuir a la solución.

## Objetivo

Implementar una solución que:
- Compare inventarios entre dos almacenes, identificando diferencias en stock y productos ausentes.
- Proporcione endpoints RESTful para listar productos, agregar productos y comparar inventarios.
- Use una arquitectura modular, desacoplada y escalable.
- Cumpla con los principios SOLID y aplique patrones de diseño.
- Incluya pruebas unitarias y documentación con Swagger.
- Siga un flujo de trabajo profesional con GitHub para enviar la solución.

## Cambios Realizados

La solución reestructura el código original de `Main.java` en una arquitectura modular basada en Spring Boot, con las siguientes mejoras:
- **Datos en memoria**: Se mantiene el almacenamiento en memoria como en el código original, usando un repositorio en memoria (`InMemoryAlmacenRepository`).
- **Arquitectura modular**: Paquetes separados para dominio, servicios, controladores, DTOs, excepciones y configuración.
- **Desacoplamiento**: Uso de interfaces en todas las capas (repositorio, servicio, controlador) para cumplir con el principio de Inversión de Dependencias.
- **Endpoints RESTful**: Implementación de tres endpoints para listar, agregar y comparar productos.
- **Documentación**: Configuración de Swagger para documentar la API.
- **Pruebas**: Suite de pruebas unitarias con JUnit 5, cubriendo casos normales y extremos.
- **Flujo de trabajo**: Instrucciones para fork, pull request y CI/CD con GitHub Actions.

## Arquitectura y Patrones de Diseño

### Estructura del Proyecto
```
src/main/java/com/walmarttech/inventory
├── controller
│   └── AlmacenController.java
├── domain
│   ├── Almacen.java
│   ├── Producto.java
│   └── ComparisonResult.java
├── dto
│   ├── ProductoDTO.java
│   └── ComparisonResultDTO.java
├── repository
│   ├── AlmacenRepository.java
│   └── InMemoryAlmacenRepository.java
├── service
│   ├── AlmacenService.java
│   ├── ComparisonStrategy.java
│   └── DefaultComparisonStrategy.java
├── exception
│   ├── AlmacenNotFoundException.java
│   └── ProductoNotFoundException.java
└── config
    └── SwaggerConfig.java
```

### Patrones de Diseño
- **Strategy**: Se implementa la interfaz `ComparisonStrategy` con una implementación concreta (`DefaultComparisonStrategy`) para la lógica de comparación de inventarios. Esto permite cambiar o extender la lógica de comparación sin modificar el código existente (cumple con Open/Closed).
- **Repository**: Se usa `AlmacenRepository` para abstraer el acceso a datos, facilitando la escalabilidad (por ejemplo, cambiar a una base de datos en el futuro).
- **DTO**: Se utilizan `ProductoDTO` y `ComparisonResultDTO` para desacoplar la capa de presentación de la capa de dominio.

### Principios SOLID
- **S (Single Responsibility)**: Cada clase tiene una sola responsabilidad. Por ejemplo, `AlmacenController` maneja solicitudes HTTP, `AlmacenService` contiene la lógica de negocio, y `InMemoryAlmacenRepository` gestiona el almacenamiento.
- **O (Open/Closed)**: La lógica de comparación es extensible mediante `ComparisonStrategy`.
- **L (Liskov Substitution)**: Las interfaces (`AlmacenRepository`, `ComparisonStrategy`) permiten sustituir implementaciones sin afectar el comportamiento.
- **I (Interface Segregation)**: Las interfaces son específicas y pequeñas (por ejemplo, `AlmacenRepository` solo define métodos para guardar y buscar almacenes).
- **D (Dependency Inversion)**: Las dependencias se inyectan a través de interfaces (por ejemplo, `AlmacenService` depende de `AlmacenRepository` y `ComparisonStrategy`).

## Archivo `pom.xml`

El archivo `pom.xml` configura un proyecto Spring Boot con las dependencias necesarias:

**Dependencias clave**:
- `spring-boot-starter-web`: Para crear la API RESTful.
- `springdoc-openapi-starter-webmvc-ui`: Para generar documentación Swagger.
- `spring-boot-starter-test` y `junit-jupiter`: Para pruebas unitarias.

## Endpoints RESTful

La API proporciona los siguientes endpoints, documentados con Swagger:

1. **GET /almacenes/{id}/productos**
    - **Descripción**: Lista los productos de un almacén específico.
    - **Parámetros**: `id` (ID del almacén, en la ruta).
    - **Respuesta**: Lista de `ProductoDTO` en formato JSON (código 200) o error 404 si el almacén no existe.
    - **Ejemplo**:
      ```json
      [
          {
              "codigo": "001",
              "nombre": "Camiseta",
              "categoria": "Ropa",
              "precio": 25.0,
              "stock":率先
          }
      ]
      ```

2. **POST /almacenes/{id}/productos**
    - **Descripción**: Agrega un nuevo producto al inventario de un almacén.
    - **Parámetros**: `id` (ID del almacén, en la ruta), cuerpo JSON con `ProductoDTO`.
    - **Respuesta**: Código 200 si se agrega correctamente, 404 si el almacén no existe, 400 si los datos son inválidos.
    - **Ejemplo de cuerpo**:
      ```json
      {
          "codigo": "002",
          "nombre": "Pantalón",
          "categoria": "Ropa",
          "precio": 45.0,
          "stock": 30
      }
      ```

3. **GET /almacenes/comparar?almacen1={id1}&almacen2={id2}**
    - **Descripción**: Compara los inventarios de dos almacenes y devuelve las diferencias.
    - **Parámetros**: `almacen1` y `almacen2` (IDs de los almacenes, como query params).
    - **Respuesta**: Objeto `ComparisonResultDTO` en formato JSON (código 200) o error 404 si algún almacén no existe.
    - **Ejemplo de respuesta**:
      ```json
      {
          "almacen1Nombre": "Almacén Norte",
          "almacen2Nombre": "Almacén Sur",
          "diferencias": [
              "Producto Camiseta: stock 50 en Almacén Norte, stock 30 en Almacén Sur",
              "Producto Gorra: presente en Almacén Sur, ausente en Almacén Norte"
          ]
      }
      ```

## Pruebas

Se implementaron pruebas unitarias con JUnit 5 para la lógica de comparación de inventarios (`DefaultComparisonStrategy`), cubriendo:
- Productos con diferente stock.
- Productos presentes en un almacén pero no en el otro.
- Almacenes vacíos.
- Productos con stock cero.

**Ejemplo de prueba** (`DefaultComparisonStrategyTest.java`):
- Verifica que las diferencias en stock se detecten correctamente.
- Asegura que los productos ausentes se reporten.
- Cubre casos extremos como almacenes vacíos.

**Cobertura**: Superior al 80% para la lógica de comparación.

**Ejecutar pruebas**:
```bash
mvn test
```

## Configuración de Swagger

La API está documentada con SpringDoc OpenAPI, accesible en: `http://localhost:8080/swagger-ui.html`.

**Pasos**:
1. Inicia la aplicación:
   ```bash
   mvn spring-boot:run
   ```
2. Accede a Swagger para probar los endpoints interactivamente.

## Flujo de Trabajo con GitHub

### Configuración del Entorno
1. Clona el repositorio:
   ```bash
   git clone https://github.com/xsoto-developer/Challenge-inventory-check.git
   ```
2. Configura el upstream:
   ```bash
   git remote add upstream https://github.com/Walmart-Tech-Mexico/Challenge-inventory-check.git
   ```

### Desarrollo
1. Crea una rama:
   ```bash
   git checkout -b feature/inventory-check
   ```
2. Implementa los cambios descritos en este README.
3. Ejecuta pruebas:
   ```bash
   mvn test
   ```

### Enviar la Solución
1. Commitea los cambios:
   ```bash
   git add .
   git commit -m "Implementa solución para Reto 02 con Spring Boot y Swagger"
   git push origin feature/inventory-check
   ```
2. Crea un pull request:
    - Ve a tu fork: `https://github.com/xsoto-developer/Challenge-inventory-check`.
    - Haz clic en "New Pull Request".
    - Selecciona la rama `feature/inventory-check` y apunta al repositorio original: `https://github.com/Walmart-Tech-Mexico/Challenge-inventory-check`.
    - Descripción sugerida del PR:
      ```
      Implementación del Reto 02 - Inventario Fantasma con Spring Boot, siguiendo principios SOLID y el patrón Strategy. Incluye endpoints RESTful, documentación Swagger, pruebas unitarias con JUnit 5 y un pipeline CI/CD con GitHub Actions.
      ```

### CI/CD con GitHub Actions
Un pipeline simple está configurado en `.github/workflows/ci.yml`:
- Ejecuta pruebas automáticamente en cada push o pull request.
- Usa JDK 17 y Maven para construir y probar el proyecto.

**Contenido de `ci.yml`**:
```yaml
name: CI Pipeline
on:
  push:
    branches: [ main, feature/* ]
  pull_request:
    branches: [ main ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn -B package --file pom.xml
      - name: Run tests
        run: mvn test
```

## Instrucciones para Probar

1. Configura el entorno (Java 17, Maven).
2. Clona el repositorio y construye el proyecto:
   ```bash
   mvn clean install
   ```
3. Inicia la aplicación:
   ```bash
   mvn spring-boot:run
   ```
4. Accede a Swagger: `http://localhost:8080/swagger-ui.html`.
5. Prueba los endpoints con datos de ejemplo:
    - Agrega productos a almacenes.
    - Lista productos.
    - Compara inventarios.

## Conclusión

Esta solución proporciona una implementación robusta, modular y escalable para el Reto 02, cumpliendo con las mejores prácticas de desarrollo. El uso de Spring Boot, principios SOLID, el patrón Strategy, pruebas unitarias y documentación Swagger asegura un código de alta calidad. El flujo de trabajo con GitHub facilita la colaboración y la integración continua.

**Contribuciones**:
- Crea una nueva rama para mejoras o correcciones.
- Sigue el mismo flujo de trabajo para enviar pull requests.

**Contacto**:
Para dudas o sugerencias, contacta al equipo técnico a través del repositorio de GitHub.