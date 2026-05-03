# Memoria Técnica: Ampliación de Programación
**Proyecto:** IronBase Track

Este documento detalla las técnicas avanzadas, patrones de diseño y optimizaciones implementadas en el proyecto IronBase Track, superando los requisitos básicos de una aplicación de gestión estándar.

## 1. Arquitectura y Patrones de Diseño
El proyecto se ha estructurado siguiendo principios sólidos de ingeniería de software para garantizar su escalabilidad, modularidad y fácil mantenimiento:
*   **Patrón MVC (Model-View-Controller):** Se ha separado estrictamente la lógica de control (`EjercicioController`), la representación visual interactiva (archivos `FXML`) y la estructura de datos pura (`Ejercicio`, `Usuario`).
*   **Patrón DAO (Data Access Object):** Toda la interacción directa con el gestor relacional MariaDB está aislada en clases específicas (`EjercicioDAO`, `UsuarioDAO`). Esto desacopla las sentencias SQL de la interfaz gráfica de usuario y estandariza el acceso a datos.

## 2. Optimización de Rendimiento (Búsqueda In-Memory)
Para evitar la sobrecarga del servidor de base de datos con peticiones constantes, se ha implementado un sistema de **búsqueda dinámica en memoria** (In-Memory Filtering):
*   El catálogo se carga en memoria una única vez al arrancar la vista en una `ObservableList`.
*   Esta lista se envuelve mediante las clases `FilteredList` y `SortedList` de JavaFX.
*   Se ha acoplado un *Listener* (escuchador de eventos) a la propiedad de texto del campo buscador, permitiendo filtrar el catálogo al instante por múltiples criterios (nombre, grupo muscular, subgrupo muscular o material) con cada pulsación de tecla, ofreciendo latencia cero al usuario.

## 3. Dinamismo de Interfaz y Gestión de Privilegios (UI/UX)
La aplicación no es estática; muta su interfaz en tiempo de ejecución dependiendo del nivel de privilegios del usuario autenticado (Control de Roles):
*   **Ajuste de Nodos Gráficos:** Mediante los métodos `setVisible(false)` y `setManaged(false)`, el sistema oculta por completo los paneles administrativos (creación/borrado) a los usuarios con rol de "Atleta", cediendo ese espacio visual al panel de registro de RMs.
*   **Prevención de Errores:** Se han implementado ventanas modales (`Alert` tipo *Warning* y *Confirmation*) para interceptar acciones críticas (campos incompletos, borrado de registros de la base de datos o cierres de sesión accidentales), mejorando drásticamente la robustez del programa frente a errores humanos.

## 4. Empaquetado Monolítico y Despliegue Avanzado
Para la distribución y despliegue del software, se ha configurado **Apache Maven** más allá de la simple gestión de dependencias:
*   **Fat JAR:** Uso del `maven-shade-plugin` para compilar la aplicación, las librerías de JavaFX y el conector JDBC en un único archivo ejecutable `.jar`.
*   **Bypass de Modularidad:** Implementación de una clase `Launcher` independiente de la clase principal `Application` para sortear las restricciones del sistema de módulos introducido en Java 11+, garantizando una ejecución limpia y portable en cualquier máquina anfitriona que disponga de JRE 17+.