package sv.edu.udb;

import org.h2.tools.Server;

public class H2Console {

    public static void main(String[] args) throws Exception {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        System.out.println("Consola H2 disponible en: http://localhost:8082");
        System.in.read();
    }
}