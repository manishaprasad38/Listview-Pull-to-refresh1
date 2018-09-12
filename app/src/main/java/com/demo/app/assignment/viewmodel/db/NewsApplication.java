package com.demo.app.assignment.viewmodel.db;

import android.app.Application;

public class NewsApplication extends Application {
	private DatabaseAdapter mDatabase;

	@Override
	public void onCreate() {
		super.onCreate();

		mDatabase = new DatabaseAdapter(this);

	}

	public DatabaseAdapter getDatabase() {
		return mDatabase;
	}
}
