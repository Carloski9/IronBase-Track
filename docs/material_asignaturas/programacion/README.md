# Documentación de Programación: IronBase Track

Este documento describe la arquitectura, funcionalidades y requerimientos técnicos del proyecto **IronBase Track**, una aplicación de escritorio desarrollada bajo el lenguaje Java.

## 1. ¿Qué hace la aplicación?

IronBase Track es un software de gestión deportiva diseñado para centralizar y administrar un catálogo de ejercicios de fuerza. La aplicación resuelve la necesidad de los entrenadores de mantener una base de datos estandarizada de movimientos (clasificados por grupo muscular, mecánica y material) y proporciona a los atletas una herramienta para consultar dichos ejercicios y llevar un registro histórico de sus Marcas Personales (RM - Repetición Máxima).

## 2. ¿Qué funcionalidades tiene?

El sistema cuenta con un control de acceso basado en roles (Administrador/Entrenador vs. Atleta) y ofrece las siguientes funcionalidades:

*   **Autenticación y Sesión:** Sistema de login que adapta la interfaz visual dinámicamente según los privilegios del usuario conectado.
*   **CRUD de Ejercicios:** Permite a los usuarios con permisos administrativos añadir nuevos ejercicios al catálogo (validando que no existan campos vacíos) y eliminarlos del sistema de forma segura.
*   **Buscador In-Memory:** Implementación de un filtro en tiempo real utilizando la interfaz `FilteredList` de JavaFX, que permite buscar ejercicios instantáneamente sin saturar la base de datos.
*   **Registro de Rendimiento:** Capacidad para que un atleta asigne un peso máximo (RM) a un ejercicio seleccionado, almacenando la fecha exacta del hito.
*   **Interoperabilidad XML:** Funcionalidad para exportar el catálogo completo de ejercicios a un archivo físico `datos.xml` compatible con esquemas XSD.

## 3. ¿Qué parte usa la base de datos?

Toda la persistencia de información del programa recae sobre una base de datos relacional MariaDB. La aplicación se comunica con ella a través de la tecnología **JDBC** y el uso del patrón de diseño arquitectónico **DAO (Data Access Object)**.

El uso de la base de datos se manifiesta en:
1.  **Autenticación (`UsuarioDAO`):** Consultas de lectura para verificar credenciales (usuario y contraseña) y extraer el `id_rol` para configurar la interfaz.
2.  **Catálogo Visual (`EjercicioDAO`):** Ejecución de sentencias `SELECT` para poblar las colecciones `ObservableList` que alimentan la interfaz gráfica (`TableView`) al arrancar el programa.
3.  **Transacciones de Inserción y Borrado:** Uso de sentencias preparadas (`PreparedStatement`) para ejecutar comandos `INSERT` (crear ejercicios o registrar RMs) y `DELETE` (borrar ejercicios), protegiendo al sistema de vulnerabilidades como la Inyección SQL.

## 4. ¿Cómo se ejecuta?

Para desplegar y ejecutar el programa en un entorno local:

1.  Asegurarse de tener instalado **Java Runtime Environment (JRE)** versión 17 o superior.
2.  Verificar que el motor de base de datos **MariaDB/MySQL** está ejecutándose en el puerto `3306` (localhost) con el esquema importado.
3.  Ejecutar el programa haciendo doble clic sobre el archivo ejecutable **`IronBaseTrack.jar`**, o bien, abriendo una terminal en la misma ruta y ejecutando el comando:
    `java -jar IronBaseTrack.jar`
