package com.vigorous.network;

import com.vigorous.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lxm.
 */

public interface ForumService {

    //TODO 同步 异步 (like okhttp call & enqueue)

    @GET("list/{boardid}/{index}")
    Call<List<Post>> getPostList(@Path("boardid") String boardid, @Path("index") int index);
}
