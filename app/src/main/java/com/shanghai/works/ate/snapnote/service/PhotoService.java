package com.shanghai.works.ate.snapnote.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.shanghai.works.ate.snapnote.util.Constant;

import java.io.File;
import java.io.IOException;

/**
 * Created by fc on 15-1-6.
 */
public class PhotoService {

    public static final String APP_PATH_SD_CARD = "/SnapNote";

    public static boolean init = false;
    public static String fullPath;

    public static Uri getPhotoTempFile(){
        if(!init){
            init();
        }
        String photoPath =  fullPath + "/" + System.currentTimeMillis() + ".png";
        File photoFile = new File(photoPath);
        try {
            photoFile.createNewFile();
        } catch (IOException e) {
            Log.w(Constant.TAG, "create new file error: " + e.getMessage());
        }
        return Uri.fromFile(photoFile);
    }

    public static Intent createCameraIntent(Uri photoFile){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile); // set the image file name
        return intent;
    }

    private static void init(){
        if(!init){
            init = true;
            fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD;
            File dir = new File(fullPath);
            if(!dir.exists()){
                if(dir.mkdirs()){
                    Log.e(Constant.TAG, "folder create failure!");
                }
            }
        }
    }
}
