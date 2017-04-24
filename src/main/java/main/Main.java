package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.roomsFillingServlet;
import servlets.seasonGeneratingServlet;
import servlets.startPageServlet;

/**
 * @author Y.Nevenchenov
 *
 *         Thanks to v.chibrikov for template and direction to dig pointed ;)
 *
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        startPageServlet start = new startPageServlet();
        seasonGeneratingServlet season = new seasonGeneratingServlet();
        roomsFillingServlet rooms = new roomsFillingServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(start), "/*");
        context.addServlet(new ServletHolder(season), "/season");
        context.addServlet(new ServletHolder(rooms), "/rooms");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();

    }
}
