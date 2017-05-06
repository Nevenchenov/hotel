package servlets;

import main.DBDayGetter;
import main.FieldValueExtractor;
import main.TableHeaderMaker;
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
 * Created on 04-May-17.
 */
public class showPeriodServlet extends HttpServlet {

    private static Map<String, String> userSeasonParameters = new HashMap<>();

    String hotelName = "Пансионат \"Ирина\". ";
    String currentYear = String.valueOf(LocalDate.now().getYear());


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        userSeasonParameters.put("outputTable", "");
        userSeasonParameters.put("promptText", "Выберите период для просмотра:");
        userSeasonParameters.put("start", "Начальная дата:");
        userSeasonParameters.put("end", "Конечная дата:");
        userSeasonParameters.put("hotelName", hotelName);
        userSeasonParameters.put("currentYear", currentYear);
        userSeasonParameters.put("title", "Бронирование мест. " + hotelName + currentYear + "г.");



        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println(PageGenerator.instance().getPage("index.html", userSeasonParameters));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        userSeasonParameters.put("promptText", "Желаете выбрать другой период? Пожалуйста:");

        TableHeaderMaker.findInDB();

        // startDate
        String startDate = FieldValueExtractor.extractFieldValue("startDate", request, response);
        LocalDate firstDay = LocalDate.parse(startDate);

       // endDate
        String endDate = FieldValueExtractor.extractFieldValue("endDate", request, response);
        LocalDate lastDay = LocalDate.parse(endDate);



        // extract dayNumber of start and end from DB
        int startDay = DBDayGetter.getDay(firstDay);
        int endDay = DBDayGetter.getDay(lastDay);

        // extract days between start and end from DB occupationMap table
        for(int i = startDay; i <= endDay; i++){

        }



        // forming output String table
        String table = "";

        // table to output
        userSeasonParameters.put("outputTable", table);

        //refresh web-page
        response.getWriter().println(PageGenerator.instance().getPage("index.html", userSeasonParameters));
    }



}
