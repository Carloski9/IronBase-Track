# IronBase-Track
> Sistema integral de gestión de entrenamientos, ejercicios y prescripción de cargas basado en RMs.
**IronBase Track** es una aplicación multiplataforma diseñada para optimizar el trabajo de los entrenadores personales. Permite la gestión de una base de datos de ejercicios basada en biomecánica, la creación de plantillas de entrenamiento por objetivos, y el cálculo automatizado de cargas (en kg) en función del 1RM actual de cada atleta y el RIR deseado.

---

## 🎯 ¿Qué problema resuelve?

Actualmente, muchos entrenadores personales gestionan a sus atletas usando hojas de cálculo complejas o notas, calculando manualmente los porcentajes de RM para cada sesión. Esto es propenso a errores, no escala bien y consume mucho tiempo. 

**IronBase Track automatiza este proceso:**
- **Para el Entrenador (Gestión):** Ofrece un panel de control (CRUD) de atletas, un catálogo filtrable de ejercicios y un creador de plantillas.
- **Para el Atleta (Vista):** Proporciona un acceso rápido y responsive a su rutina del día con los pesos **exactos** que debe levantar, adaptados a su progresión actual.

---

## 🛠️ Tecnologías Utilizadas (Módulos de 1º DAM)

Este proyecto es el resultado del **Proyecto Intermodular de 1º de DAM**, integrando los conocimientos de los siguientes módulos:

* **[0484] Bases de Datos:** MySQL / MariaDB. Diseño de un modelo relacional que refleja la lógica real del entrenamiento: los ejercicios se categorizan por biomecánica (bilateral, polea, multiarticular), mientras que los objetivos (hipertrofia, pérdida de grasa) se asocian a las *Plantillas* de entrenamiento. Incluye gestión de roles de usuario.
* **[0485] Programación:** Desarrollo de la lógica de negocio en **Java**, implementando algoritmos de cálculo de progresión de cargas.
* **[0487] Entornos de Desarrollo:** Control de versiones con **Git/GitHub**, diseño UML (Casos de Uso y Diagrama de Clases) y documentación técnica con Javadoc.
* **[0373] Lenguajes de Marcas:** Generación de archivos **JSON** para intercambio de rutinas e interfaz **HTML/CSS** responsive para la vista del atleta.
* **[0483] Sistemas Informáticos:** Scripts de automatización para backups de la base de datos y documentación de despliegue en servidor.

---

## 🏗️ Arquitectura y Ampliación (MPO)

Para cumplir con los estándares de calidad del módulo **MPO (Ampliación de Programación)**, este proyecto aplica un diseño orientado a objetos riguroso:

* **Patrón Arquitectónico:** Implementación estricta de **MVC (Modelo-Vista-Controlador)** separado por paquetes.
* **Mejora Estructural MPO:** Se ha extraído la lógica de cálculo matemático (porcentajes de RM, RIR, volumen) de los controladores hacia una **Capa de Servicios** dedicada (`CalculadoraCargasService`). Esto garantiza la escalabilidad, facilita el testing y cumple con el principio de responsabilidad única.

---

## 🧑‍💻 Perfil Profesional (IPE)

### Sobre mí
Mi nombre es **Carlos Molina**. Soy estudiante de 1º de Desarrollo de Aplicaciones Multiplataforma (DAM) y Entrenador Personal. Actualmente trabajo en una consultora TIC y ofreciendo servicios de entrenamiento personal. Mi perfil profesional en el ambito de la programación se orienta hacia el desarrollo Backend (Java/Spring) y la creación de soluciones de software aplicadas al sector de la salud, el fitness y el rendimiento humano. 

**Motivación:** Me apasiona unir la lógica de la programación con la biomecánica y la fisiología del entrenamiento para crear herramientas que tengan un impacto real en el rendimiento de los atletas y en la productividad de los entrenadores.

*(Nota: La investigación completa de empresas, perfiles referentes y reflexiones se encuentra en la carpeta `/docs/empleabilidad` del repositorio).*
