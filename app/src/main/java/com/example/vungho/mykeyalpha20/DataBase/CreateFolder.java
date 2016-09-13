package com.example.vungho.mykeyalpha20.DataBase;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by vungho on 24/06/2016.
 */
public class CreateFolder {

    private static  final String dir = "2KEY";

    public CreateFolder() {
        File folfer = new File(Environment.getExternalStorageDirectory() + File.separator + dir);
        boolean success = true;

        if (!folfer.exists()){
            success = folfer.mkdir();
            Log.i("mk", folfer.getPath());
        }
        if (success){
            Log.i("folder", "Create folder success!");
        }else {
            Log.i("folder", "Create folfer fail");
        }
    }
}
