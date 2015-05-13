package com.foda.campus.ui.activity;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.android.volley.error.VolleyError;
import com.foda.campus.R;
import com.foda.campus.app.ApiClient;
import com.foda.campus.model.LostAndFound;
import com.foda.campus.model.LostAndFoundData;
import com.foda.campus.util.UIHelper;
import com.foda.campus.view.EndOfListView;
import com.foda.campus.volley.ResponseListener;
import com.lemon.aklib.adapter.BaseAdapterHelper;
import com.lemon.aklib.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class LostAndFoundActivity extends BaseActivity implements EndOfListView.OnEndOfListListener {

    private static final String TAG = "LostAndFoundActivity";

    private EndOfListView listView;
    private QuickAdapter<LostAndFoundData> adapter;
    private List<LostAndFoundData> dataList = new ArrayList<LostAndFoundData>();

    private int page = 1;

    private boolean isFirstLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        page = 1;
        findLostAndFound();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_lost_and_found;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lost_and_found, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionAdd) {
            UIHelper.showLostAndFoundAdd(LostAndFoundActivity.this);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEndOfList(Object lastItem) {

        if (isFirstLoading) {
            adapter.showIndeterminateProgress(true);
            page++;
            if (dataList.size() > 7) {
                findLostAndFound();
            }
        }
    }

    private void showIndeterminateProgress(boolean visibility) {
        adapter.showIndeterminateProgress(visibility);
    }

    private void initView() {

        adapter = new QuickAdapter<LostAndFoundData>(LostAndFoundActivity.this, R.layout.listitem_lost_and_found) {
            @Override
            protected void convert(BaseAdapterHelper helper, LostAndFoundData item) {

                ImageView imgLostAndFound = helper.getView(R.id.imgLostAndFound);
                int type = item.type;
                if (type == 0) {
//                    tvTitle.setCompoundDrawables(getResources().getDrawable(R.drawable.lost), null, null, null);
                    imgLostAndFound.setImageResource(R.drawable.lost);
                } else {
//                    tvTitle.setCompoundDrawables(getResources().getDrawable(R.drawable.found), null, null, null);
                    imgLostAndFound.setImageResource(R.drawable.found);
                }
                helper.setText(R.id.tvTitle, item.title);
                helper.setText(R.id.tvTime, item.publishTime);
            }
        };

        listView = (EndOfListView) findViewById(R.id.listView);
        listView.setOnEndOfListListener(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.showLostAndFoundDetails(LostAndFoundActivity.this, adapter.getItem(position));
            }
        });
    }

    public void findLostAndFound() {

        ApiClient.findLostAndFound(TAG, page, new ResponseListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onResponse(Object response) {

                showIndeterminateProgress(false);

                List<LostAndFoundData> list = ((LostAndFound) response).items;
                if (page == 1) {
                    dataList = list;
                } else {
                    dataList.addAll(list);
                }
                adapter.replaceAll(dataList);

                isFirstLoading = true;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showIndeterminateProgress(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApiClient.requestQueue.cancelAll(TAG);
        ApiClient.requestQueue.getCache().clear();
    }
}

