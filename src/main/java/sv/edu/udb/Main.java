package sv.edu.udb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sv.edu.udb.domain.Curso;
import sv.edu.udb.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        // Al pedir el SessionFactory por primera vez, Hibernate lee hibernate.cfg.xml,
        // se conecta a H2 y --gracias a hbm2ddl.auto=update-- crea la tabla CURSO
        // si todavía no existe
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Session es una conexion con la base de datos
        try (Session session = sessionFactory.openSession()) {

            // Transaction agrupa las operaciones para que se confirmen  o se reviertan todas juntas.
            final Transaction transaction = session.beginTransaction();

            final Curso curso1 = Curso.builder()
                    .nombre("Antropologia Filosofica")
                    .UV(5)
                    .activo(false)
                    .build();

            final Curso curso2 = Curso.builder()
                    .nombre("Pensamiento Social Cristiano")
                    .UV(2)
                    .activo(false)
                    .build();

            // persist() es para registra las entidades que seran insertadas al hacer commit.
            session.persist(curso1);
            session.persist(curso2);

            transaction.commit();

            System.out.println("Cursos guardados con id: " + curso1.getId() + " y " + curso2.getId());
        }

        // Cierra la conexion/sesion
        sessionFactory.close();
    }
}