package me.ele.safeuri;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Created by Eric on 16/8/3.
 */
public class SafeUriUtils {

    private SafeUriUtils() {}

    private static File getImageCacheFile(Context context, String fileName) {
        File imagePath = new File(context.getCacheDir(), "images");
        if (!imagePath.exists()) {
            if (!imagePath.mkdirs()) {
                return null;
            }
        }
        return new File(imagePath, fileName);
    }

    private static Uri getImageCacheUri(Context context, String authority, File file) {
        return FileProvider.getUriForFile(context, authority, file);
    }

    private static void grantWriteUriPermission(Context context, Intent intent, Uri uri, int modeFlags) {
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            context.grantUriPermission(resolveInfo.activityInfo.packageName, uri, modeFlags);
        }
    }

    private static File getImageCacheFileInternal(Context context, Intent intent, String key, String authority, String fileName, int flag) {
        File file = getImageCacheFile(context, fileName);
        if (file == null) {
            return null;
        }
        Uri uri = getImageCacheUri(context, authority, file);
        intent.putExtra(key, uri);
        grantWriteUriPermission(context, intent, uri, flag);
        return file;
    }

    public static File getImageCacheFileForWriting(Context context, Intent intent, String key, String authority, String fileName) {
        return getImageCacheFileInternal(context, intent, key, authority, fileName, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    public static File getImageCacheFileForReading(Context context, Intent intent, String key, String authorities, String fileName) {
        return getImageCacheFileInternal(context, intent, key, authorities, fileName, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
}
