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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper3 extends SQLiteOpenHelper {
    private static String TAG = "데이터 베이스 helper";
    private static String DB_PATH = "";
    private static String DB_NAME = "FreeProductDB.db";
    public static String tableName = "User";

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    public Cursor cursor_for_object;
    public float point = 0;
    public Map<String,Float> return_value = new HashMap<String,Float>();

    public DatabaseHelper3(@Nullable Context context) {
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
                    product.setCompany(cursor.getString(2));
                    product.setObline(cursor.getString(3));
                    product.setObrecy(cursor.getString(4));
                    product.setOblevel(cursor.getInt(5));
                    product.setOboutC(cursor.getInt(6));

                    product.setUnitoboutC(cursor.getDouble(7));
                    product.setPrice(cursor.getDouble(8));
                    product.setScore(cursor.getDouble(9));

                    product.setObendday(cursor.getString(10));

                    product.setImageUrl(cursor.getString(11));
                    product.setUrl(cursor.getString(12));

                    product.setReCarbon(cursor.getDouble(13));
                    product.setRePrice(cursor.getDouble(14));
                    product.setReScore(cursor.getDouble(15));

                    mlist.add(product);
                }
            }
            return mlist;
        } catch (SQLException sqlException) {
            Log.e(TAG, sqlException.toString());
            throw sqlException;
        }
    }

    //이름 포함된 제품명 가져오기
    public ArrayList getObjectResult(String search) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        if(search.length() != 0){
            cursor_for_object = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"", null);
        }
        while (cursor_for_object.moveToNext()) {
            arrayList_OB.add(cursor_for_object.getString(1));
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
            company = cursor.getString(2);
        }
        return company;
    }


    //추천알고리즘
    public String getCategory(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String category = "";
        //이름이 ! 일 떄 해당 상품의 카테고리 얻음
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"", null);
        while (cursor.moveToNext()) {
            category = cursor.getString(3); //OBLINE얻음
        }
        return category;
    }

    public ArrayList getObjectsResult(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        //OBLINE이 ~인 상품 중에 탄소배출량이 적은 제품 순서대로 3개 나열 + 같은 제품은 제외
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBLINE = '" + search + "' AND OBJECT != '" + search + "'" +
                "ORDER BY OBLEVEL DESC, OBOUTC ASC LIMIT 3 ", null);
        while (cursor.moveToNext()) {

            arrayList_OB.add(cursor.getString(1)); // 상품명 Append
        }
        return arrayList_OB;
    }


    public ArrayList getObjectsResult_for_money(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"" +
                "ORDER BY PRICE ASC ", null);
        while (cursor.moveToNext()) {

            arrayList_OB.add(cursor.getString(1)); // 상품명 Append
        }
        return arrayList_OB;
    }
    public ArrayList getObjectsResult_for_carbon(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"" +
                "ORDER BY OBOUTC ASC ", null);
        while (cursor.moveToNext()) {
            arrayList_OB.add(cursor.getString(1)); // 상품명 Append
        }
        return arrayList_OB;
    }

    public ArrayList getObjectsResult_for_score(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList arrayList_OB = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"" +
                "ORDER BY SCORE DESC ", null);
        while (cursor.moveToNext()) {

            arrayList_OB.add(cursor.getString(1)); // 상품명 Append
        }
        return arrayList_OB;
    }

    //제품명 알 때 재활용 분류 파악
    public String getRecycle(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String recycle = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            recycle = cursor.getString(4);
        }
        return recycle;
    }

    //제품명 알 때 탄소중립마크 레벨 파악
    public String getLevel(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String level = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            level = Integer.toString(cursor.getInt(5));
        }
        return level;
    }

    //제품명 알 때 재활용 분류 파악
    public String getEndDate(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String end_date = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            end_date = Integer.toString(cursor.getInt(8));
        }
        return end_date;
    }

    //제품명 알 때 탄소배출량 파악
    public String getCarbon(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String amount = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            amount = Double.toString(cursor.getDouble(6)); //탄소배출량
        }
        return amount;
    }

    public String getMoney(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String Money = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            Money = Double.toString(cursor.getDouble(8)); //가격
        }
        return Money;
    }

    public String getScore(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String Score = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            Score = Double.toString(cursor.getDouble(9)); //점수
        }
        return Score;
    }

    public ArrayList<String> getproductObject(String search, double carbon_point, double score_point, double price_point, Context context) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> real = new ArrayList<String>();
        if(search.length() != 0){
            cursor_for_object = db.rawQuery("SELECT * FROM User WHERE OBJECT LIKE \"%" + search + "%\"", null);
        }
        while (cursor_for_object.moveToNext()){
            double carbon = cursor_for_object.getDouble(13);
            double score = cursor_for_object.getDouble(15);
            double price = cursor_for_object.getDouble(14);
            point = (float) (carbon_point * carbon + score_point * score + price_point * price);
            return_value.put(cursor_for_object.getString(1),point);
        }
        Log.e("RETURN", String.valueOf(return_value));

        List<Map.Entry<String,Float>> list_entries = new ArrayList<Map.Entry<String,Float>>(return_value.entrySet());

        // 비교함수 Comparator를 사용하여 오름차순으로 정렬
        Collections.sort(list_entries, new Comparator<Map.Entry<String,Float>>() {
            // compare로 값을 비교
            public int compare(Map.Entry<String,Float> obj1, Map.Entry<String,Float> obj2) {
                // 오름 차순 정렬
                return obj1.getValue().compareTo(obj2.getValue());
            }
        });
        for(Map.Entry<String,Float> entry : list_entries) {
            real.add(entry.getKey());
        }

        return real;
    }

    public String getUrl(String search) {
        SQLiteDatabase db = getReadableDatabase();
        String Url = "";
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE OBJECT = '" + search + "' ", null);
        while (cursor.moveToNext()) {
            Url = cursor.getString(12);
        }
        return Url;
    }




}