package comp5216.sydney.edu.au.myapplication.util;

import java.util.UUID;

public class IdUtil {
    /**
     * U user
     * a Assignment
     * T Task
     */
    public static String getUUID(String type) {
        String uuid = type + UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
