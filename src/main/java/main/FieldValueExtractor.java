package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Y.Nevenchenov
 *
 *
 * Created on 22-Apr-17.
 */
public class FieldValueExtractor {

    public static String extractFieldValue(String fieldName, HttpServletRequest request, HttpServletResponse response){

        String field = request.getParameter(fieldName);

        response.setContentType("text/html;charset=utf-8");

        if (field == null || request.getParameter(fieldName).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return field;
    }
}
