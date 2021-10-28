package comp5216.sydney.edu.au.firebaseapp.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


/**
 * Use to compress image
 * The code for this section comes from the open source community
 *
 * @author futhureWithYou（https://ublog.csdn.net/furtureWithYou/article/details/81565595）
 * update at 2018.08.10
 */
public class Compressor {
    public static String getRealFilePath(Activity activity, final Uri uri) {
        if (uri == null) {
            return null;
        }
        final String scheme = uri.getScheme();
        String realPath = null;
        if (scheme == null) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = activity.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}
                            , null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        realPath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return realPath;
    }


    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSiz(options, 500, 500);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSiz(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
