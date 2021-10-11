package comp5216.sydney.edu.au.finalproject.util;

import java.util.UUID;

public class IdUtil {
    public static String getUUID(String type) {
        String uuid = type + UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
