package com.bank.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

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
    public static void takePhoto(Activity context, int requestCode, String absFilenamePath, Type type) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(absFilenamePath)));
        takePictureIntent.putExtra("cameraid", type==null?0: type.getValues());// 0:后置,1前置
        takePictureIntent.putExtra("width", 1600);
        takePictureIntent.putExtra("height", 1200);
        context.startActivityForResult(takePictureIntent, requestCode);

    }
    //默认使用后置摄像头
    public static void takePhoto(Activity context, int requestCode, String absFilenamePath) {
        takePhoto(context,requestCode,absFilenamePath,null);
    }
}
