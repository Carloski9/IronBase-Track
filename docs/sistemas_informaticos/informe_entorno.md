# Informe Técnico: Entorno de Ejecución - IronBase Track

## 1. Tipo de sistema donde se ejecuta
**Decisión:** PC de usuario (Cliente Pesado) con Base de Datos Local.
**Justificación:** IronBase Track está desarrollada como una aplicación de escritorio mediante JavaFX. Al ser una herramienta de gestión personal y administrativa, la ejecución idónea es un entorno de escritorio nativo (Windows/Linux/macOS) que tenga instalada la Máquina Virtual de Java (JVM). La base de datos MariaDB se aloja en local (localhost) para garantizar el rendimiento y la privacidad de los datos en esta fase inicial, aunque la arquitectura permite conectarla a un servidor remoto en el futuro.

## 2. Requisitos de hardware
La aplicación es muy ligera, delegando la mayor carga al motor de la base de datos relacional.
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
**Justificación:** Aunque Java es multiplataforma, Windows es el estándar actual para usuarios finales y la configuración de MariaDB/DBeaver es extremadamente accesible para entornos locales. La interfaz gráfica en JavaFX garantiza compatibilidad nativa sin necesidad de instalar dependencias visuales adicionales.

## 4. Instalación del entorno
Para desplegar la aplicación desde cero, se deben seguir estos pasos de forma secuencial:
1.  **Dependencias Base:** Instalar Java Development Kit (JDK 17 o superior).
2.  **Motor de Datos:** Instalar MariaDB Server. Durante la instalación, configurar la contraseña de `root`.
3.  **Configuración de BD:** Abrir un cliente SQL (ej. DBeaver), crear el esquema `ironbase` y ejecutar el archivo `schema.sql` (ubicado en `/docs/sql`) para generar las tablas y datos semilla.
4.  **Ejecución:** Hacer doble clic en el archivo `IronBaseTrack.jar` o ejecutar en terminal: `java -jar IronBaseTrack.jar`.

## 5. Usuarios, permisos y estructura
* **Usuarios de Base de Datos:**
    * `root`: Acceso total para mantenimiento y creación de copias de seguridad.
    * *(Recomendado)* `ironbase_app`: Usuario con permisos limitados solo para CRUD en las tablas necesarias, mejorando la seguridad.
* **Usuarios de Aplicación (Lógica de Negocio):**
    * *Administrador:* Puede crear, editar y borrar ejercicios y plantillas maestras.
    * *Atleta:* Permiso exclusivo de lectura del catálogo y registro de sus propios RM.
* **Estructura de Carpetas:** Los archivos `.xml` exportados y posibles logs de la aplicación se guardan en el directorio `/docs` dentro de la carpeta de usuario.

## 6. Mantenimiento básico
* **Actualizaciones:** Revisar semestralmente parches de seguridad de Java (JVM) y actualizaciones menores de MariaDB.
* **Copias de Seguridad (Backups):** Se recomienda exportar un archivo `.sql` (dump) de la base de datos de forma semanal para evitar pérdida de registros de usuarios en caso de fallo del disco duro.
* **Troubleshooting:** Si la aplicación no arranca, verificar mediante la terminal que el servicio de MariaDB está activo (`services.msc` en Windows) y que el puerto 3306 no está bloqueado.

## 7. Evidencias de Ejecución
*(Nota para la entrega: Añadir aquí 2 o 3 capturas de pantalla de la aplicación corriendo en tu ordenador, una con la base de datos conectada mostrando datos en la tabla).*
