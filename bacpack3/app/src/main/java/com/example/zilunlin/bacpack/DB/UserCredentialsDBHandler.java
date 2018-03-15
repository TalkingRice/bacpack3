package com.example.zilunlin.bacpack.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zilunlin.bacpack.UserInfo.User;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.permission;
import static android.R.attr.type;

/**
 * Created by zilunlin on 3/15/18.
 */

public class UserCredentialsDBHandler extends SQLiteOpenHelper{

    //Setting up the database
    private static final int DATABASE_VERSION = '1';
    private static final String DATABASE_NAME = "userCredential.db";

    public static final String TABLE_NAME = "Logins";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_PERMISSION = "PERMISSION";

    public UserCredentialsDBHandler(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        String CREATE_LOGIN_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PERMISSION + " TEXT,"
                + COLUMN_TYPE + " TEXT)";

        db.execSQL(CREATE_LOGIN_CREDENTIALS_TABLE);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Saves the user's information upon log in.
    public boolean saveLoginCredentials(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_PERMISSION, permission);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Retrieves the user's information
    public List<User> getUserCredentials() {
        List<User> userInfo = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
        if (db != null){
        try {
            Cursor user_info_query = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if (user_info_query.getCount() == 0) {
                return userInfo;
            } else {
                while (user_info_query.moveToNext()) {
                    User currentUser = new User();
                    currentUser.setName(user_info_query.getString(0));
                    currentUser.setId(user_info_query.getString(1));
                    currentUser.setType(user_info_query.getString(2));
                    currentUser.setPermission(user_info_query.getString(3));

                    userInfo.add(currentUser);
                    user_info_query.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }else {
        User defaultUser = new User();
        defaultUser.setId("2");
        defaultUser.setName("Log in!");
        defaultUser.setPermission("0");
        defaultUser.setType("Student");
        userInfo.add(0, defaultUser);
            return userInfo;
    }}

    public boolean userLogOut(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COLUMN_ID + " = " + id, null) > 0;
    }
}
