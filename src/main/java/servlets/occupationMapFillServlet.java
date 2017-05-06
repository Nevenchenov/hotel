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
 * This forms 'occupationMap' table in DB where each bed occupation statements are represented at each day of the season
 *
 *
 */
public class occupationMapFillServlet extends HttpServlet {

    private static Map<String, String> settingSeasonParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //nothing to do here / never called
        
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
