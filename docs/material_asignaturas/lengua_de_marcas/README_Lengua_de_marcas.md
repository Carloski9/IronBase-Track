# Documentación de Lenguaje de Marcas: IronBase Track

Este apartado describe la implementación del sistema de intercambio de datos mediante el estándar XML y su correspondiente validación estructural. Para este proyecto, se han gestionado dos entornos de datos: uno operativo vinculado a la aplicación Java y otro específico para la validación de esquemas.

## 1. Documentos XML del Proyecto
**1.1. Archivo de Exportación (datos.xml)**
Ubicado en el directorio /docs/xml, este archivo es el destino predeterminado de la funcionalidad de exportación del programa Java. Representa el estado actual del catálogo de ejercicios en el momento de la ejecución y se genera de forma dinámica para permitir la persistencia externa de los datos. Contiene la lista total de los 63 ejercicios.

**1.2. Archivo de Pruebas (ejercicios.xml)**
Ubicado en /docs/material_asignaturas/lenguaje_de_marcas, este documento se ha configurado específicamente para verificar la robustez del esquema XSD. Es el archivo sobre el cual se han realizado las capturas de pantalla de validación técnica que se adjuntan como evidencia de funcionamiento.

## 2. Validación Estructural (esquema_ejercicios.xsd)

Para asegurar que los datos exportados sean íntegros y compatibles con otros sistemas, se ha desarrollado un esquema de validación **XSD (XML Schema Definition)**. Este documento impone las siguientes restricciones técnicas:

*   **Integridad de campos:** Define la obligatoriedad de los nodos principales para evitar la pérdida de información crítica.
*   **Restricciones de dominio (Enumeraciones):** Los campos de mecánica y lateralidad están limitados a los valores específicos permitidos por la lógica de negocio (ej. Multiarticular/Monoarticular).
*   **Validación de tipos:** Garantiza que los identificadores sean exclusivamente numéricos y el resto de campos cumplan con el formato de cadena de texto esperado.

Este archivo lo hemos generado para validar y documentar el archivo de pruebas *ejercicios.xml*

## 3. Localización de Archivos y Evidencias

Todos los elementos relativos a este módulo se encuentran en este directorio:
*   Archivo de exportación operativo: `/docs/xml/datos.xml`
*   Archivo de pruebas académicas: `/docs/material_asignaturas/lenguaje_de_marcas/ejercicios.xml`
*   Esquema de validación: `/docs/material_asignaturas/lenguaje_de_marcas/esquema_ejercicios.xsd`
*   Evidencias de validación: Capturas de pantalla y logs alojados en `/docs/material_asignaturas/lenguaje_de_marcas` que demuestran la conformidad del XML frente al esquema.

## 4. Función en el Proyecto

Dentro de la arquitectura del software, este sistema de marcas permite la persistencia externa del catálogo fuera del motor de base de datos, facilitando tareas de migración, auditoría de datos y comunicación con módulos externos que requieran información del catálogo de ejercicios.