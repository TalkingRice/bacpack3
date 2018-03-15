package com.example.zilunlin.bacpack.DB;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zilunlin on 3/15/18.
 */

public class UserCredentialsDBHandler extends SQLiteOpenHelper{

    //Setting up the database
    private static final int DATABASE_VERSION = '1';
    private static final String DATABASE_NAME = "userCredential.db";

    public static final String TABLE_NAME = "Logins";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PERMISSION = "permission";

    public UserCredentialsDBHandler(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PERMISSION + " TEXT,"
                + COLUMN_TYPE + " TEXT )";

        db.execSQL(CREATE_LOGIN_CREDENTIALS_TABLE);
    }
}
