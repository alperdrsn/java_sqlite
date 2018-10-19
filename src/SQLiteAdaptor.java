
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdaptor extends SQLiteOpenHelper {

    /**SQLite Handler Class**/
    Context context;
    public static final int Database_Version = 1;   // Database Version
    public static final String Database_Name = "DB";    // Database Name
    public static final String Table_Name = "User"; // Table Name

    public static final String Column_1 = "id";     // Column 1
    public static final String Column_2 = "Name";   // Column 2

    SQLiteDatabase db;

    public SQLiteAdaptor(Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**Create Table if not exists**/
        String query = "Create table if not exists " + Table_Name + "(" + Column_1 + " Integer , " + Column_2 + " varchar(50))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**Drop table when the database version change**/
        db.execSQL("drop table if exists " + Table_Name + ";");
        onCreate(db);
    }

    /**Method Return Max+1 id**/
    public int getMaxID() {
        db = getReadableDatabase();
        Cursor cur = db.rawQuery("Select NULLIF(max(" + Column_1 + "),0) from " + Table_Name, null);
        int max = 0;
        if (cur.getCount()>0) {
            cur.moveToNext();
            max = cur.getInt(0);
        }
        cur.close();
        return max+1;
    }

    /**Insert With new name**/
    public void insert(String name) {
        db = getWritableDatabase();
        int max = getMaxID();
        db.execSQL("insert into " + Table_Name + " (" + Column_1 + ", " + Column_2 + ") values(" + max + ",'" + name + "');");
    }

    /**Update with new name**/
    public void update(int id, String name) {
        db = getWritableDatabase();
        db.execSQL("update " + Table_Name + " set " + Column_2 + " = '" + name + "' where " + Column_1 + " = " + id + ";");
    }

    /**Delete Name**/
    public void delete(int id) {
        db = getWritableDatabase();
        db.execSQL("delete from " + Table_Name + " where " + Column_1 + " = " + id + ";");
    }

    /**Return All the saved data as Cursor**/
    public Cursor view() {
        Cursor cur = db.rawQuery("Select * from " + Table_Name, null);
        return cur;
    }

    /**Returns true if exists else false**/
    public boolean checkExists(String name) {
        Cursor cur = db.rawQuery("Select * from " + Table_Name + " where " + Column_2 + " = '" + name + "'", null);
        return cur.getCount() > 0 ? true : false;
    }
}
