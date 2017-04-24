package servlets;

import main.DBRoomWriter;
import main.FieldValueExtractor;
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
 *         Thanks to v.chibrikov for template have been developed
 *
 *
 */
public class roomsFillingServlet extends HttpServlet {

    private static Map<String, String> roomParameters = new HashMap<>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        
        roomParameters.put("roomNumber", "?");
        roomParameters.put("bedsCount", "?");
        roomParameters.put("rank", "?");
        roomParameters.put("floor", "?");
        roomParameters.put("minPrice", "?");
        roomParameters.put("maxPrice", "?");
        roomParameters.put("isWrote", DBRoomWriter.isWrote);
        response.getWriter().println(PageGenerator.instance().getPage("fillRooms.html", roomParameters));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        // roomNumber
        roomParameters.put("roomNumber", FieldValueExtractor.extractFieldValue("roomNumber", request, response));

        // bedsCount
        roomParameters.put("bedsCount", FieldValueExtractor.extractFieldValue("bedsCount", request, response));

        // class
        roomParameters.put("rank", FieldValueExtractor.extractFieldValue("rank", request, response));

        // minPrice
        roomParameters.put("floor", FieldValueExtractor.extractFieldValue("floor", request, response));

        // minPrice
        roomParameters.put("minPrice", FieldValueExtractor.extractFieldValue("minPrice", request, response));

        // maxPrice
        roomParameters.put("maxPrice", FieldValueExtractor.extractFieldValue("maxPrice", request, response));

        // write room to DB
        DBRoomWriter.sendToDB(roomParameters);
        // add report to response
        roomParameters.put("isWrote", DBRoomWriter.isWrote);

        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("fillRooms.html", roomParameters));
    }



}
