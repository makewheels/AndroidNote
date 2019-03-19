package com.eg.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eg.note.bean.DaoMaster;

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
