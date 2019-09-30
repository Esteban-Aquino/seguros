/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Esteban
 */
public class util {

    public static String nvl(String value, String alternateValue) {
        if (Objects.isNull(value)) {
            return alternateValue;
        }
        return value;
    }

    public static int nvl(int value, int alternateValue) {
        if (Objects.isNull(value)) {
            return alternateValue;
        }
        return value;
    }

    public static Date parseDateJava(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
    
    public static java.sql.Date parseDateSql(String date, String format) throws ParseException {
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return new java.sql.Date(formatter.parse(date).getTime());
        } catch (ParseException e) {
            return null;
        }
        
    }
    public static String parseStringDateJava(Date date, String format) throws ParseException {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
