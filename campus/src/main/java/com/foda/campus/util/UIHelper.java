package com.foda.campus.util;


import android.content.Context;
import android.content.Intent;

import com.foda.campus.ui.activity.NewsActivity;

public class UIHelper {

    public static void showNews(Context context) {
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }
}
