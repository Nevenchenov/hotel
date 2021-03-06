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
public class seasonGeneratingServlet extends HttpServlet {

    private static Map<String, String> settingSeasonParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        
        settingSeasonParameters.put("promptText", "Select Start and End Dates of matter season:");
        settingSeasonParameters.put("start", "First Day of Season:");
        settingSeasonParameters.put("end", "Last Day of Season:");
        settingSeasonParameters.put("countDaysAdded", "no Days as yet");
        settingSeasonParameters.put("isWrote", "are sent to DB");

        response.getWriter().println(PageGenerator.instance().getPage("makeSeason.html", settingSeasonParameters));

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
        settingSeasonParameters.put("isWrote", DBSeasonWriter.isWrote);

        // Count of Days treated to
        settingSeasonParameters.put("countDaysAdded", String.valueOf(DBSeasonWriter.count));

        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("makeSeason.html", settingSeasonParameters));
    }



}
