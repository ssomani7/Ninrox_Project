package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	static final String KEY_ROWID = "_id";
	static final String KEY_VALUE = "Score";
	static final String TAG = "DBAdapter";

	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "score";
	static final int DATABASE_VERSION = 2;

	static final String DATABASE_CREATE = "create table score (_id integer primary key autoincrement, "
			+ "Score integer not null);";

	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
				ContentValues initialValues = new ContentValues();
				initialValues.put(KEY_VALUE, 0);
				db.insert(DATABASE_TABLE, null, initialValues);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long insertScore(int score) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_VALUE, score);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public Cursor getHighScore() throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_VALUE }, KEY_ROWID + "=" + "1", null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updateScore(int score) {
		ContentValues args = new ContentValues();
		args.put(KEY_VALUE, score);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + "1", null) > 0;
	}

}
