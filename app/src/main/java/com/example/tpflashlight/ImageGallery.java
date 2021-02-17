package com.example.tpflashlight;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ImageGallery {
    public static ArrayList<String> listofImages(Context context){
        Uri uri;
        Cursor cursor;
        final String CAMERA_IMAGE_BUCKET_NAME =Environment.getExternalStorageDirectory().toString()
                        + "/DCIM/Camera";
        final String CAMERA_IMAGE_BUCKET_ID =getBucketId(CAMERA_IMAGE_BUCKET_NAME);
        //int column_index_data, colum_index_folder_name;
        ArrayList<String>listofallImages = new ArrayList<>();
        //String absolutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };

        String orderby = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,selection,selectionArgs,orderby+" DESC");

        //column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        cursor.getCount();
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                listofallImages.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        //while (cursor.moveToNext()){
        //    absolutePathOfImage = cursor.getString(column_index_data);
        //    listofallImages.add(absolutePathOfImage);
        //}

        return  listofallImages;
    }

    private static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
}
