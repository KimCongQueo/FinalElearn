package nauq.mal.com.formapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 3/28/2018.
 */

public class FileUtils {

    public static void clearAllImageTmp(Context context) {
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (cacheDir == null || (cacheDir != null && !cacheDir.exists())) {
            cacheDir = context.getCacheDir();
            String rootDir = cacheDir.getAbsolutePath() + "/TASKSAPP";
            cacheDir = new File(rootDir);
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        } else {
            cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TASKSAPP");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        deleteRecursive(cacheDir);

    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);
        fileOrDirectory.delete();
    }

    public static File resizeImages(Context context, String path) throws IOException {
//        SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());
//        File cacheDir = context.getExternalCacheDir();
//        if (cacheDir == null)
//            //fall back
//            cacheDir = context.getCacheDir();
//        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
//        File root = new File(rootDir);
//        if (!root.exists())
//            root.mkdirs();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (cacheDir == null || (cacheDir != null && !cacheDir.exists())) {
            cacheDir = context.getCacheDir();
            String rootDir = cacheDir.getAbsolutePath() + "/TASKSAPP";
            cacheDir = new File(rootDir);
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        } else {
            cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TASKSAPP");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        String rootDir = cacheDir.getAbsolutePath();
        File root = new File(rootDir);
        if (!root.exists())
            root.mkdirs();

        Bitmap bitmap = decodeImageFromFiles(path, 1024);
        File compressed = new File(root, SDF.format(new Date()) + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(bitmap == null){
            return new File(path);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(compressed);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        bitmap.recycle();
        bitmap = null;
        return compressed;
    }

    public static Bitmap decodeImageFromFiles(String path, int maxSize) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
//        while (scaleOptions.outWidth / scale / 2 >= width
//                || scaleOptions.outHeight / scale / 2 >= height) {
//            scale *= 2;
//        }
        if(scaleOptions.outWidth > scaleOptions.outHeight){
            scale = scaleOptions.outWidth/maxSize;
        } else {
            scale = scaleOptions.outHeight/maxSize;
        }
        scale++;
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(path, outOptions);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    bitmap.recycle();
                    bitmap = null;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    bitmap.recycle();
                    bitmap = null;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    bitmap.recycle();
                    bitmap = null;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static File resizeImages2(final Context context, String path, boolean isReduceQuality) throws IOException {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if (cacheDir == null || (cacheDir != null && !cacheDir.exists())) {
            cacheDir = context.getCacheDir();
            String rootDir = cacheDir.getAbsolutePath() + "/TASKSAPP";
            cacheDir = new File(rootDir);
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        } else {
            cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TASKSAPP");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        String rootDir = cacheDir.getAbsolutePath();
        File root = new File(rootDir);
        if (!root.exists())
            root.mkdirs();

        File orgFile = new File(path);
        if (orgFile.length() < 1 * 1024 * 1024) {
            return orgFile;
        }


        Bitmap bitmap;
        if (isReduceQuality) {
            bitmap = BitmapFactory.decodeFile(path);
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(path);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmap, 90);
                        bitmap.recycle();
                        bitmap = null;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmap, 180);
                        bitmap.recycle();
                        bitmap = null;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmap, 270);
                        bitmap.recycle();
                        bitmap = null;
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmap;
                }
                bitmap = rotatedBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
            scaleOptions.inJustDecodeBounds = false;
            BitmapFactory.decodeFile(path, scaleOptions);
            bitmap = decodeImageFromFiles3(path, orgFile.length(), (int) (scaleOptions.outWidth * 0.7), (int) (scaleOptions.outHeight * 0.7), scaleOptions.outWidth, scaleOptions.outHeight);
        }
        final File compressed = new File(root, SDF.format(new Date()) + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, isReduceQuality ? 60 : 50, byteArrayOutputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(compressed);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        bitmap.recycle();
        bitmap = null;
        if (compressed.length() > 1 * 1024 * 1024) {
            return resizeImages2(context, compressed.getPath(), false);
        }
        return compressed;
    }

    public static Bitmap decodeImageFromFiles3(String path, double fileSize, int width, int height, int outWidth, int outHeight) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        double scale = 1;
//        while (outWidth / scale >= width
//                && outHeight / scale >= height) {
//            scale += 0.2;
//        }
        double scale = Math.sqrt(fileSize/1024/1024*1.0);
        if(scale < 2 && scale > 1){
            scale = 2;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (outWidth / scale), (int) (outHeight / scale), false);
        bitmap.recycle();
        bitmap = null;
        return resizedBitmap;
    }

}