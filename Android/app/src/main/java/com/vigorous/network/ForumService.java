package com.vigorous.network;

import com.vigorous.entity.Post;
import com.vigorous.entity.Reply;
import com.vigorous.entity.ResultModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lxm.
 */

public interface ForumService {

    //TODO 同步 异步 (like okhttp call & enqueue)

    @GET("list/{boardid}/{index}/{lastid}")
    Call<List<Post>> getPostList(@Path("boardid") String boardid, @Path("index") int index,@Path("lastid") int lastid);

    //==============================================================================================
    @GET("list/{postid}")
    Call<List<Reply>> getReplyList(@Path("postid") String postid);


    //==============================================================================================
    @FormUrlEncoded
    @POST("regist")
    Call<ResultModel> regist(@Field("nickname") String nickname,@Field("password") String pass);

    @POST("login/{nickname}/{pass}")
    Call<ResultModel> login(@Path("nickname")String name, @Path("pass")String pass);

    @POST("loginbytoken/{token}")
    Call<ResponseBody> loginbytoken(@Path("token")String token);
}
