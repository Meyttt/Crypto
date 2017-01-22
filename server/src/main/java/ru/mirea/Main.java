package ru.mirea;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by master on 28.11.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(BaseServlet.class,"/base");
        handler.addServlet(KeyExchangeServlet.class, "/keys");
        handler.addServlet(RegistrationServlet.class,"/registration");
        handler.addServlet(DialogServlet.class, "/dialog");
        handler.addServlet(VerificationServlet.class,"/verification");
        server.setHandler(handler);
        server.start();
    }
}
