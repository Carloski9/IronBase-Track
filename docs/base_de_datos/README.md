# Documentación de Base de Datos: IronBase Track

El presente documento detalla el diseño, la estructura y los scripts correspondientes a la base de datos relacional del proyecto **IronBase Track**. Este directorio contiene todos los entregables requeridos para la definición, población y consulta del modelo de datos.

## 1. Contenido del Directorio

El repositorio cuenta con los siguientes archivos correspondientes a la capa de persistencia de datos:

*   **diagrama_ER** (PDF/Imagen): Modelo conceptual de la base de datos (Diagrama Entidad-Relación) que ilustra las entidades principales y sus cardinalidades.
*   **modelo_relacional** (PDF/Imagen): Traducción del modelo conceptual a tablas relacionales, definiendo claves primarias, claves foráneas y tipos de datos.
*   **1_creacion_tablas.sql**: Script DDL (Data Definition Language) que genera la estructura completa de la base de datos de forma limpia.
*   **2_insercion_datos.sql**: Script DML (Data Manipulation Language) que inserta un catálogo estandarizado de 63 ejercicios, configura los roles del sistema y establece usuarios de prueba.
*   **3_consultas.sql**: Batería de consultas de selección (DQL) que incluyen cruces de tablas (`JOIN`), agrupaciones y ordenamientos para validar el correcto funcionamiento del modelo.

## 2. Diseño y Arquitectura de la Base de Datos

La base de datos ha sido diseñada aplicando reglas de normalización para garantizar la integridad referencial y minimizar la redundancia de la información. A continuación se detallan las decisiones de diseño fundamentales estructuradas por módulos funcionales:

### 2.1. Gestión de Accesos y Usuarios
Para el control de permisos se ha evitado la redundancia de tablas por tipo de usuario. Se ha implementado un modelo dinámico mediante dos tablas:
*   **roles**: Define los distintos niveles de privilegio del sistema (Administrador, Entrenador, Atleta_Gratis, Atleta_Pro).
*   **usuarios**: Centraliza la información personal y las credenciales de acceso. Su relación es de N:1 respecto a la tabla `roles`, vinculándose a través de la clave foránea `id_rol`.

### 2.2. Catálogo Estandarizado de Ejercicios
La tabla `ejercicios` constituye el núcleo del sistema de rutinas. Para asegurar la coherencia de los datos y optimizar futuras consultas o filtros en la aplicación, se ha optado por un tipado estricto:
*   Se utilizan tipos de datos `ENUM` para restringir los valores permitidos en campos categóricos clave, tales como `tipo_mecanica` ('Multiarticular', 'Monoarticular'), `lateralidad` ('Bilateral', 'Unilateral') y `material` ('Libre_Barra', 'Libre_Mancuernas', 'Polea', 'Maquina_Discos', 'Maquina_Placas', 'Peso_Corporal').

### 2.3. Sistema de Registro de Rendimiento (RM)
Para cubrir el requisito funcional de seguimiento de marcas de los usuarios de nivel "Pro", se ha diseñado la tabla `atleta_rm`.
*   Esta tabla actúa como resolución de una relación N:M (Muchos a Muchos) entre `usuarios` y `ejercicios`. 
*   Permite registrar múltiples levantamientos máximos (`peso_rm`) para un mismo atleta y ejercicio a lo largo del tiempo, almacenando la fecha exacta (`fecha_consecucion`) para posibilitar el análisis de progresión.

### 2.4. Gestión de Rutinas y Plantillas
El esquema de entrenamiento se ha modularizado en tres tablas para permitir una alta flexibilidad:
*   **plantillas**: Almacena los metadatos y objetivos generales de un bloque de entrenamiento (ej. Fuerza, Hipertrofia).
*   **rutina_ejercicio**: Es una entidad débil que detalla la composición de la plantilla. Define variables específicas de entrenamiento por cada ejercicio asignado (series, repeticiones, porcentaje de carga y tiempos de descanso).
*   **atleta_plantilla**: Gestiona la asignación temporal (fecha de inicio y fin) de una plantilla específica a un usuario concreto.

## 3. Instrucciones de Despliegue

Para desplegar la base de datos en un entorno local y evitar conflictos con las restricciones de integridad referencial (`FOREIGN KEY`), los scripts deben ejecutarse estrictamente en el siguiente orden secuencial:

1.  Ejecutar el script `1_creacion_tablas.sql` para construir el esquema. Este script incluye cláusulas `DROP TABLE IF EXISTS` en orden inverso a sus dependencias para permitir reconstrucciones seguras.
2.  Ejecutar el script `2_insercion_datos.sql` para poblar el diccionario de datos.
3.  Ejecutar el script `3_consultas.sql` para comprobar el cruce de datos entre entidades.