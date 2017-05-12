package com.vigorous.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigorous.R;
import com.vigorous.entity.Post;

import java.util.List;

/**
 * Created by lxm.
 */

public class ForumListAdapter extends RecyclerView.Adapter {
    private List<Post> mPostList;

    public ForumListAdapter(List<Post> postList) {
        mPostList = postList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        ItemViewHolder holder = new ItemViewHolder(rootview);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setDataToView((ItemViewHolder) holder,position);
    }

    private void setDataToView(ItemViewHolder holder,int position) {
        Post post = mPostList.get(position);
        holder.mTvTitle.setText(post.getTitle());
        holder.mTvContent.setText(post.getContent());
        holder.mTvNickname.setText(post.getUser().getNickname());
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvNickname;
        TextView mTvTitle;
        TextView mTvContent;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvNickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
