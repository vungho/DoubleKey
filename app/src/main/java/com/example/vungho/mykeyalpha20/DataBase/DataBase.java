package com.example.vungho.mykeyalpha20.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by vungho on 02/05/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    /*
    Các phần mặc định, không được xóa
    This is default method, don't delete it
     */
    public DataBase(Context context) {
        super(context, "/storage/emulated/0/2KEY/atv.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public void queryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //Cài đặt file nhạc vào hệ thống
    public void insertMusicInfoToDataBase(String path, String newPath, String name){
        try{
            SQLiteDatabase database = getWritableDatabase();
            String sql = "insert into music values (?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, path);
            statement.bindString(2, newPath);
            statement.bindString(3, name);
            statement.executeInsert();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("insert music", "Lỗi");
        }
    }

    //Cài đặt file video vào hệ thống
    public void insertVideoInfoToDataBase(String path, String newPath, String name, byte[] arrayImage){
        try {
            SQLiteDatabase database = getWritableDatabase();
            String sql = "insert into video values (?, ?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, path);
            statement.bindString(2, newPath);
            statement.bindString(3, name);
            statement.bindBlob(4, arrayImage);
            statement.executeInsert();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("insert video", "Lỗi");
        }
    }

    //Cài đặt file thông tin ứng dụng vào hệ thống
    public void insertAppInfoToDataBase(String packagename, String name, byte[] arrayImage){
        try{
            SQLiteDatabase database = getWritableDatabase();
            String sql = "insert into app values(?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, packagename);
            statement.bindString(2, name);
            statement.bindBlob(3, arrayImage);
            statement.executeInsert();
            Log.i("sql ", "Thanh cong!");
        }catch (Exception e){
            e.printStackTrace();
            Log.i("sql app: ", "Lỗi");
        }
    }

    //Cài đặt file hình ảnh vào hệ thống
    public void insertImageIoDataBase (String path, String newPath, String name, byte[] arrayImage){
        try{
            SQLiteDatabase database = getWritableDatabase();
            String sql = "insert into image values (?, ?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, path);
            statement.bindString(2, newPath);
            statement.bindString(3, name);
            statement.bindBlob(4, arrayImage);
            statement.executeInsert();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("sql image", "Lỗi");
        }
    }

    /*
    Phần xử lý thông tin User
     */
    public void insertUser (String classify, String pass){
        try{
            SQLiteDatabase database = getWritableDatabase();
            String sql = "insert into user values (?, ?)";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, classify);
            statement.bindString(2, pass);
            statement.executeInsert();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Cursor getUser (String classify){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database
                .rawQuery("select pass from user where classify = '" +classify +"'", null);
        return cursor;
    }

    public boolean fixUser(String classify, String newPass){
        try {
            SQLiteDatabase database = getWritableDatabase();
            String sql = "update user set pass = ? where classify = ?";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, newPass);
            statement.bindString(2, classify);
            statement.executeUpdateDelete();
            return  true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }



    /*
    Xử lý phần trạng thái LockScreen tránh chồng lấp view
     */
    public String getLockSreen(){
        String statut = null;
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("select statut from lockScreen", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            statut = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return statut;
    }

    public boolean insertLockSrceen (String statut){
        try{
            SQLiteDatabase database = getWritableDatabase();
            SQLiteStatement statement = database.compileStatement("insert into lockScreen values(?)");
            statement.clearBindings();
            statement.bindString(1, statut);
            statement.executeInsert();
            statement.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteLockScreen (){
        try {
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL("delete from lockScreen");
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /*
    Xử lý trạng thái của tài khoản đăng nhập
     */

    public boolean insertStatusAccount(String classify){
        try{
            SQLiteDatabase database = getWritableDatabase();
            SQLiteStatement statement = database.compileStatement("insert into classifyAccount values (?)");
            statement.clearBindings();
            statement.bindString(1, classify);
            statement.executeInsert();
            statement.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteStatusAccount(){
        try {
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL("delete from classifyAccount");
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public String getStatusAccount(){
        String classify = null;
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("select classify from classifyAccount", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            classify = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return classify;
    }
}
