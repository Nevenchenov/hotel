package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

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
        occupationMapFillServlet occupationMapFill = new occupationMapFillServlet();
        seasonGeneratingServlet season = new seasonGeneratingServlet();
        roomsFillingServlet rooms = new roomsFillingServlet();
        showPeriodServlet showUserPeriod = new showPeriodServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(start), "/setHotel");
        context.addServlet(new ServletHolder(occupationMapFill), "/occupationMapFill");
        context.addServlet(new ServletHolder(season), "/season");
        context.addServlet(new ServletHolder(rooms), "/rooms");
        context.addServlet(new ServletHolder(showUserPeriod), "/*");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();

    }
}
