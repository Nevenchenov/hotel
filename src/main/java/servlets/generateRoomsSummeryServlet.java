package servlets;

import main.DBRoomsSummeryWriter;
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
 * This forms 'roomsSummery' table in DB that will help to quicklier build Table of rooms-beds for header
 *
 * Created on 07-May-17.
 */
public class generateRoomsSummeryServlet extends HttpServlet {

    private static Map<String, String> generateRoomsSummeryParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //nothing to do here / never called
        
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String report;
        report = DBRoomsSummeryWriter.exploreDB();

        // Finding  room class categories

        // Finding number of rooms in each class

        // Finding number or each beds-capacity room category

        // Finding number of rooms each beds-capacity category

        // Finding number of beds in each class category

        // add report to response
//        generateRoomsSummeryParameters.put("daysWritten", String.valueOf(reportNumbers[0]));
//        generateRoomsSummeryParameters.put("bedsWritten", String.valueOf(reportNumbers[1]));
//        generateRoomsSummeryParameters.put("rowsWritten", String.valueOf(reportNumbers[2]));

        generateRoomsSummeryParameters.put("report", report);


        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("occupationFillingReport.html", generateRoomsSummeryParameters));
    }



}
