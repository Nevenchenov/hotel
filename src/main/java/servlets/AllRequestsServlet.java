package servlets;

import main.DBRoomWriter;
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
public class AllRequestsServlet extends HttpServlet {

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
        response.getWriter().println(PageGenerator.instance().getPage("page.html", roomParameters));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        // roomNumber
        roomParameters.put("roomNumber", fieldValueExtractor("roomNumber", request, response));

        // bedsCount
        roomParameters.put("bedsCount", fieldValueExtractor("bedsCount", request, response));

        // class
        roomParameters.put("rank", fieldValueExtractor("rank", request, response));

        // minPrice
        roomParameters.put("floor", fieldValueExtractor("floor", request, response));

        // minPrice
        roomParameters.put("minPrice", fieldValueExtractor("minPrice", request, response));

        // maxPrice
        roomParameters.put("maxPrice", fieldValueExtractor("maxPrice", request, response));

        // write room to DB
        DBRoomWriter.sendToDB(roomParameters);
        // add report to response
        roomParameters.put("isWrote", DBRoomWriter.isWrote);

        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("page.html", roomParameters));
    }

    private static String fieldValueExtractor(String fieldName, HttpServletRequest request, HttpServletResponse response){
        String field = request.getParameter(fieldName);

        response.setContentType("text/html;charset=utf-8");

        if (field == null || field.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
       return field;
    }

}
