package android.novica.shoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Novica on 4/21/2017.
 */

public class ShoppingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ShoppingDB.db";
    private static final String TABLE_NAME = "temperatures_table";
    private static final String COL_1 = "nameOfList";
    private static final String COL_2 = "";
    private static final String COL_3 = "currTemp";
    private static final String COL_4 = "photoCode";
    private static final String COL_5 = "currDate";

    public ShoppingDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" (\n" +
                "\t`tempID`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t"+COL_1+"\tTEXT,\n" +
                "\t"+COL_2+"\tTEXT,\n" +
                "\t"+COL_3+"\tTEXT,\n" +
                "\t"+COL_4+"\tTEXT,\n" +
                "\t"+COL_5+"\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String locationName, String condition, String temperature, String photoCode, String date){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, locationName);
        values.put(COL_2, condition);
        values.put(COL_3, temperature);
        values.put(COL_4, photoCode);
        values.put(COL_5, date);
        return  (db.insert(TABLE_NAME, null, values) != -1) ?
                true : false;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);

        return result;
    }

    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
