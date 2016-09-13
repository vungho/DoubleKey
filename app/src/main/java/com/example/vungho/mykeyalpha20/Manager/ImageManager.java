package com.example.vungho.mykeyalpha20.Manager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by vungho on 31/05/2016.
 */
public class ImageManager {

    public static byte[] convertImageToArr(String uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);

        options.inSampleSize = calculateSampleSise(options, 128, 128);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(uri, options);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 96, outputStream);

        byte[] arrImage = outputStream.toByteArray();
        return arrImage;
    }

    public static byte[] covertImageToArr(Bitmap bitmap){
        byte[] arrImage = null;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 96, outputStream);
        arrImage = outputStream.toByteArray();

        return arrImage;
    }

    private static int calculateSampleSise(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        final int outHeight = options.outHeight;
        final int outWidth = options.outWidth;
        int inSampleSize = 1;

        if (outHeight > reqHeight || outWidth > reqHeight) {
            final int tempHeight = outHeight / 2;
            final int tempWidth = outWidth / 2;

            //Calculate sampleSize
            while ((tempHeight / inSampleSize) > reqHeight && (tempWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap convertIntoBitmap(byte[] image) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }


    public static byte[] convertDrawableToByte(Drawable drawable) {

        Bitmap bitmap = null;
        byte[] arrImage = null;
        try {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            arrImage = outputStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrImage;
    }

    public static Bitmap covertIntoBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        try {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
