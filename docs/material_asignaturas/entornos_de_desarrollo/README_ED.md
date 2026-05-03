## Documentación de Entornos de Desarrollo: IronBase Track
El presente documento describe la configuración del entorno de desarrollo, la metodología de control de versiones y la estructura organizativa del repositorio para el proyecto IronBase Track.

## 1. Gestión de Control de Versiones
Para la gestión del ciclo de vida del software se ha utilizado Git como sistema de control de versiones distribuido, alojando el repositorio remoto en la plataforma GitHub.

**1.1. Historial de Commits**
El desarrollo del proyecto se ha gestionado mediante un flujo de trabajo basado en commits descriptivos. Aunque la implementación de Git se intensificó en las fases intermedias del desarrollo, el historial refleja la evolución modular del sistema:

* Inicialización: Configuración de la estructura Maven y dependencias básicas.

* Persistencia: Implementación de la capa DAO y scripts de base de datos.

* Interfaz: Desarrollo de las vistas FXML y controladores JavaFX.

* Refactorización: Limpieza de código, optimización de imports y resolución de advertencias del IDE.

* **1.2. Flujo de Trabajo**
Se ha seguido una metodología de desarrollo incremental. Cada conjunto de cambios se ha validado localmente antes de ser integrado en el repositorio remoto, asegurando que las versiones publicadas sean funcionales y estables.

## 2. Estructura del Repositorio
El repositorio sigue el estándar de proyectos Maven, lo que garantiza una organización clara y una gestión de dependencias eficiente. La jerarquía de directorios se define de la siguiente manera:

* **/src/main/java**: Contiene el código fuente organizado por paquetes siguiendo el patrón MVC (model, controller, dao, util).

* **/src/main/resources**: Aloja los archivos de configuración y las vistas FXML de la interfaz de usuario.

* **/docs**: Carpeta centralizadora de la documentación técnica del proyecto.

* **/docs/material_asignaturas/**: Subdirectorio especializado para los requisitos específicos de cada módulo:

* **/docs/material_asignaturas/base_de_datos**: Scripts SQL y diagramas de diseño.

* **/docs/material_asignaturas/entornos_desarrollo**: Documentación del proyecto. 

* **/docs/material_asignaturas/sistemas_informaticos**: Informes de requisitos y capturas de funcionamiento.

* **/docs/material_asignaturas/lenguaje_de_marcas**: Archivos XML y esquemas de validación XSD.

* **pom.xml**: Archivo de configuración de Maven donde se declaran las dependencias (JavaFX, JDBC driver, etc.) y la versión de Java utilizada.

## 3. Tecnologías y Herramientas del Entorno
Para el desarrollo de la aplicación se han empleado las siguientes herramientas y entornos:

* **IDE**: IntelliJ IDEA, configurado con herramientas de análisis estático de código para la detección de advertencias y optimización de rendimiento.

* **JDK**: Java Development Kit versión 21.

* **JavaFX**: Versión 21.0.6 para el desarrollo de la interfaz gráfica.

* **Gestor de Dependencias**: Apache Maven, encargado de la construcción y gestión de librerías externas.

* **Gestión de Base de Datos**: DBeaver / MySQL Workbench para la administración del servidor de datos relacional.

## 4. Documentación Técnica
Siguiendo los estándares de entornos de desarrollo, se ha priorizado el uso de Markdown para la redacción de la documentación. Este formato permite una lectura clara directamente desde el repositorio, facilitando la transferencia de conocimiento y el mantenimiento del software.