package com.vigorous.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vigorous.BaseFragment;
import com.vigorous.R;
import com.vigorous.adapter.ForumListAdapter;
import com.vigorous.entity.Post;
import com.vigorous.network.Retrofit2Mgr;
import com.vigorous.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxm.
 */

public class ForumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = ForumFragment.class.getSimpleName();
    public static final String BUNDLE_BOARDID = "boardid";
    public static final String BUNDLE_BOARDNAME = "boardname";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mEmptyView;
    private ProgressBar mProgressBar;
    private ForumListAdapter mForumListAdapter;
    private String mBoardId;
    private String mBoardName;
    private int mStartIndex = 0;
    private boolean mAllLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBoardId = bundle.getString(BUNDLE_BOARDID,"-1");
            mBoardName = bundle.getString(BUNDLE_BOARDNAME,"");
            Log.d(TAG,"success get board " + mBoardId + ":" + mBoardName);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_forum,container,false);
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.forum_rv);
            mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);
            mEmptyView = (TextView) mRootView.findViewById(R.id.empty_view);
            mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
            initView();
        }
        return mRootView;
    }

    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    loadData();
                    mForumListAdapter.showFooter();
                    mRecyclerView.scrollToPosition(mForumListAdapter.getItemCount() - 1);
                }
            }
        });
        //// TODO: 2017/5/12
        List<Post> posts = new ArrayList<>();
        mForumListAdapter = new ForumListAdapter(posts);
        mRecyclerView.setAdapter(mForumListAdapter);
        loadData();
    }

    private void loadMore() {

    }

    private void loadData() {
        //OkUtils.okAsyncGet();
        try {
            Retrofit2Mgr.getInstance(Constant.HOTCH_POST).getPostList(mBoardId, mStartIndex, new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    Log.d(TAG,"post list res code "+ response.code());
                    mForumListAdapter.hideFooter();
                    List<Post> posts = response.body();
                    if (response.code() == 200 && ( posts == null || posts.size()==0) ) {
                        mAllLoaded = true;
                    }
                    if (posts != null && (posts.size() > 0) ) {
                        mForumListAdapter.addPostList(posts);
                        mStartIndex += posts.size();
                    }

                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {//顶部下拉刷新
        Log.d(TAG,"forumfragment onRefresh");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
