package org.project.expendituremanagement.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    public static Date convertDateToDateWithoutTime(Date dateWithTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime;

        try {
            dateWithoutTime = sdf.parse(sdf.format(dateWithTime));
        }
        catch (ParseException exception) {
            return null;
        }
        return dateWithoutTime;
    }
    public static Date convertDateInStringToDate(String dateInString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;

        try{
            date=sdf.parse(dateInString);
        }
        catch (ParseException exception){
            return null;
        }
        return date;

    }
}
