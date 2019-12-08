package com.example.tutorsearcherandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ProfilePictureUtil {
    public static String BitMapToString(Bitmap bitmap){
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG,20, baos);
        byte[] b = baos.toByteArray();

        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
