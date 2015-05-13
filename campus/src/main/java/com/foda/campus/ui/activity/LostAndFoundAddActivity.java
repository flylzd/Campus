package com.foda.campus.ui.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;
import com.foda.campus.R;
import com.foda.campus.app.ApiClient;
import com.foda.campus.model.LostAndFound;
import com.foda.campus.volley.ResponseListener;

public class LostAndFoundAddActivity extends BaseActivity {

    private static final String TAG = "LostAndFoundAddActivity";

    private RadioGroup rgLostAndFound;
    private TextView etTitle;
    private TextView etContent;
    private String title;
    private String content;
    private int type = 0;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_add);

        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_lost_and_found;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

        rgLostAndFound = (RadioGroup) findViewById(R.id.rgLostAndFound);
        etTitle = (TextView) findViewById(R.id.etTitle);
        etContent = (TextView) findViewById(R.id.etContent);

        rgLostAndFound.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbLost:
                        type = 0;
                        break;
                    case R.id.rbFound:
                        type = 1;
                        break;
                }
            }
        });

        etTitle = (TextView) findViewById(R.id.etTitle);
        etContent = (TextView) findViewById(R.id.etContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lost_and_found_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionSend) {
            saveLostAndFound();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveLostAndFound() {

        title = etTitle.getText().toString();
        content = etContent.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            Toast.makeText(LostAndFoundAddActivity.this, "标题或内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.saveLostAndFound(TAG, type, title, content, new ResponseListener() {
            @Override
            public void onStarted() {
                progressDialog = ProgressDialog.show(LostAndFoundAddActivity.this, null,"正在提交数据...");
            }

            @Override
            public void onResponse(Object response) {
                progressDialog.dismiss();

                LostAndFound bean = (LostAndFound) response;
                if (bean.code == 200) {
                    finish();
                } else {
                    Toast.makeText(LostAndFoundAddActivity.this, "发布失败",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
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
