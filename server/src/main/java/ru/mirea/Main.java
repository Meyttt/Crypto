package ru.mirea;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.mirea.common.VerificationData;

/**
 * Created by master on 28.11.2016.
 */
public class Main {

    public static void main1(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(BaseServlet.class,"/base");
        handler.addServlet(KeyExchangeServlet.class, "/keys");
        handler.addServlet(DialogServlet.class, "/dialog");
        handler.addServlet(BaseExchange.class,"/bases");
        server.setHandler(handler);
        server.start();
    }

    public static void main(String[] args) {
        String testString="Test";
        System.out.println(testString.toCharArray());
        System.out.println(testString.getBytes());
    }
}
