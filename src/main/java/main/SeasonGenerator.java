package main;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Rain on 21-Apr-17.
 */
public class SeasonGenerator {
    //start&finish season:
    private static String startDate;
    private static String endDate;
    private static ArrayList<LocalDate> season = new ArrayList<>();

//    //constructor:
//    public SeasonGenerator(Date startDate, Date endDate){
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }

    //generate working period for hotel
    public static ArrayList<LocalDate> generateSeason(String startDate, String endDate){
        LocalDate firstDay = LocalDate.parse(startDate);
                season.add(firstDay);
        int count = 1;
        while(true){
            LocalDate currentDay = firstDay.plusDays(count);
            season.add(currentDay);
            if(currentDay.equals(LocalDate.parse(endDate))) break;
        else count++;
        }

        return season;
    }
}
