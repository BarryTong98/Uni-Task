package comp5216.sydney.edu.au.finalproject.utils;

import java.util.UUID;

public class IdUtil {
    /**
     * U user
     * a Assignment
     * T Task
     *
     * G Group
     */
    public static String getUUID(String type) {
        String uuid = type + UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
