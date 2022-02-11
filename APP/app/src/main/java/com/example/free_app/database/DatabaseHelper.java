package com.example.free_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.free_app.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "데이터 베이스 helper";
    private static String DB_PATH = "";
    private static String DB_NAME = "FreeAppDB.db";
    public static String tableName = "User";

    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null,1);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDatabase() throws SQLException{

        boolean isDBExist = checkDB();
        if(!isDBExist){
            this.getReadableDatabase();
            this.close();
            try{
                copyDB();
                Log.e(TAG,"createDatabase 생성");
            }
            catch (IOException e){
                throw new Error("ErrorCopyingDB!!!!!!!");
            }
        }
    }

    private void copyDB() throws IOException {

        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();


    }

    public boolean checkDB() {
        File DBFile = new File(DB_PATH + DB_NAME);
        Boolean b;
        b = DBFile.exists();
        Log.e(TAG,b.toString());
        return DBFile.exists();
    }

    public boolean openDB() throws SQLException {
        if(!checkDB()){
            createDatabase();
        }
        String path = DB_PATH + DB_NAME;
        try{
            mDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        }catch (SQLException sqlException){
            Log.e(TAG,"can't open db");
        }
        return mDatabase != null;
    }

    public synchronized void close(){
        if(mDatabase != null){
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }





    public List getTableData(){
        try {
            List mlist = new ArrayList();

            String sql = "SELECT * FROM " + tableName;

            Cursor cursor = mDatabase.rawQuery(sql,null);

            if(cursor != null){
                while(cursor.moveToNext()){
                    Product product = new Product();
                    product.setId(cursor.getInt(0));
                    product.setObject(cursor.getString(1));
                    product.setCompany(cursor.getString(2));
                    product.setObline(cursor.getString(3));
                    product.setObrecy(cursor.getString(4));
                    product.setOblevel(cursor.getInt(5));
                    product.setOboutC(cursor.getString(6));
                    product.setObendday(cursor.getString(7));

                    mlist.add(product);
                }
            }
            return mlist;
        }catch (SQLException sqlException){
            Log.e(TAG,sqlException.toString());
            throw sqlException;
        }
    }





}
