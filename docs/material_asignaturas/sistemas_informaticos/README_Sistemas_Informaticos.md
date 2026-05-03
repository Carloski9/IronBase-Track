# Informe Técnico: Entorno de Ejecución - IronBase Track

## 1. Tipo de sistema donde se ejecuta
**Decisión:** PC de usuario (Cliente Pesado) con Base de Datos Local.
**Justificación:** IronBase Track se ha desarrollado como una herramienta de gestión nativa mediante la plataforma JavaFX. La ejecución se realiza en el entorno del cliente (PC de usuario) requiriendo la Máquina Virtual de Java (JVM). La base de datos se aloja mediante un servidor MariaDB local (localhost), garantizando una latencia mínima y la privacidad de la información, aunque el sistema está preparado para una transición a arquitectura Cliente-Servidor remota.

## 2. Requisitos de hardware
El software ha sido optimizado para funcionar en equipos de gama de entrada, delegando la carga computacional principalmente al sistema gestor de bases de datos.
* **CPU:**
    * *Mínima:* Procesador dual-core (Ej. Intel Core i3 o AMD Ryzen 3).
    * *Recomendada:* Quad-core a 2.5 GHz o superior.
* **Memoria RAM:**
    * *Mínima:* 4 GB (Suficiente para el SO, la JVM y MariaDB en segundo plano).
    * *Recomendada:* 8 GB para una fluidez óptima.
* **Almacenamiento:**
    * 500 MB libres (incluye el motor de MariaDB, la JVM y el ejecutable `.jar`).
* **Periféricos:** Ratón y teclado estándar, monitor con resolución mínima de 1280x720.

## 3. Sistema Operativo recomendado
**Sistema Principal:** Windows 10 / Windows 11 (64-bits).
*Justificación:* Aunque Java es multiplataforma, Windows es el estándar actual para usuarios finales y la configuración de MariaDB/DBeaver es extremadamente accesible para entornos locales. La interfaz gráfica en JavaFX garantiza compatibilidad nativa sin necesidad de instalar dependencias visuales adicionales.
**Entorno de Ejecución:** Java Runtime Environment (JRE) versión 17 o superior.
**Gestor de Base de Datos:** MariaDB Server 10.6+ o MySQL 8.0+.

## 4. Instalación y despliegue del entorno
Para desplegar la aplicación desde cero, se deben seguir estos pasos de forma secuencial:
1.  **Dependencias Base:** Configurar el Java Development Kit (JDK 21) y las variables de entorno del sistema.
2.  **Motor de Datos:** Instalar y securizar MariaDB Server, definiendo las credenciales de acceso para el usuario `root`. En este caso, el usuario root no tiene password para facilitar la entrada en la base de datos local.
3.  **Configuración de BD:** Mediante una herramienta de administración SQL, crear el esquema e importar los scripts ubicados en `/docs/material_asignaturas/base_de_datos/` siguiendo el orden numérico indicado en el README.md.
4.  **Ejecución:** Hacer doble clic en el archivo `IronBaseTrack.jar` o ejecutar en terminal: `java -jar IronBaseTrack.jar`.

## 5. Usuarios, permisos y estructura
* **Usuarios de Base de Datos:**
    * `root`: Acceso total para mantenimiento y creación de copias de seguridad.
* **Usuarios de Aplicación (Lógica de Negocio):**
    * *Administrador/Entrenador:* Puede crear, editar y borrar ejercicios y plantillas maestras.
    * *Atleta:* Permiso exclusivo de lectura del catálogo y registro de sus propios RM.
* **Estructura de Carpetas:** Los archivos `.xml` exportados y posibles logs de la aplicación se guardan en el directorio `/docs/xml/` dentro de la carpeta de usuario.

## 6. Mantenimiento básico
* **Actualizaciones:** Revisar semestralmente parches de seguridad de Java (JVM) y actualizaciones menores de MariaDB.
* **Copias de Seguridad (Backups):** Se recomienda exportar un archivo `.sql` (dump) de la base de datos de forma semanal para evitar pérdida de registros de usuarios en caso de fallo del disco duro.
* **Troubleshooting:** Si la aplicación no arranca, verificar mediante la terminal que el servicio de MariaDB está activo (`services.msc` en Windows) y que el puerto 3306 no está bloqueado.

## 7. Esquema del Sistema
La arquitectura lógica de IronBase Track se basa en un modelo de capas que interactúan de la siguiente forma:

* **Capa de Presentación (Usuario)**: Interfaz gráfica desarrollada en JavaFX que captura eventos y muestra información.

* **Capa de Aplicación (Lógica de Negocio)**: Ejecutada sobre la JVM, procesa las reglas de entrenamiento, validación de registros y gestión de roles.

* **Capa de Conectividad (JDBC)**: Puente de comunicación que traduce las peticiones de la aplicación a sentencias SQL.

* **Capa de Persistencia (MariaDB/XML)**: Servidor de base de datos para datos relacionales y sistema de archivos local para la exportación de documentos XML.

## 8. Evidencias de Ejecución
En este apartado se incluyen las capturas de pantalla que certifican la correcta ejecución del sistema en el entorno de pruebas:

Captura 1: Pantalla de autenticación y control de acceso.

Captura 2: Interfaz principal con carga de datos desde el servidor SQL: Panel de administración de ejercicios (Rol Entrenador)

Captura 3: Ejecución de filtros de búsqueda avanzados.

Captura 4: Registro de RM y exportación de datos a XML.

Captura 5: Consola de administración de base de datos mostrando las tablas creadas. Son siete imágenes.