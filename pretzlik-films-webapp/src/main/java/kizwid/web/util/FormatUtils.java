package kizwid.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple date formatter
 * Date: 08/06/2012
 */
public class FormatUtils {

    public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat FORMAT_SQL_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String yyyymmdd(Date date) {
        return YYYYMMDD.format(date);
    }
    public static int parseDate(String yyyymmdd){
        if( yyyymmdd == null){
            yyyymmdd = yyyymmdd(new Date());
        }
        return  Integer.parseInt(yyyymmdd);
    }


    public static String formatSqlDateTime(Date dt){
        return (dt==null?"1900-01-01 00:00:00": FORMAT_SQL_TIMESTAMP.format(dt));
    }



}
