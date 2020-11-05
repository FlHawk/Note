package com.swufe.note.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swufe.note.bean.NoteBean;

import java.util.ArrayList;
import java.util.List;

public class DBManager{
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }
    public boolean add(NoteBean bean){
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CONTENT", bean.getContent());
        values.put("TIME", bean.getrealTime());
        if(db.insert(TBNAME, null, values)>0) flag =true;
        else flag = false;
        db.close();
        return  flag;
    }

    public boolean delete(int id){
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)})>0) flag = true;
        else flag = false;
        db.close();
        return  flag;
    }
    public boolean update(NoteBean bean){
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CONTENT", bean.getContent());
        values.put("TIME", bean.getTime());
        if(db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(bean.getId())})>0)flag=true;
        else flag=false;
        db.close();
        return flag;
    }

    public List<NoteBean> listAll(){
        List<NoteBean> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<NoteBean>();
            while(cursor.moveToNext()){
                NoteBean bean = new NoteBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                bean.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                bean.setTime(cursor.getString(cursor.getColumnIndex("TIME")));

                rateList.add(bean);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }
}
