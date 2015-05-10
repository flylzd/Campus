package com.foda.campus.ui.activity;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.android.volley.error.VolleyError;
import com.foda.campus.R;
import com.foda.campus.app.ApiClient;
import com.foda.campus.model.News;
import com.foda.campus.model.NewsData;
import com.foda.campus.view.EndOfListView;
import com.foda.campus.view.PMSwipeRefreshLayout;
import com.foda.campus.volley.ResponseListener;
import com.lemon.aklib.adapter.BaseAdapterHelper;
import com.lemon.aklib.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, EndOfListView.OnEndOfListListener {

    private static final String TAG = "NewsActivity";

    private PMSwipeRefreshLayout pullRefreshLayout;
    private EndOfListView listView;
    private QuickAdapter<NewsData> adapter;
    private List<NewsData> dataList1 = new ArrayList<NewsData>();
    private List<NewsData> dataList2 = new ArrayList<NewsData>();
    private List<NewsData> dataList3 = new ArrayList<NewsData>();

    private RadioGroup radioGroup;

//    private final static String SEARCH_TYPE_1 = "热点关注";
//    private final static String SEARCH_TYPE_2 = "学校新闻";
//    private final static String SEARCH_TYPE_3 = "公告信息";

    private final static String SEARCH_TYPE_1 = "1";
    private final static String SEARCH_TYPE_2 = "2";
    private final static String SEARCH_TYPE_3 = "3";
    private String search = SEARCH_TYPE_1;


    private final static int DEFAULT_PAGE = 1;
    private int page1 = DEFAULT_PAGE;
    private int page2 = DEFAULT_PAGE;
    private int page3 = DEFAULT_PAGE;

    private boolean isFirstLoadingomplete1 = false;
    private boolean isFirstLoadingomplete2 = false;
    private boolean isFirstLoadingomplete3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();
        getNews1();
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

        adapter = new QuickAdapter<NewsData>(NewsActivity.this, R.layout.listitem_news) {
            @Override
            protected void convert(BaseAdapterHelper helper, NewsData item) {

                helper.setText(R.id.tvTitle, item.title);
                helper.setText(R.id.tvPublishTime, "发布时间：" + item.publishTime);
                helper.setText(R.id.tvPublisher, "发布人：" + item.publisher);
            }
        };

        pullRefreshLayout = (PMSwipeRefreshLayout) findViewById(R.id.pullRefreshLayout);
        pullRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        pullRefreshLayout.setOnRefreshListener(this);
        pullRefreshLayout.setRefreshing(true);
        listView = (EndOfListView) findViewById(R.id.listView);
        listView.setOnEndOfListListener(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbNews1:
                        if (!dataList1.isEmpty()) {
                            adapter.replaceAll(dataList1);
                        } else {
                            isFirstLoadingomplete1 = true;
                            search = SEARCH_TYPE_1;
                            getNews1();
                        }
                        break;
                    case R.id.rbNews2:
                        if (!dataList2.isEmpty()) {
                            adapter.replaceAll(dataList2);
                        } else {
                            isFirstLoadingomplete2 = true;
                            search = SEARCH_TYPE_2;
                            getNews2();
                        }
                        break;
                    case R.id.rbNews3:
                        if (!dataList3.isEmpty()) {
                            adapter.replaceAll(dataList3);
                        } else {
                            isFirstLoadingomplete3 = true;
                            search = SEARCH_TYPE_3;
                            getNews3();
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        if (search.equals(SEARCH_TYPE_1)) {
            isFirstLoadingomplete1 = false;
            page1 = DEFAULT_PAGE;
            getNews1();
        } else if (search.equals(SEARCH_TYPE_2)) {
            isFirstLoadingomplete2 = false;
            page2 = DEFAULT_PAGE;
            getNews2();

        } else if (search.equals(SEARCH_TYPE_3)) {
            isFirstLoadingomplete3 = false;
            page3 = DEFAULT_PAGE;
            getNews3();
        }
    }

    @Override
    public void onEndOfList(Object lastItem) {

        if (search.equals(SEARCH_TYPE_1)) {

            if (isFirstLoadingomplete1) {
                adapter.showIndeterminateProgress(true);
                if (dataList1.size() < 5) {
                    return;
                }
                page1++;
                getNews1();
            }
        } else if (search.equals(SEARCH_TYPE_2)) {
            if (isFirstLoadingomplete2) {
                adapter.showIndeterminateProgress(true);
                if (dataList2.size() < 5) {
                    return;
                }
                page2++;
                getNews2();
            }
        } else if (search.equals(SEARCH_TYPE_3)) {
            if (isFirstLoadingomplete3) {
                adapter.showIndeterminateProgress(true);
                if (dataList3.size() < 5) {
                    return;
                }
                page3++;
                getNews3();
            }
        }
    }

    private void showIndeterminateProgress(boolean visibility) {
        adapter.showIndeterminateProgress(visibility);
    }

    private void getNews1() {

        ApiClient.getNewsList(TAG, page1, search, new ResponseListener() {
            @Override
            public void onStarted() {
                if (page1 != DEFAULT_PAGE) {
                    return;
                }
                if (!pullRefreshLayout.isRefreshing()) {
                    pullRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onResponse(Object response) {
                if (page1 != DEFAULT_PAGE) {
                    showIndeterminateProgress(false);
                }
                pullRefreshLayout.setRefreshing(false);

                isFirstLoadingomplete1 = true;

                List<NewsData> list = ((News) response).items;
                if (page1 == DEFAULT_PAGE) {
                    dataList1 = list;
                } else {
                    dataList1.addAll(list);
                }
                adapter.replaceAll(dataList1);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showIndeterminateProgress(false);
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getNews2() {

        ApiClient.getNewsList(TAG, page2, search, new ResponseListener() {
            @Override
            public void onStarted() {
                if (page2 != DEFAULT_PAGE) {
                    return;
                }
                if (!pullRefreshLayout.isRefreshing()) {
                    pullRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onResponse(Object response) {
                if (page1 != DEFAULT_PAGE) {
                    showIndeterminateProgress(false);
                }
                pullRefreshLayout.setRefreshing(false);

                isFirstLoadingomplete2 = true;

                List<NewsData> list = ((News) response).items;
                if (page2 == DEFAULT_PAGE) {
                    dataList2 = list;
                } else {
                    dataList2.addAll(list);
                }
                adapter.replaceAll(dataList2);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showIndeterminateProgress(false);
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getNews3() {
        ApiClient.getNewsList(TAG, page3, search, new ResponseListener() {
            @Override
            public void onStarted() {
                if (page3 != DEFAULT_PAGE) {
                    return;
                }
                if (!pullRefreshLayout.isRefreshing()) {
                    pullRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onResponse(Object response) {
                if (page3 != DEFAULT_PAGE) {
                    showIndeterminateProgress(false);
                }
                pullRefreshLayout.setRefreshing(false);

                isFirstLoadingomplete3 = true;

                List<NewsData> list = ((News) response).items;
                if (page3 == DEFAULT_PAGE) {
                    dataList3 = list;
                } else {
                    dataList3.addAll(list);
                }
                adapter.replaceAll(dataList3);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showIndeterminateProgress(false);
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }


}
