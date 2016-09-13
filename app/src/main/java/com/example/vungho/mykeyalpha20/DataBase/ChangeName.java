package com.example.vungho.mykeyalpha20.DataBase;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by vungho on 24/06/2016.
 */
public class ChangeName {

    public static String changePathPlus(String old){
        File file = new File(old);
        String nameChir = file.getName();
        String parent = file.getParent();
        String newChir = parent + "/." + nameChir + ".mullock";
        return newChir;
    }

    public static boolean changePath(String old, String newChir){
        File file = new File(old);
        File fileTo = new File(newChir);

        Log.i("chir", old);
        Log.i("new", newChir);

        boolean sucess = file.renameTo(fileTo);
        return sucess;
    }

    public static boolean recoveryPath(String from, String to){
        File fromFile = new File(from);
        File toFile = new File(to);

        if (fromFile.renameTo(toFile)){
            Log.i("recovery", "Success");
            return true;
        }else {
            Log.i("recovery", "Faild");
            return false;
        }
    }
}
