package sv.edu.udb.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    // El SessionFactory es costoso de crear, por eso se arma una sola vez
    // y se reutiliza durante toda la vida de la aplicación
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Configuration() lee el archivo hibernate.cfg.xml y construye el SessionFactory,
        // que es la fábrica de Session
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

}