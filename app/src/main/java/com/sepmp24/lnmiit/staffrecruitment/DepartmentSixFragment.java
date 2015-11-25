package com.sepmp24.lnmiit.staffrecruitment;


import android.content.Intent;
import  android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nikunj on 04-11-2015.
 */
public class DepartmentSixFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener  {

    // all required variables
    private static final String VACANCY_URL = "http://172.22.47.97/sepmp24/all_vacancies.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_VACANCY_ID = "vacancy_id";
    private static final String TAG_VACANCY_TITLE = "vacancy_title";
    private static final String TAG_VACANCY_DEP_NO = "vacancy_dep_no";
    private static final String TAG_VACANCY_ACT_DATE = "vacancy_act_date";
    private static final String TAG_VACANCY_DEADLINE = "vacancy_deadline";
    private static final String TAG_VACANCY_SEATS = "vacancy_seats";
    private static final String TAG_VACANCY_STATUS = "vacancy_status";
    private static final String TAG_VACANCY_DES = "vacancy_des";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_CANDIDATE_ID = "cand_id";
    private JSONArray mVacancies = null;
    private ArrayList<HashMap<String, String>> mVacanciesList;

    SwipeRefreshLayout mSwipeRefreshLayout;

    //basic constructor
    public DepartmentSixFragment() {
    }

    //this fragment will return this view
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dep_one_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        //do whatever you want to do with the rootView here (:
        setUpFragment();



        return rootView;
    }

    @Override
    public void onRefresh() {
        setUpFragment();
    }

    public void setUpFragment() {

        updateJSONdata();
        return;

    }


    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata() {


        mVacanciesList = new ArrayList<HashMap<String, String>>();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("dep_no", 6);
        String result = null;

        client.post(VACANCY_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("VacanciesDownload", "request : starting");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("Response : " + response.toString());

                try {
                    mVacancies = response.getJSONArray("data");
                    ;
                    for (int i = 0; i < mVacancies.length(); i++) {
                        System.out.println("Response Array : " + mVacancies.toString());
                        HashMap<String, String> map = new HashMap<String, String>();
                        //downloading a vacancy from server
                        JSONObject j = mVacancies.getJSONObject(i);
                        map.put(TAG_VACANCY_ID, j.getString(TAG_VACANCY_ID));
                        map.put(TAG_VACANCY_TITLE, j.getString(TAG_VACANCY_TITLE));
                        map.put(TAG_VACANCY_DEP_NO, j.getString(TAG_VACANCY_DEP_NO));
                        map.put(TAG_VACANCY_ACT_DATE, j.getString(TAG_VACANCY_ACT_DATE));
                        map.put(TAG_VACANCY_DEADLINE, j.getString(TAG_VACANCY_DEADLINE));
                        map.put(TAG_VACANCY_SEATS, j.getString(TAG_VACANCY_SEATS));
                        map.put(TAG_VACANCY_STATUS, j.getString(TAG_VACANCY_STATUS));
                        map.put(TAG_VACANCY_DES, j.getString(TAG_VACANCY_DES));
                        //adding this vacancy to List
                        mVacanciesList.add(map);


                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                finally {
                    updateList();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("Response : " + response.toString());


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("on fail");

            }


            @Override
            public void onFinish() {
                super.onFinish();
                System.out.println("on finish");
            }
        });





    }
    public void updateList() {

        System.out.println("Response : " + mVacanciesList.toString());
        ListAdapter adapter = new SimpleAdapter(getActivity(), mVacanciesList,
                R.layout.vacancy_cardview, new String[]{TAG_VACANCY_TITLE,
                TAG_VACANCY_SEATS,TAG_VACANCY_DEADLINE}, new int[]{R.id.titledb,
                R.id.seatsdb,R.id.deadlinedb});

        // I shouldn't have to comment on this one:
        setListAdapter(adapter);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(), ApplyForVacancyActivity.class);
                HashMap<String, String> item = mVacanciesList.get(position);
                intent.putExtra(TAG_VACANCY_ID, item.get(TAG_VACANCY_ID).toString());
                intent.putExtra(TAG_VACANCY_TITLE, item.get(TAG_VACANCY_TITLE).toString());
                intent.putExtra(TAG_VACANCY_DEP_NO, item.get(TAG_VACANCY_DEP_NO).toString());
                intent.putExtra(TAG_VACANCY_ACT_DATE, item.get(TAG_VACANCY_ACT_DATE).toString());
                intent.putExtra(TAG_VACANCY_DEADLINE, item.get(TAG_VACANCY_DEADLINE).toString());
                intent.putExtra(TAG_VACANCY_SEATS, item.get(TAG_VACANCY_SEATS).toString());
                intent.putExtra(TAG_VACANCY_STATUS, item.get(TAG_VACANCY_STATUS).toString());
                intent.putExtra(TAG_VACANCY_DES, item.get(TAG_VACANCY_DES).toString());
                getActivity().startActivity(intent);
            }
        });
    }
}
