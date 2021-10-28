package comp5216.sydney.edu.au.firebaseapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User to generate date in string type
 */
public class DateUtil {
    public static String dateToString(Date date, Boolean flag) {
        DateFormat dateFormat;
        if (flag) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        }
        return dateFormat.format(date);
    }
}
