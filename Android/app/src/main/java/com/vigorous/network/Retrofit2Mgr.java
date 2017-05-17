package com.vigorous.network;

import com.vigorous.MyApp;
import com.vigorous.entity.Post;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lxm.
 */

public class Retrofit2Mgr {

    private static final String TAG = Retrofit2Mgr.class.getSimpleName();

    private static volatile OkHttpClient sOkHttpClient;

    private ForumService mForumService;

    private static HashMap<String,Retrofit2Mgr> sRetrofit2Mgr = new HashMap<>();

    public static Retrofit2Mgr getInstance(String url) {
        Retrofit2Mgr mgr = sRetrofit2Mgr.get(url);
        if (mgr == null) {
            mgr = new Retrofit2Mgr(url);
            sRetrofit2Mgr.put(url,mgr);
            return mgr;
        }
        return mgr;
    }

    public Retrofit2Mgr(String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(getOkClient()).addConverterFactory(GsonConverterFactory.create()).build();
        mForumService = retrofit.create(ForumService.class);
    }

    private OkHttpClient getOkClient() {
        if (sOkHttpClient == null) {
            synchronized (Retrofit2Mgr.class) {
                if (sOkHttpClient == null) {
                    Cache cache = new Cache(new File(MyApp.getMyAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    sOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .build();//TODO: Add inteceptor for cache controller
                }
            }
        }
        return sOkHttpClient;
    }

    //==============================================================================================
    public void getPostList(String boardid, int index, Callback<List<Post>> cb) throws IOException {
        Call<List<Post>> call = mForumService.getPostList(boardid,index);
        call.enqueue(cb);
    }
}
