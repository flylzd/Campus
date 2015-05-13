package com.foda.campus.app;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.error.VolleyErrorHelper;
import com.foda.campus.constant.Urls;
import com.foda.campus.model.LostAndFound;
import com.foda.campus.model.News;
import com.foda.campus.volley.GsonGetRequest;
import com.foda.campus.volley.GsonPostRequest;
import com.foda.campus.volley.ResponseListener;
import com.foda.campus.volley.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    public static final String TAG = "ApiClient";
    public static final RequestQueue requestQueue = VolleySingleton.getRequestQueue();

    /**
     * 获取新闻列表
     */
    public static void getNewsList(String tag, int page, String typeName, ResponseListener listener) {

        listener.onStarted();

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("page", String.valueOf(page));
        requestParams.put("limit", "10");
        requestParams.put("typeName", typeName);
//        requestParams.put("sort", "id");
//        requestParams.put("dir", "ASC");

        String url = Urls.ACTION_URL + "findNewsList.action";
        GsonGetRequest request = createGsonGetRequest(url, requestParams, News.class, listener);
        request.setTag(tag);
        requestQueue.add(request);
    }


    /**
     * 获取失物招领
     */
    public static void findLostAndFound(String tag, int page,  ResponseListener listener) {

        listener.onStarted();

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("page", String.valueOf(page));
        requestParams.put("limit", "10");

//        requestParams.put("sort", "id");
//        requestParams.put("dir", "ASC");

        String url = Urls.ACTION_URL + "findLostAndFound.action";
        GsonGetRequest request = createGsonGetRequest(url, requestParams, LostAndFound.class, listener);
        request.setTag(tag);
        requestQueue.add(request);
    }

    /**
     * 获取失物招领
     */
    public static void findOne(String tag, String id,  ResponseListener listener) {

        listener.onStarted();

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("id", id);

        String url = Urls.ACTION_URL + "findOne.action";
        GsonGetRequest request = createGsonGetRequest(url, requestParams, LostAndFound.class, listener);
        request.setTag(tag);
        requestQueue.add(request);
    }

    public static void saveLostAndFound(String tag, int type, String title, String content,  ResponseListener listener) {

        listener.onStarted();

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("type", String.valueOf(type));
        requestParams.put("title", title);
        requestParams.put("content", content);

        String url = Urls.ACTION_URL + "saveLostAndFound.action";
        GsonGetRequest request = createGsonGetRequest(url, requestParams, LostAndFound.class, listener);
        request.setTag(tag);
        requestQueue.add(request);
    }

    public static void sendLostAndFound(String tag, String id,  String content,  ResponseListener listener) {

        listener.onStarted();

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("id", id);
        requestParams.put("content", content);

        String url = Urls.ACTION_URL + "sendLostAndFound.action";
        GsonGetRequest request = createGsonGetRequest(url, requestParams, LostAndFound.class, listener);
        request.setTag(tag);
        requestQueue.add(request);
    }


    private static <T> GsonPostRequest createGsonPostRequest(String url, Map<String, String> requestParamsMap, Class<T> clazz, final ResponseListener listener) {

        GsonPostRequest request = new GsonPostRequest(url, clazz, requestParamsMap, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Log.d(TAG, "onResponse = " + response.toString());
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "volleyError = " + volleyError.toString());
                Toast.makeText(AppContext.getInstance().getApplicationContext(), VolleyErrorHelper.getMessage(volleyError, AppContext.getInstance()), Toast.LENGTH_SHORT).show();
                listener.onErrorResponse(volleyError);
            }
        });

        try {
            Log.d(TAG, "request Headers = " + request.getHeaders().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        String params = requestParamsMap.toString().substring(1, requestParamsMap.toString().length() - 1);
        Log.d(TAG, "request Url-Params = " + request.getUrl() + "?" + params);

        return request;
    }



    private static <T> GsonGetRequest createGsonGetRequest(String url, Map<String, String> requestParamsMap, Class<T> clazz, final ResponseListener listener) {

        GsonGetRequest request = new GsonGetRequest(url, clazz, requestParamsMap, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Log.d(TAG, "onResponse = " + response.toString());
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "volleyError = " + volleyError.toString());
                listener.onErrorResponse(volleyError);
            }
        });

        try {
            Log.d(TAG, "request Headers = " + request.getHeaders().toString());
            String params = requestParamsMap.toString().substring(1, requestParamsMap.toString().length() - 1);
            //        Log.d(TAG, "request Url-Params = " + request.getUrl() + "?" + params);
            Log.d(TAG, "request Url-Params = " + request.getUrl());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        return request;
    }
}
