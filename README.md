# Universidad Don Bosco
**Facultad de Ingeniería — Escuela de Computación**  
**Técnico en Ingeniería en Computación**  
**Materia:** Desarrollo de Aplicaciones con Web Frameworks  
**Ciclo:** II - 2026  
**Docente:** Ing. Yesenia Escobar  

---

## 📌 Proyecto de Investigación Aplicada — Evaluacion01-DWF

### 👥 Integrantes (Equipo F)
- **Carlos Eduardo Rodriguez Montoya**
- **Johanna Marisela Portillo Anzora**
- **Francisco Miguel Serrano Orellana**
- **Marcelo Augusto Zelaya Colocho**
- **Nelson Eduardo Molina Hernandez**

---

## 🎯 Tema Asignado: Tema 2
### **Hibernate como implementación de JPA**

---

## 💻 Descripción de la Aplicación y Demostración Práctica

Este repositorio contiene la demostración práctica desarrollada para el **Tema 2**, orientada a ejemplificar el uso de **Hibernate** como proveedor e implementación de la especificación **JPA (Jakarta Persistence)**.

La aplicación consiste en un proyecto Java estructurado con Maven que demuestra la **creación automática del esquema de base de datos** en un motor relacional (PostgreSQL) a partir de la definición de entidades con anotaciones JPA, configurando el contexto de persistencia mediante un archivo `persistence.xml`.

### Funcionalidades y Demostración:
1. **Configuración de la Unidad de Persistencia (Persistence Unit):**
   - Mapeo del proveedor de persistencia (`org.hibernate.jpa.HibernatePersistenceProvider`).
   - Configuración de la conexión JDBC y dialecto SQL correspondiente.
2. **Generación Automática de Tablas (DDL):**
   - Utilización de la propiedad `jakarta.persistence.schema-generation.database.action` (`drop-and-create` / `update`) para generar la estructura de tablas a partir de entidades Java (e.g. `Estudiante`).
3. **Persistencia e Interacción:**
   - Creación de un `EntityManagerFactory` y `EntityManager` para inicializar el contexto de persistencia.
   - Ejecución de transacciones y verificación de inserciones en la base de datos con visualización del código SQL generado en consola (`hibernate.show_sql`).
