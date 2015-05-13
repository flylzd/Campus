package com.foda.campus.ui.activity;


import android.os.Bundle;

import com.foda.campus.R;

public class LibraryDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_details);

        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_library_details;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

    }
}

