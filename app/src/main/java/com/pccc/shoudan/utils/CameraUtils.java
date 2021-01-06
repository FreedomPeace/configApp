package com.pccc.shoudan.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

public class CameraUtils {
    public static void savePhoto(int resultCode, Intent data) {
        if (resultCode==Activity.RESULT_OK) {
            if (data!=null) {

            }
        }
    }

    public enum  Type{
        PRE(1),//前置
        AFTER(0);//后置
        private int i;
        Type(int i) {
            this.i = i;
        }
        public int getValues(){
            return i;
        }

    }
    private static void takePhotoCommon(Activity activity,Fragment fragment, int requestCode, String absFilenamePath, Type type) {
        if (absFilenamePath!=null) {
            String absDir = absFilenamePath.substring(0, absFilenamePath.lastIndexOf("/"));
            File file = new File(absDir);
            if (!file.exists()) {
                boolean mkdir = file.mkdirs();
                System.out.println(mkdir);
            }
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(absFilenamePath)));
        takePictureIntent.putExtra("cameraid", type==null?0: type.getValues());// 0:后置,1前置
        takePictureIntent.putExtra("width", 1600);
        takePictureIntent.putExtra("height", 1200);
        if (activity != null) {
            activity.startActivityForResult(takePictureIntent, requestCode);
        } else {
            fragment.startActivityForResult(takePictureIntent,requestCode);
        }
    }

    //默认使用后置摄像头
    public static void takePhoto(Activity activity, int requestCode, String absFilenamePath) {
        takePhoto(activity,requestCode,absFilenamePath,null);
    }
    public static void takePhoto(Activity activity, int requestCode, String absFilenamePath, Type type) {
        takePhotoCommon(activity,null, requestCode, absFilenamePath, type);
    }

    //默认使用后置摄像头
    public static void takePhoto(Fragment fragment, int requestCode, String absFilenamePath) {
        takePhoto(fragment,requestCode,absFilenamePath,null);
    }
    public static void takePhoto(Fragment fragment, int requestCode, String absFilenamePath, Type type) {
        takePhotoCommon(null,fragment, requestCode, absFilenamePath, type);
    }
}
