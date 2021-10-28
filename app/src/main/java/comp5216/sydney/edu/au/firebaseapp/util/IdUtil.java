package comp5216.sydney.edu.au.firebaseapp.util;

import java.util.UUID;

/**
 * User to generate UUID
 */
public class IdUtil {
    public static String getUUID(String type) {
        String uuid = type + UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
