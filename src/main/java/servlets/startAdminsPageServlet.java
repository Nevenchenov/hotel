package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */
public class startAdminsPageServlet extends HttpServlet {

    private static Map<String, String> userSeasonParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String currentYear = String.valueOf(LocalDate.now().getYear());

        userSeasonParameters.put("rooms", "set rooms");
        userSeasonParameters.put("beds", "set beds");
        userSeasonParameters.put("season", "generate season");
        userSeasonParameters.put("occupationMapFill", "generate occupation map");
        userSeasonParameters.put("generateRoomsSummery", "generate rooms summery");



        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println(PageGenerator.instance().getPage("setHotel.html", userSeasonParameters));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
//what that need to do?
    }



}
