# IronBase Track 

IronBase Track es una aplicación de escritorio (Cliente Pesado) desarrollada en **JavaFX** y conectada a una base de datos relacional **MariaDB**. Su objetivo es proporcionar una herramienta integral para la gestión de entrenamientos de fuerza, permitiendo a los entrenadores (Administradores) gestionar el catálogo de ejercicios y rutinas, y a los atletas registrar su progresión (Repetición Máxima - RM).

Este repositorio constituye la entrega final del **Proyecto Intermodular de 1º de DAM**.



## Características Principales

* **Sistema de Roles:** Acceso diferenciado mediante login para Administradores y Atletas.
* **Gestión de Catálogo (CRUD):** El administrador puede crear, leer y eliminar ejercicios de la base de datos, con campos validados (mecánica, material, grupo muscular).
* **Gestión de Plantillas (Rutinas):** Sistema de asignación de rutinas preconfiguradas (Weider, Torso/Pierna, Powerlifting) mediante tablas intermedias (N:M).
* **Seguridad:** Conexión a la base de datos blindada contra Inyección SQL mediante el uso de `PreparedStatement`.
* **Exportación de Datos:** Generación de catálogos de ejercicios en formato **XML**, con validación estricta mediante **XSD** para garantizar la integridad estructural.



## Tecnologías Utilizadas (Integración de Módulos)

* **Bases de Datos:** MariaDB. Diseño de un modelo relacional estricto que refleja la lógica real del entrenamiento. Uso de tablas intermedias para resolver relaciones N:M (ej. `Rutina_Ejercicio`). Incluye gestión de roles y seguridad SQL.
* **Programación:** Desarrollo de la lógica de negocio y arquitectura MVC/DAO en Java. Interfaz gráfica de escritorio desarrollada con **JavaFX**, implementando un sistema CRUD y control de sesiones.
* **Entornos de Desarrollo:** Control de versiones con Git/GitHub, diseño de modelos lógicos y conceptuales (Draw.io), y buenas prácticas de codificación.
* **Lenguajes de Marcas:** Generación de estructura de datos mediante **XML** y validación estricta con **XSD** para la exportación del catálogo de ejercicios. Estilización de la interfaz de JavaFX mediante CSS.
* **Sistemas Informáticos:** Documentación técnica de despliegue y justificación de arquitectura hardware/software del entorno de ejecución local.


## Estructura del Repositorio para Evaluación

Para facilitar la corrección por parte del equipo docente, el código, el ejecutable y los entregables específicos de cada módulo se encuentran organizados de la siguiente manera:

*   **`IronBaseTrack.jar`** -> Archivo ejecutable de la aplicación compilada. **[Prog]**
*   **`/src/`** -> Código fuente Java (Controladores, Modelos, DAOs) y vistas de interfaz (`.fxml`). **[Prog]**
*   **`/docs/diagramas/`** -> Modelos Entidad-Relación y Modelo Relacional. **[ED] [BD]**
*   **`/docs/xml/`** -> Archivos de exportación generados (ej. `datos.xml`). **[LM]**
*   **`/docs/material_asignaturas/base_de_datos/`** -> Scripts de creación e inserción SQL. **[BD]**
*   **`/docs/material_asignaturas/entornos_de_desarrollo/`** -> Documentación específica de la asignatura. **[ED]**
*   **`/docs/material_asignaturas/lengua_de_marcas/`** -> Documentación y esquemas de validación XSD. **[LM]**
*   **`/docs/material_asignaturas/programacion/`** -> Documentación técnica y memorias del software. **[Prog]**
*   **`/docs/material_asignaturas/sistemas_informaticos/`** -> Informe técnico del Entorno de Ejecución y arquitectura. **[SI]**
*   **`/docs/material_asignaturas/ampliacion_de_programacion/`** -> Documentación técnica sobre optimizaciones al proyecto. **[AProg]**


## Instalación y Ejecución

Para desplegar este proyecto en un entorno local, por favor consulte el documento detallado ubicado en `/docs/material_asignaturas/sistemas_informaticos/README_Sistemas_informaticos.md`, donde se especifican los requisitos de la máquina virtual de Java (JVM) y la configuración del servidor MariaDB local.


**Desarrollado por:** [Carlos Molina Abril] - Estudiante de 1º DAM.
