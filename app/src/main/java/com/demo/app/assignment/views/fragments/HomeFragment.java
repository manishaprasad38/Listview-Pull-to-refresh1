package com.demo.app.assignment.views.fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.assignment.R;
import com.demo.app.assignment.model.News;
import com.demo.app.assignment.viewmodel.db.DatabaseAdapter;
import com.demo.app.assignment.viewmodel.db.NewsApplication;
import com.demo.app.assignment.viewmodel.callback.ApiServices;
import com.demo.app.assignment.viewmodel.callback.AppGlobalTags;
import com.demo.app.assignment.views.activity.HomeActivity;
import com.demo.app.assignment.views.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //intialization
    HomeAdapter mAdapter;
    ListView mListView;
    public DatabaseAdapter mDatabase;
    private static final String TAG = "HomeFragment";
    ArrayList<News.RowsItem> infoList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView txtHeader;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment using data binding
//        FragmentHomeBinding homeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,null,false);
//        View view = homeBinding.getRoot();
//        homeBinding.setNewsModel(newsViewModel);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setRetainInstance(true);
        mListView = view.findViewById(R.id.listInfo);
        txtHeader = view.findViewById(R.id.header);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.simpleSwipeRefreshLayout);
        infoList = new ArrayList<News.RowsItem>();
        mDatabase = ((NewsApplication) getActivity().getApplication()).getDatabase();
        if (checkInternetConenction()) {
            DataParse();
        } else {
            new OfflineNewsAsync().execute();
        }
// implement setOnRefreshListener event on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                swipeRefreshLayout.setRefreshing(false);
                if (checkInternetConenction()) {
                    DataParse();
                } else {
                    new OfflineNewsAsync().execute();
                }
            }
        });

        return view;
    }


    public void DataParse() {
        try {
            ApiServices.ApiInterfaces apiInterfaces = ApiServices.getInstance()
                    .getClient(ApiServices.BASE_URL)
                    .create(ApiServices.ApiInterfaces.class);

            Call<News> call = apiInterfaces.news();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    try {
                        int responseCode = response.code();
                        if (responseCode == AppGlobalTags.GlobalTag.RESULT_CODE_OK) {
                         //   Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
                            /**
                             * Got Successfully
                             */
                            txtHeader.setText(response.body().getTitle());
                            /**
                             * Binding that List to Adapter
                             */
                            List<News.RowsItem> news = response.body().getRows();

                            for (News.RowsItem info : news) {

                                // check conditon the isExistTodb

                                mDatabase.open();
                                mDatabase.addNews(info);
                                Log.d(TAG,info.toString());
                                mDatabase.close();
                                infoList.add(info);

                            }
                            intAdapter();

                        } else if (responseCode == AppGlobalTags.GlobalTag.TOKEN_ERROR) {
                            Toast.makeText(getActivity(), "token", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), " error", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable throwable) {
                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_LONG).show();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void intAdapter() {
        try {
            if (infoList != null && infoList.size() > 0){
                HomeAdapter mAdapter = new HomeAdapter(getActivity(), infoList);
                mListView.setAdapter(mAdapter);
            }else {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // check network connection

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
           // Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(getActivity(), " Offline Mode ", Toast.LENGTH_LONG).show();

            return false;
        }
        return false;
    }

    class OfflineNewsAsync extends AsyncTask<String, News.RowsItem, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            infoList = new ArrayList<News.RowsItem>();
            infoList.clear();
        }

        protected Void doInBackground(String... params) {

            mDatabase.open();
            infoList = mDatabase.getAllNewsRow();
            mDatabase.close();
            return null;
        }

        @Override
        protected void onProgressUpdate(News.RowsItem... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // mDatabase.close();
            Log.d("DATA List in row", "" + infoList);
            if (infoList != null) {
                if (infoList.size() > 0) {
                    mAdapter = new HomeAdapter(getActivity(), infoList);
                    mListView.setAdapter(mAdapter);
                }

            }

        }
    }
}