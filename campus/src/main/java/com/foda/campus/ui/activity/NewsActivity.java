package com.foda.campus.ui.activity;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.RadioGroup;

import com.android.volley.error.VolleyError;
import com.foda.campus.R;
import com.foda.campus.app.ApiClient;
import com.foda.campus.model.NewsData;
import com.foda.campus.view.EndOfListView;
import com.foda.campus.view.PMSwipeRefreshLayout;
import com.foda.campus.volley.ResponseListener;
import com.lemon.aklib.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, EndOfListView.OnEndOfListListener {


    private static final String TAG = "NewsActivity";

    private PMSwipeRefreshLayout pullRefreshLayout;
    private EndOfListView listView;
    private QuickAdapter<NewsData> adapter;
    private List<NewsData> dataList = new ArrayList<NewsData>();

    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();

        ApiClient.getNewsList(TAG,new ResponseListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onResponse(Object response) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }


    @Override
    protected int getActionBarTitle() {
        return R.string.title_news;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {


    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onEndOfList(Object lastItem) {

    }

    private void showIndeterminateProgress(boolean visibility) {
        adapter.showIndeterminateProgress(visibility);
    }


}
