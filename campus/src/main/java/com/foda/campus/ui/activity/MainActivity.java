package com.foda.campus.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.foda.campus.R;
import com.foda.campus.util.UIHelper;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private View layoutHomeNews;
    private View layoutHomeAbout;
    private View layoutHomeBeauty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        layoutHomeNews = findViewById(R.id.layoutHomeNews);
        layoutHomeAbout = findViewById(R.id.layoutHomeAbout);
        layoutHomeBeauty = findViewById(R.id.layoutHomeBeauty);

        layoutHomeNews.setOnClickListener(this);
        layoutHomeAbout.setOnClickListener(this);
        layoutHomeBeauty.setOnClickListener(this);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected int getActionBarTitle() {
        return R.string.title_main;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutHomeNews:
                UIHelper.showNews(MainActivity.this);
                break;
            case R.id.layoutHomeAbout:
                UIHelper.showSchoolAbout(MainActivity.this);
                break;
            case R.id.layoutHomeBeauty:
                UIHelper.showSchoolBeauty(MainActivity.this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Log.e(TAG, "exit application");
//            this.finish();
            System.exit(0);
        }
    }


}
