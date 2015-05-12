package com.foda.campus.ui.activity;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foda.campus.R;

public class LostAndFoundAddActivity extends BaseActivity {

    private RadioGroup rgLostAndFound;
    private TextView etTitle;
    private TextView etContent;

    private int type = 0;

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

            Toast.makeText(LostAndFoundAddActivity.this, "fsdfda", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
