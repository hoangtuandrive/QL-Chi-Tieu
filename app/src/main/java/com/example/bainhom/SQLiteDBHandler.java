package com.example.bainhom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "N11-QLChiTieu";

    private static final String TABLE_DichVu = "DichVu";
    private static final String KEY_ID = "id";
    private static final String KEY_TIEN = "tien";
    private static final String KEY_DICHVU = "dichvu";
    private static final String KEY_GHICHU = "ghichu";
    private static final String KEY_NGAY = "ngay";
    private static final String KEY_THANHTOAN = "thanhtoan";

    private static final String create_table_dv = "CREATE TABLE " + TABLE_DichVu + "("
            + KEY_ID + " TEXT PRIMARY KEY, "
            + KEY_TIEN + " DOUBLE, "
            + KEY_DICHVU + " TEXT, " + KEY_GHICHU + " TEXT, " + KEY_NGAY + " TEXT, "
            + KEY_THANHTOAN + " TEXT"+ ")";

    private Context context;

    public SQLiteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context=this.context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(create_table_dv);
        } catch(Exception e){

        }

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DichVu);

        // Create tables again
        onCreate(db);
    }



    public void addDV(DV dv) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, dv.getId());
        values.put(KEY_TIEN, dv.getTien());
        values.put(KEY_DICHVU, dv.getDichvu());
        values.put(KEY_GHICHU, dv.getGhichu());
        values.put(KEY_NGAY, dv.getNgay());
        values.put(KEY_THANHTOAN, dv.getThanhtoan());
        // Inserting Row
        db.insert(TABLE_DichVu, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<DV> getAllDV() {
        List<DV> dvList = new ArrayList<DV>();
        // Select All Query
        String selectQuery = "SELECT id,tien,dichvu,ghichu,ngay,thanhtoan FROM " + TABLE_DichVu;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DV dv=new DV();
//                dv.getTaiKhoan().setEmail(cursor.getString(0));
//                dv.getThongTinCaNhan().setHoTen(cursor.getString(1));
                dv.setId(cursor.getString(0));
                dv.setTien(cursor.getDouble(1));
                dv.setDichvu(cursor.getString(2));
                dv.setGhichu(cursor.getString(3));
                dv.setNgay(cursor.getString(4));
                dv.setThanhtoan(cursor.getString(5));

                dvList.add(dv);
            } while (cursor.moveToNext());
        }
        return dvList ;
    }

    public int updateDV(DV dv,double tien, String dichvu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIEN, tien);
        values.put(KEY_DICHVU, dichvu);

        // updating row
        return db.update(TABLE_DichVu, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dv.getId()) });
    }

    public void deleteDV(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DichVu, KEY_ID + " = ?",
                new String[] { String.valueOf(id)});
        db.close();
    }

}