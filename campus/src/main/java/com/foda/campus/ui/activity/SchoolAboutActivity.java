package com.foda.campus.ui.activity;


import android.os.Bundle;

import com.foda.campus.R;

public class SchoolAboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_about);

        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_school_about;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

    }
}
