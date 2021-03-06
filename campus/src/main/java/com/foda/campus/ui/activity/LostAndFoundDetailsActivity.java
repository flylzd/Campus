package com.foda.campus.ui.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.foda.campus.R;
import com.foda.campus.app.ApiClient;
import com.foda.campus.model.LostAndFound;
import com.foda.campus.model.LostAndFoundData;
import com.foda.campus.view.EndOfListView;
import com.foda.campus.volley.ResponseListener;
import com.lemon.aklib.adapter.BaseAdapterHelper;
import com.lemon.aklib.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class LostAndFoundDetailsActivity extends BaseActivity implements EndOfListView.OnEndOfListListener {

    private static final String TAG = "LostAndFoundDetailsActivity";

    private ListView listView;
    private QuickAdapter<LostAndFoundData> adapter;
    private List<LostAndFoundData> dataList = new ArrayList<LostAndFoundData>();

    private LostAndFoundData data;

    private int page = 1;
    private boolean isFirstLoading = false;

    private EditText etSend;
    private Button btnSend;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_details);

        if (getIntent().hasExtra("data")){
            data = (LostAndFoundData) getIntent().getSerializableExtra("data");
            setActionBarTitle(data.title);
        }
        initView();
        findLostAndFound();
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

        adapter = new QuickAdapter<LostAndFoundData>(LostAndFoundDetailsActivity.this, R.layout.listitem_lost_and_found_details) {
            @Override
            protected void convert(BaseAdapterHelper helper, LostAndFoundData item) {
                helper.setText(R.id.tvContent, item.content);
                helper.setText(R.id.tvTime, item.publishTime);
            }
        };

        listView = (ListView) findViewById(R.id.listView);
//        listView.setOnEndOfListListener(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        etSend = (EditText) findViewById(R.id.etSend);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send = etSend.getText().toString();

                if (TextUtils.isEmpty(send)) {
                    Toast.makeText(LostAndFoundDetailsActivity.this, "内容不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ApiClient.sendLostAndFound(TAG,data.id, send,new ResponseListener() {
                    @Override
                    public void onStarted() {
                        progressDialog = ProgressDialog.show(LostAndFoundDetailsActivity.this, null,"正在提交数据...");
                    }

                    @Override
                    public void onResponse(Object response) {
                        progressDialog.dismiss();

                        LostAndFound bean = (LostAndFound) response;
                        if (bean.code == 200) {
//                            finish();
                            etSend.getText().clear();
                            findLostAndFound();
                        } else {
                            Toast.makeText(LostAndFoundDetailsActivity.this, "发送失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void onEndOfList(Object lastItem) {

        if (isFirstLoading) {
            adapter.showIndeterminateProgress(true);
            page++;
            if (dataList.size()> 7) {
//                findLostAndFound();
            }
        }
    }

    private void showIndeterminateProgress(boolean visibility) {
        adapter.showIndeterminateProgress(visibility);
    }

    public void findLostAndFound() {

        ApiClient.findOne(TAG, data.id, new ResponseListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onResponse(Object response) {

                showIndeterminateProgress(false);

                List<LostAndFoundData> list = ((LostAndFound) response).items;
                dataList.clear();
                dataList.add(data);
                if (list != null && list.size() != 0) {
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

