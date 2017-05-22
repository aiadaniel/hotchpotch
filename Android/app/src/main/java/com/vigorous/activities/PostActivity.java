package com.vigorous.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vigorous.MyApp;
import com.vigorous.R;
import com.vigorous.adapter.PostReplyAdapter;
import com.vigorous.entity.Post;
import com.vigorous.entity.Reply;
import com.vigorous.network.Retrofit2Mgr;
import com.vigorous.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends BaseActivity {
    private static final String TAG = PostActivity.class.getSimpleName();

    public static final String POST_EXTRA = "post";

    ImageView mIvAvatar;
    TextView mTvNickname;
    TextView mTvTitle;
    TextView mTvContent;
    ImageView mIvContent;
    TextView mTvShare;
    TextView mTvReply;
    ImageView mIvPrise;
    ImageView mIvDown;
    TextView mTvPriseNum;
    RecyclerView mRvReply;
    PostReplyAdapter mPostReplyAdapter;
    
    Post mPost;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvNickname = (TextView) findViewById(R.id.tv_username);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvContent = (ImageView) findViewById(R.id.iv_content);
        mTvShare = (TextView) findViewById(R.id.tv_share);
        mTvReply = (TextView) findViewById(R.id.tv_reply);
        mIvPrise = (ImageView) findViewById(R.id.iv_prise);
        mIvDown = (ImageView) findViewById(R.id.iv_down);
        mTvPriseNum = (TextView) findViewById(R.id.tv_prise_num);
        mRvReply = (RecyclerView) findViewById(R.id.rv_reply);
        mPost = getIntent().getParcelableExtra(POST_EXTRA);
        if (mPost != null) {
            initPostView();
            initRecycleView();
            Log.d(TAG,"get post " + mPost.getId() + " " + mPost.getAuthor());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initPostView() {
        mTvNickname.setText(mPost.getAuthor().getNickname());
        if (mPost.getAuthor().getAvatar() != null && !"null".equals(mPost.getAuthor().getAvatar())) {
            Log.d(TAG,"==will down "+ mPost.getAuthor().getAvatar());
            Glide.with(MyApp.getMyAppContext()).load(Constant.HOTCH_FILE+"down?filePath="+mPost.getAuthor().getAvatar())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(mIvAvatar);
        } else {
            mIvAvatar.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }
        mTvTitle.setText(mPost.getTitle());
        mTvContent.setText(mPost.getContent());
        int bound = 45;
        Drawable shareLeftDrawable = mTvShare.getCompoundDrawables()[0];
        shareLeftDrawable.setBounds(0,0,bound,bound);
        mTvShare.setText(""+999);
        Drawable replyLeftDrawable = mTvReply.getCompoundDrawables()[0];
        replyLeftDrawable.setBounds(0,0,bound,bound);
        mTvReply.setText(""+15);
        mTvPriseNum.setText(""+8267);
    }

    //todo 取消分割线
    private void initRecycleView() {
        mRvReply.setHasFixedSize(false);
        mRvReply.setLayoutManager(new LinearLayoutManager(PostActivity.this,LinearLayoutManager.VERTICAL,false));
        mRvReply.setItemAnimator(new DefaultItemAnimator());
        mRvReply.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //// TODO: 2017/5/19
        List<Reply> replies = new ArrayList<>();
        mPostReplyAdapter = new PostReplyAdapter(replies);
        mRvReply.setAdapter(mPostReplyAdapter);
        loadData();
    }

    private void loadData() {
        Log.d(TAG,"postid " + mPost.getId());
        Retrofit2Mgr.getInstance(Constant.HOTCH_REPLY).getReplyList(mPost.getId(), new Callback<List<Reply>>() {
            @Override
            public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                Log.d(TAG,"get reply list " + response.code());
                List<Reply> replies = response.body();
                mPostReplyAdapter.setReplyList(replies);
            }

            @Override
            public void onFailure(Call<List<Reply>> call, Throwable t) {

            }
        });
    }
}
