package com.demo.app.assignment.viewmodel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.demo.app.assignment.model.News;
import com.demo.app.assignment.utils.Constants;

import java.util.ArrayList;



public class DatabaseAdapter {

	private DatabaseHelper DBHelper;

	private SQLiteDatabase db;
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	private final Context context;

	// Logcat Log
	private static final String TAG = "DatabaseHelperNews";

	// Database Name
	private static final String DATABASE_NAME = Constants.DB_NAME;
	// tables name
	private static final String TABLE_NAME = Constants.TABLE_NEWS;

	//  Table Columns names
	private static final String KEY_ID = Constants.KEY_ID;
	private static final String KEY_TITLE = "title";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_IMAGE = "imageHref";

    // table created
	static String CREATE_NEWS_TABLE = "CREATE TABLE "
			+ TABLE_NAME + "(" + KEY_ID
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
			+ KEY_DESCRIPTION + " TEXT,"
			+ KEY_IMAGE + " TEXT"+ ")";

	public DatabaseAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			 super(context, DATABASE_NAME, null, DATABASE_VERSION);
			//super(context, "/sdcard/" + DATABASE_NAME, null, DATABASE_VERSION);
			/*SQLiteDatabase.openOrCreateDatabase("/sdcard/" + DATABASE_NAME,
					null);*/
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_NEWS_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	public DatabaseAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	// Database lock to prevent conflicts.
	public static final Object[] databaseLock = new Object[0];


	public void addNews(News.RowsItem info) {

		synchronized (databaseLock) {
			ContentValues values = new ContentValues();
			values.put(KEY_TITLE, info.getTitle());
			values.put(KEY_DESCRIPTION, info.getDescription());
			values.put(KEY_IMAGE, info.getImageHref());
			// Inserting Row
			try {
				db.insert(TABLE_NAME, null, values);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// Getting All RowItems
	public ArrayList<News.RowsItem> getAllNewsRow() {
		ArrayList<News.RowsItem> newslist = new ArrayList<News.RowsItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			News.RowsItem info = new News().new RowsItem();
			info.setTitle(cursor.getString(1));
			info.setDescription(cursor.getString(2));
			info.setImageHref(cursor.getString(3));
			// Adding contact to list
			newslist.add(info);
			cursor.moveToNext();
		}
		// return news list
		return newslist;
	}

	// Getting single subject
	public News.RowsItem getNews(String title) {

		Cursor cursor = db.query(TABLE_NAME, new String[] {
				KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_IMAGE}, KEY_ID + "=?",
				new String[] { title }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		News.RowsItem info = new News().new RowsItem();
		info.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
		info.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
		info.setImageHref(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
		cursor.close();
		return info;
	}


	public void deleteAllStudents() {
		db.delete(TABLE_NAME, null, null);
	}

	// Getting News Count
	public int getNewsCount() {
		Cursor c = db.query(TABLE_NAME, new String[] { KEY_ID },
				null, null, null, null, null);
		return c == null ? 0 : c.getCount();
	}

	// Deleting News
	public boolean deleteNewsInfo(int row_id) {
		int deleteCount = db.delete(TABLE_NAME, KEY_ID + "="
				+ row_id, null);
		return (deleteCount > 0);
	}

//	// Getting Announcement, Coursework Count
//	public int getAnnounceCount() {
//		Cursor c = db.query(TABLE_NAME,
//				new String[] { KEY_ID }, null, null, null, null, null);
//		return c == null ? 0 : c.getCount();
//	}

}
