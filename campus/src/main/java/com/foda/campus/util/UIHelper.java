package com.foda.campus.util;


import android.content.Context;
import android.content.Intent;

import com.foda.campus.ui.activity.NewsActivity;
import com.foda.campus.ui.activity.SchoolAboutActivity;
import com.foda.campus.ui.activity.SchoolBeautyActivity;

public class UIHelper {

    public static void showNews(Context context) {
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }

    public static void showSchoolAbout(Context context) {
        Intent intent = new Intent(context, SchoolAboutActivity.class);
        context.startActivity(intent);
    }

    public static void showSchoolBeauty(Context context) {
        Intent intent = new Intent(context, SchoolBeautyActivity.class);
        context.startActivity(intent);
    }
}
