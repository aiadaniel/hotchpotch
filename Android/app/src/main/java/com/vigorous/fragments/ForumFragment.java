package com.vigorous.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vigorous.BaseFragment;
import com.vigorous.R;
import com.vigorous.adapter.ForumListAdapter;
import com.vigorous.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxm.
 */

public class ForumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String BUNDLE_BOARDID = "boardid";
    public static final String BUNDLE_BOARDNAME = "boardname";

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mEmptyView;
    private ProgressBar mProgressBar;
    private ForumListAdapter mForumListAdapter;

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
            }
        });
        //// TODO: 2017/5/12
        List<Post> posts = new ArrayList<>();
        mForumListAdapter = new ForumListAdapter(posts);
        mRecyclerView.setAdapter(mForumListAdapter);
    }

    @Override
    public void onRefresh() {

    }
}
