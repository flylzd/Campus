package com.foda.campus.ui.activity;


import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.foda.campus.R;
import com.foda.campus.model.NewsData;

public class NewsDetailsActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvContent;

    private NewsData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        if (getIntent().hasExtra("data")){
            data = (NewsData) getIntent().getSerializableExtra("data");
        }
        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_news_details;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);

        tvTitle.setText(data.title);
        tvContent.setText(Html.fromHtml(data.content));
    }
}

