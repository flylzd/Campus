package com.foda.campus.util;


import android.content.Context;
import android.content.Intent;

import com.foda.campus.ui.activity.FastfoodActivity;
import com.foda.campus.ui.activity.FastfoodMenuActivity;
import com.foda.campus.ui.activity.LibraryActivity;
import com.foda.campus.ui.activity.LostAndFoundActivity;
import com.foda.campus.ui.activity.LostAndFoundAddActivity;
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

    public static void showLostAndFound(Context context) {
        Intent intent = new Intent(context, LostAndFoundActivity.class);
        context.startActivity(intent);
    }

    public static void showLostAndFoundAdd(Context context) {
        Intent intent = new Intent(context, LostAndFoundAddActivity.class);
        context.startActivity(intent);
    }

    public static void showFastfood(Context context) {
        Intent intent = new Intent(context, FastfoodActivity.class);
        context.startActivity(intent);
    }

    public static void showFastfoodMenu(Context context) {
        Intent intent = new Intent(context, FastfoodMenuActivity.class);
        context.startActivity(intent);
    }

    public static void showLibrary(Context context) {
        Intent intent = new Intent(context, LibraryActivity.class);
        context.startActivity(intent);
    }
}
