package com.example.vsgo_juniormobileprogrammer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.vsgo_juniormobileprogrammer.models.PresenceModel;
import com.example.vsgo_juniormobileprogrammer.models.UserModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "DB_Presensi.db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_USER = "users";
    private static final String TABLE_PRESENSI = "presences";

    public static final String COL_PRESENCE_ID = "presence_id";
    public static final String COL_ON_DUTY = "on_duty";
    public static final String COL_OFF_DUTY = "off_duty";
    public static final String COL_DATE = "date";

    public static final String COL_NIM = "nim";
    public static final String COL_FULLNAME = "fullname";
    public static final String COL_BIRTHDATE = "birthdate";
    public static final String COL_GENDER = "gender";
    public static final String COL_PASSWORD = "password";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE "+TABLE_USER+" ("+
            COL_NIM + " TEXT UNIQUE, "+
            COL_FULLNAME + " TEXT,"+
            COL_BIRTHDATE + " TEXT,"+
            COL_GENDER + " TEXT,"+
            COL_PASSWORD + " TEXT );"
            ;
    private static final String CREATE_TABLE_PRESENSI =
            "CREATE TABLE "+TABLE_PRESENSI+" ("+
                    COL_PRESENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COL_NIM + " TEXT, "+
                    COL_ON_DUTY + " TEXT,"+
                    COL_OFF_DUTY + " TEXT," +
                    COL_DATE + " TEXT );"
            ;

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS "+TABLE_USER+";";
    private static final String DROP_TABLE_PRESENSI = "DROP TABLE IF EXISTS "+TABLE_PRESENSI+";";

    private SQLiteDatabase writableDatabase = this.getWritableDatabase();

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRESENSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_PRESENSI);
        onCreate(db);
    }

    public Boolean registerUser(UserModel userModel){
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_USER+" WHERE "+COL_NIM+" = '"+userModel.getNim()+"'";
        Cursor data = readableDatabase.rawQuery(query, null);
        if(data.moveToFirst()){
            return false;
        }else{
            ContentValues val = new ContentValues();
            val.put(COL_NIM, userModel.getNim());
            val.put(COL_FULLNAME, userModel.getFullname());
            val.put(COL_BIRTHDATE, userModel.getBirthdate());
            val.put(COL_GENDER, userModel.getGender());
            val.put(COL_PASSWORD, userModel.getPassword());
            writableDatabase.insert(TABLE_USER, null, val);
            return true;
        }


    }

    public Boolean getUser(String nim, String password){
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_USER+" WHERE "+COL_NIM+" = '"+nim+"' AND "+COL_PASSWORD+" = '"+password+"'";
        Cursor data = readableDatabase.rawQuery(query, null);
        return data.moveToFirst();
    }

    public Boolean onPresence(PresenceModel presenceModel){
       try{
           SQLiteDatabase dbReadable = this.getReadableDatabase();
           SQLiteDatabase dbWritable = this.getWritableDatabase();

           String query = "SELECT * FROM "+TABLE_PRESENSI
                   + " WHERE "+COL_NIM+" = '"+presenceModel.getNim()
                   +"' AND "+COL_DATE+"= '"+presenceModel.getDate()+"'";
           Cursor data = dbReadable.rawQuery(query, null);
           ContentValues val = new ContentValues();
           if(data.moveToFirst()){
               boolean isOffDuty = data.getString(3).isEmpty();
               if(isOffDuty){
                   val.put(COL_OFF_DUTY, presenceModel.getOffDuty());
                   dbWritable.update(TABLE_PRESENSI, val, "nim=? AND date=?",new String[]{presenceModel.getNim(), presenceModel.getDate()});
                   return true;
               }else{
                   return false;
               }
           }else{
               val.put(COL_NIM, presenceModel.getNim());
               val.put(COL_DATE, presenceModel.getDate());
               val.put(COL_ON_DUTY, presenceModel.getOnDuty());
               val.put(COL_OFF_DUTY, "");
               dbWritable.insert(TABLE_PRESENSI, null, val);
               return true;
           }

       } catch(Exception e){
           return false;
       }
    }

    public List<PresenceModel> getPresences(String nim) {
        List<PresenceModel> presenceModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_PRESENSI+ " WHERE "+COL_NIM+" = '"+nim+"'";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToFirst()){
            do{
                presenceModelList.add(new PresenceModel(
                        data.getString(0),
                        data.getString(1),
                        data.getString(4),
                        data.getString(2),
                        data.getString(3)
                ));
            } while (data.moveToNext());
        }
        data.close();
        return presenceModelList;
    }

    public UserModel getUser(String nimParam){
        try{
            SQLiteDatabase readDB = this.getReadableDatabase();
            String query = "SELECT * FROM "+TABLE_USER+" WHERE "+COL_NIM+" = '"+nimParam+"'";
            Cursor cursor = readDB.rawQuery(query, null);
            String nim, fullname, birthdate, gender, password;
            if(cursor.moveToFirst()){
                nim = cursor.getString(0);
                fullname = cursor.getString(1);
                birthdate = cursor.getString(2);
                gender = cursor.getString(3);
                password = cursor.getString(4);
                return new UserModel(nim, fullname, birthdate, gender, password);
            }
            return null;
        }catch (SQLiteException e){
            System.out.println("Error Get User");
            System.out.println("Error Description : " + e.toString());
            return null;
        }
    }

    public Boolean changeUserProfile(UserModel userModel){
        SQLiteDatabase writabelDB = this.writableDatabase;
        try{
            ContentValues val = new ContentValues();
            val.put(COL_NIM, userModel.getNim());
            val.put(COL_FULLNAME, userModel.getFullname());
            val.put(COL_BIRTHDATE, userModel.getBirthdate());
            val.put(COL_GENDER, userModel.getGender());
            val.put(COL_PASSWORD, userModel.getPassword());

            writabelDB.update(TABLE_USER, val,"nim=?", new String[]{userModel.getNim()});
            return true;
        }catch(SQLiteException e){
            System.out.println("Update Profile Error");
            System.out.println("Error Desc : "+ e);
            return false;
        }

    }


}
