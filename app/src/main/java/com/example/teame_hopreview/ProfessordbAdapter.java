package com.example.teame_hopreview;

import static com.example.teame_hopreview.CoursedbAdapter.COURSE_TABLE;
import static com.example.teame_hopreview.CoursedbAdapter.CRSE_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProfessordbAdapter {
    private SQLiteDatabase db;
    private ProfessordbAdapter.ProfessordbHelper dbHelper;
    private final Context context;

    private static final String DB_NAME = "hopreview.db";
    private static int dbVersion = 1;

    protected static final String PROF_TABLE = "professors";
    public static final String PROF_ID = "prof_id";   // column 0
    public static final String PROF_NAME = "prof_name";
    public static final String PROF_DEPT = "prof_dept";
    public static final String PROF_RATINGS = "prof_ratings";
    public static final String COURSE_ID = "course_id";
    public static final String[] PROF_COLS = {PROF_ID, PROF_NAME, PROF_DEPT, PROF_RATINGS};

    public ProfessordbAdapter(Context ctx) {
        context = ctx;
        dbHelper = new ProfessordbHelper(context, DB_NAME, null, dbVersion);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public void clear() {
        dbHelper.onUpgrade(db, dbVersion, dbVersion+1);  // change version to dump old data
        dbVersion++;
    }

    // database update methods
    public long insertProfessor(Professor prof) {
        // create a new row of values to insert
        ContentValues cvalues = new ContentValues();
        // assign values for each col
        cvalues.put(PROF_NAME, prof.getProfessorName());
        cvalues.put(PROF_DEPT, prof.getDepartment());
        // TODO: edit this
//        cvalues.put(PROF_COURSES, prof.getCourses());
        // add to course table in database
        return db.insert(PROF_TABLE, null, cvalues);
    }

    //    public boolean removeCourse(long ri) {   // ri is the course id
    //        return db.delete(COURSE_TABLE, "CRSE_ID="+ri, null) > 0;
    //    }

    //    public boolean updateName(long ri, String nm) {
    //        ContentValues cvalue = new ContentValues();
    //        cvalue.put(CRSE_NAME, nm);
    //        return db.update(COURSE_TABLE, cvalue, CRSE_ID+"="+ri, null) > 0;
    //    }

    //    public boolean updateDesignation(long ri, String gr) {
    //        ContentValues cvalue = new ContentValues();
    //        cvalue.put(CRSE_DESIGNATION, gr);
    //        return db.update(COURSE_TABLE, cvalue, CRSE_ID+"="+ri, null) > 0;
    //    }

    public boolean updateRatings(long ri, float cr) {
        ContentValues cvalue = new ContentValues();
        cvalue.put(PROF_RATINGS, cr);
        return db.update(PROF_TABLE, cvalue, PROF_ID+"="+ri, null) > 0;
    }

    // database query methods
    public Cursor getAllProfessors() {
        return db.query(PROF_TABLE, PROF_COLS, null, null, null, null, null);
    }

    public Cursor getProfessorCursor(long ri) throws SQLException {
        Cursor result = db.query(true, PROF_TABLE, PROF_COLS, PROF_ID+"="+ri, null, null, null, null, null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No course items found for row: " + ri);
        }
        return result;
    }

//    public Professor getProfessorItem(long ri) throws SQLException {
//        Cursor cursor = db.query(true, PROF_TABLE, PROF_COLS, PROF_ID+"="+ri, null, null, null, null, null);
//        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
//            throw new SQLException("No course items found for row: " + ri);
//        }
//        // must use column indices to get column values
//        int nameIndex = cursor.getColumnIndex(PROF_NAME);
//        Professor result = new Professor(cursor.getString(nameIndex), cursor.getString(2), cursor.getString(3));
//        return result;
//    }

    private static class ProfessordbHelper extends SQLiteOpenHelper {

        // SQL statement to create a new database
        private static final String DB_CREATE = "CREATE TABLE " + PROF_TABLE
                + " (" + PROF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROF_NAME + " TEXT,"
                + PROF_DEPT + " TEXT, " + PROF_RATINGS + " TEXT," + COURSE_ID + " INTEGER," +
                " FOREIGN KEY ("+COURSE_ID+") REFERENCES "+COURSE_TABLE+"("+CRSE_ID+"));";

        public ProfessordbHelper(Context context, String name, SQLiteDatabase.CursorFactory fct, int version) {
            super(context, name, fct, version);
        }

        @Override
        public void onCreate(SQLiteDatabase adb) {
            // TODO Auto-generated method stub
            adb.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase adb, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.w("Coursedb", "upgrading from version " + oldVersion + " to "
                    + newVersion + ", destroying old data");
            // drop old table if it exists, create new one
            // better to migrate existing data into new table
            adb.execSQL("DROP TABLE IF EXISTS " + PROF_TABLE);
            onCreate(adb);
        }
    }
}
