package com.demo.app.assignment.views.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.demo.app.assignment.R;
import com.demo.app.assignment.viewmodel.DatabaseAdapter;
import com.demo.app.assignment.viewmodel.NewsApplication;


public class HomeActivity extends FragmentActivity
{
    public DatabaseAdapter mDatabase;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityH activityHomeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
       // activityHomeBinding.getRoot();
        setContentView(R.layout.activity_home);
      //  loadFragment(new HomeFragment());
        mDatabase = ((NewsApplication)getApplication()).getDatabase();

    }
}
