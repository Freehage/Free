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
        super(context, DB_NAME, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDatabase() throws SQLException {

        boolean isDBExist = checkDB();
        if (!isDBExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDB();
                Log.e(TAG, "createDatabase 생성");
            } catch (IOException e) {
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
        while ((mLength = mInput.read(mBuffer)) > 0) {
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
        Log.e(TAG, b.toString());
        return DBFile.exists();
    }

    public boolean openDB() throws SQLException {
        if (!checkDB()) {
            createDatabase();
        }
        String path = DB_PATH + DB_NAME;
        try {
            mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        } catch (SQLException sqlException) {
            Log.e(TAG, "can't open db");
        }
        return mDatabase != null;
    }

    public synchronized void close() {
        if (mDatabase != null) {
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


    public List getTableData() {
        try {
            List mlist = new ArrayList();

            String sql = "SELECT * FROM " + tableName;

            Cursor cursor = mDatabase.rawQuery(sql, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Product product = new Product();
                    product.setId(cursor.getInt(0));
                    product.setObject(cursor.getString(1));
                    product.setObclass(cursor.getString(2));
                    product.setCompany(cursor.getString(3));
                    product.setObline(cursor.getString(4));
                    product.setObrecy(cursor.getString(5));
                    product.setOblevel(cursor.getInt(6));
                    product.setOboutC(cursor.getString(7));
                    product.setObendday(cursor.getString(8));

                    mlist.add(product);
                }
            }
            return mlist;
        } catch (SQLException sqlException) {
            Log.e(TAG, sqlException.toString());
            throw sqlException;
        }
    }


    public ArrayList getObjectResult(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        //String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        //Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE OBJECT LIKE %'" + search + "'%", null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"", null);
        while (cursor.moveToNext()) {
            arrayList_OB.add(cursor.getInt(0));
        }
        return arrayList_OB;
    }

    public String getCompanyResult(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String company = "";
        //String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력 "UPDATE TodoList SET title='"+_search+"'
        //Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%"+search+"%\"", null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            company = cursor.getString(3);
        }
        return company;
    }


    //추천알고리즘
    public String getCategory(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String category = "";
        //이름이 ! 일 떄 해당 상품의 카테고리 얻음
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            category = cursor.getString(4); //OBLINE얻음
        }
        return category;
    }

    public ArrayList getObjectsResult(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        //OBLINE이 ~인 상품 중에 탄소배출량이 적은 제품 순서대로 3개 나열
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBLINE = '" + search + "' ORDER BY OBOUTC ASC LIMIT 3 ", null);
        while (cursor.moveToNext()) {
            arrayList_OB.add(cursor.getString(1)); // 상품명 Append
        }
        return arrayList_OB;
    }

    public String getRecycle(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String recycle = "";
        //String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력 "UPDATE TodoList SET title='"+_search+"'
        //Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%"+search+"%\"", null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            recycle = cursor.getString(5);
        }
        return recycle;
    }


    public String getLevel(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String level = "";
        //String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력 "UPDATE TodoList SET title='"+_search+"'
        //Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%"+search+"%\"", null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            level = Integer.toString(cursor.getInt(6));
        }
        return level;
    }

    public String getEndDate(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String end_date = "";
        //String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력 "UPDATE TodoList SET title='"+_search+"'
        //Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%"+search+"%\"", null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            end_date = Integer.toString(cursor.getInt(8));
        }
        return end_date;
    }

    //이름 알 때 탄소배출량 알기
    public String getCarbon(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String amount = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            amount = Integer.toString(cursor.getInt(7)); //탄소배출량
        }
        return amount;
    }
}