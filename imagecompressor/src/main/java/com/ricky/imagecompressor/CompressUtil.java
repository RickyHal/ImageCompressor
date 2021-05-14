package com.ricky.imagecompressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author haiyanghou
 * @date 2021/5/13
 */
public class CompressUtil {

    static {
        System.loadLibrary("jpeg-compress");
    }

    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);

    /**
     * Compress bitmap
     *
     * @param context
     * @param srcBitmap src bitmap
     * @param quality   compress quality 0-100
     * @param format    compress format
     * @return dst bitmap
     */
    public static Bitmap compressBitmap(Context context, Bitmap srcBitmap, int quality, @Nullable Bitmap.CompressFormat format) {
        String cachePath = createCachePath(context, format);
        try {
            compressBitmap(srcBitmap, quality, cachePath);
            return BitmapFactory.decodeFile(cachePath);
        } catch (Exception e) {
            return null;
        } finally {
            File file = new File(cachePath);
            file.deleteOnExit();
        }
    }

    private static String createCachePath(Context context, Bitmap.CompressFormat format) {
        String suffix = format == Bitmap.CompressFormat.JPEG ? ".jpg" : ".png";
        return getDiskCachePath(context) + File.separator + System.currentTimeMillis() + suffix;
    }

    private static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
}