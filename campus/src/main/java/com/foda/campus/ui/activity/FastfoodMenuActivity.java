package com.foda.campus.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.foda.campus.R;
import com.foda.campus.view.EndOfListView;
import com.lemon.aklib.adapter.BaseAdapterHelper;
import com.lemon.aklib.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;


public class FastfoodMenuActivity extends BaseActivity {

    private EndOfListView listView;
    private QuickAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>(20);

    private Button btnAbout;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_menu);

        initView();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_fastfood_menu;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initView() {

        dataList.add("一家人汤饭");
        dataList.add("桂林米粉");
        dataList.add("木桶饭");
        dataList.add("隆江猪脚");
        dataList.add("电饭煲");
        dataList.add("大口九");
        dataList.add("菠萝蜜");
        dataList.add("一家人汤饭");
        dataList.add("一家人汤饭");

        adapter = new QuickAdapter<String>(FastfoodMenuActivity.this, R.layout.listitem_fastfood_menu) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
//                helper.setText(R.id.tvTitle, item);
            }
        };

        adapter.replaceAll(dataList);
        listView = (EndOfListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + btnCall.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
