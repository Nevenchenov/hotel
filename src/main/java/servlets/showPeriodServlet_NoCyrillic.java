package servlets;

import main.DBSeasonWriter;
import main.FieldValueExtractor;
import main.SeasonGenerator;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */
public class showPeriodServlet_NoCyrillic extends HttpServlet {

    private static Map<String, String> userSeasonParameters = new HashMap<>();

    String hotelName = "\"Irena\". " + "Hotel. ";
    String currentYear = String.valueOf(LocalDate.now().getYear());


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        
        userSeasonParameters.put("promptText", "Choose viewed period:");
        userSeasonParameters.put("start", "Start Date:");
        userSeasonParameters.put("end", "Finish Date:");
        userSeasonParameters.put("hotelName", hotelName);
        userSeasonParameters.put("currentYear", currentYear);
        userSeasonParameters.put("title", "Apartments Reserving. " + hotelName + currentYear + "г." );

        response.getWriter().println(PageGenerator.instance().getPage("index.html", userSeasonParameters));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        // startDate
        String startDate = FieldValueExtractor.extractFieldValue("startDate", request, response);

       // endDate
        String endDate = FieldValueExtractor.extractFieldValue("endDate", request, response);


        // generate calendar to hotel work within:
        ArrayList<LocalDate> calendar = SeasonGenerator.generateSeason(startDate, endDate);

        // write room to DB
        DBSeasonWriter.sendToDB(calendar);

        // add report to response
        userSeasonParameters.put("isWrote", DBSeasonWriter.isWrote);

        // Count of Days treated to
        userSeasonParameters.put("countDaysAdded", String.valueOf(DBSeasonWriter.count));

        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("index.html", userSeasonParameters));
    }

    public void destroy() {

        super.destroy();

    }

}
