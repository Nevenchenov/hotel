package servlets;

import main.DBOccupationMapFill;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */
public class occupationMapFillServlet extends HttpServlet {

    private static Map<String, String> settingSeasonParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //nothing to do here / never called
        
//        settingSeasonParameters.put("promptText", "Select Start and End Dates of matter season:");
//        settingSeasonParameters.put("start", "First Day of Season:");
//        settingSeasonParameters.put("end", "Last Day of Season:");
//        settingSeasonParameters.put("countDaysAdded", "no Days as yet");
//        settingSeasonParameters.put("isWrote", "are sent to DB");
//
//        response.getWriter().println(PageGenerator.instance().getPage("makeSeason.html", settingSeasonParameters));
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {



        // write room to DB and get numbers for report
        int[] reportNumbers = DBOccupationMapFill.fillDB();

        // add report to response
        settingSeasonParameters.put("daysWritten", String.valueOf(reportNumbers[0]));
        settingSeasonParameters.put("bedsWritten", String.valueOf(reportNumbers[1]));
        settingSeasonParameters.put("rowsWritten", String.valueOf(reportNumbers[2]));


        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("occupationFillingReport.html", settingSeasonParameters));
    }



}
