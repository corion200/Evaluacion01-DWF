# Evaluacion01-DWF

## Integrantes
- Carlos Eduardo Rodriguez Montoya
- Johanna Marisela Portillo Anzora
- Francisco Miguel Serrano Orellana
- Marcelo Augusto Zelaya Colocho
- Nelson Eduardo Molina Hernandez

---

## Investigación: Hibernate como implementación de JPA

### 1. ¿Qué es Hibernate?
Hibernate es un marco de trabajo (framework) de código abierto para la plataforma Java que facilita el mapeo objeto-relacional (ORM - Object-Relational Mapping). Su función principal es conectar las clases del modelo orientadas a objetos en Java con las tablas de una base de datos relacional (como PostgreSQL, MySQL, etc.), permitiendo manipular la información mediante entidades de software sin necesidad de escribir consultas SQL directas para las operaciones comunes.

### 2. Historia y evolución
- 2001: Gavin King crea Hibernate como una alternativa más ligera, rápida y flexible a los Entity Beans de EJB 2.0 (Enterprise JavaBeans), que en ese momento eran complejos y rígidos.
- 2003: Se lanza Hibernate 2.0, mejorando significativamente el rendimiento y la abstracción.
- 2006: Se publica la especificación JPA 1.0 (Java Persistence API) como parte de EJB 3.0. Esta especificación se inspiró fuertemente en las ideas y el diseño de Hibernate.
- Evolución continua: Con la transición de Java EE a Jakarta EE, Hibernate adoptó las especificaciones jakarta.persistence, manteniéndose como el estándar de la industria en el ecosistema Java enterprise para la capa de persistencia.

### 3. Relación entre Hibernate y JPA
- JPA (Jakarta/Java Persistence API): Es una especificación o interfaz estándar. Define el conjunto de reglas, anotaciones (como @Entity, @Table) y métodos que los entornos Java deben seguir para gestionar la persistencia, pero no contiene código ejecutable por sí sola.
- Hibernate: Es una implementación concreta (un provider) de esa especificación. Además de cumplir rigurosamente con los estándares de JPA, Hibernate incluye características avanzadas propias (como su motor de caché de segundo nivel y HQL extendido).

[ Esquema de relación ]
Aplicación -> JPA Interfaces (jakarta.persistence) -> Hibernate (ORM Core) -> Base de Datos Relacional

### 4. Arquitectura de Hibernate
La arquitectura interna de Hibernate gestiona el ciclo de vida de los datos desde las entidades Java hasta la BD mediante varios componentes clave:

- SessionFactory: Objeto hilo-seguro (thread-safe) e inmutable que se crea una sola vez durante el inicio de la aplicación. Su función es leer la configuración del sistema y construir las instancias de Session. Debido a su alto costo de creación, suele manejarse como un Singleton.
- Session: Interfaz principal que representa una conversación física con la base de datos (encapsula una conexión JDBC). No es hilo-seguro y está diseñada para durar solo el tiempo necesario de una unidad de trabajo (solicitud o transacción) antes de cerrarse.
- Transaction: Objeto encargado de abstraer las transacciones de la base de datos. Garantiza que un grupo de operaciones se ejecute bajo el principio ACID (todo o nada) mediante operaciones como commit() y rollback().

### 5. EntityManager y Session: similitudes y diferencias

Criterios de comparación:

- Origen:
  - EntityManager: Estándar Jakarta/Java EE.
  - Session: Propietario de Hibernate.

- Portabilidad:
  - EntityManager: Alta (se puede cambiar el proveedor ORM sin alterar el código).
  - Session: Ligada exclusivamente a Hibernate.

- Relación:
  - EntityManager: Funciona como un envoltorio (wrapper) estándar de Session.
  - Session: Es la implementación subyacente real del motor.

- Acceso nativo:
  - EntityManager: Se puede obtener la Session interna invocando em.unwrap(Session.class).
  - Session: Ofrece acceso directo a funciones nativas avanzadas de Hibernate.

### 6. Ventajas y limitaciones de Hibernate

Ventajas:
- Productividad: Reduce drásticamente la cantidad de código repetitivo (boilerplate) en llamadas JDBC.
- Generación de Esquema: Crea y actualiza automáticamente las tablas de la BD a partir de las clases de entidad Java.
- Caché de Dos Niveles: Optimiza las lecturas reduciendo las consultas repetitivas a la base de datos.
- Independencia de BD: Soporta dialectos SQL; cambiar de motor de BD solo requiere ajustar la propiedad del dialecto en la configuración.

Limitaciones:
- Curva de aprendizaje: La gestión de estados de entidades, estrategias de carga (FetchType.LAZY vs EAGER) y cachés requiere comprensión profunda.
- Overhead en consultas complejas: Para reportes masivos o procesamiento por lotes (batch processing), SQL nativo o plantillas como JDBC Template pueden resultar más eficientes.

---

## Demostración práctica: Configuración y Creación Automática de Esquema

Esta sección demuestra cómo configurar Hibernate en un proyecto Java estructurado con Maven y verificar la generación automática del esquema en la base de datos.

### 1. Dependencias (pom.xml)

<dependencies>
    <!-- Hibernate Core (JPA Implementation) -->
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.5.2.Final</version>
    </dependency>

    <!-- Conector PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.3</version>
    </dependency>
</dependencies>

### 2. Archivo de Configuración (src/main/resources/META-INF/persistence.xml)

<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">

    <persistence-unit name="EvaluacionPU">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        
        <properties>
            <!-- Conexión a la base de datos -->
            <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/evaluacion_db"/>
            <property name="jakarta.persistence.jdbc.user" value="postgres"/>
            <property name="jakarta.persistence.jdbc.password" value="root"/>

            <!-- Dialecto de Hibernate -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>

            <!-- Configuración para la creación automática del esquema -->
            <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/>
            
            <!-- Formato e impresión de SQL en consola para diagnóstico -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>

### 3. Definición de la Entidad Java (Estudiante.java)

package com.evaluacion.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo_institucional", unique = true, nullable = false)
    private String correo;

    public Estudiante() {}

    public Estudiante(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}

### 4. Clase Principal de Ejecución (Main.java)

package com.evaluacion;

import com.evaluacion.modelo.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EvaluacionPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Estudiante e1 = new Estudiante("Nelson Eduardo Molina Hernandez", "nel.molina@universidad.edu");
            Estudiante e2 = new Estudiante("Carlos Eduardo Rodriguez Montoya", "car.rodriguez@universidad.edu");

            em.persist(e1);
            em.persist(e2);

            em.getTransaction().commit();
            System.out.println("\nBase de datos inicializada y registros guardados correctamente.\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}

---

## Resultado Esperado en Consola

Hibernate: 
    drop table if exists estudiantes cascade
Hibernate: 
    create table estudiantes (
        id bigint generated by default as identity,
        correo_institucional varchar(255) not null,
        nombre_completo varchar(100) not null,
        primary key (id)
    )
Hibernate: 
    alter table if exists estudiantes 
       add constraint UK_correo_institucional unique (correo_institucional)
Hibernate: 
    insert into estudiantes (correo_institucional, nombre_completo) values (?, ?)
