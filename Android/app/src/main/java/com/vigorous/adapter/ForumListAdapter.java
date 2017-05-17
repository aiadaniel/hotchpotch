package com.vigorous.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vigorous.MyApp;
import com.vigorous.R;
import com.vigorous.entity.Post;
import com.vigorous.utils.Constant;

import java.util.List;

/**
 * Created by lxm.
 */

public class ForumListAdapter extends RecyclerView.Adapter {
    private static final String TAG = ForumListAdapter.class.getSimpleName();
    public static final int VIEW_NORMAL = 1;
    public static final int VIEW_FOOTER = 2;
    private List<Post> mPostList;
    private boolean mIsShowFooter = false;

    public ForumListAdapter(List<Post> postList) {
        mPostList = postList;
    }

    public void setPostList(List<Post> postList) {
        mPostList = postList;
        notifyDataSetChanged();
    }

    public void addPostList(List<Post> postList) {
        int oldposition = mPostList.size();
        mPostList.addAll(postList);
        notifyItemRangeInserted(oldposition,mPostList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_NORMAL:
                View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
                ItemViewHolder holder = new ItemViewHolder(rootview);
                return holder;
            case VIEW_FOOTER:
                View footview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer,parent,false);
                FooterViewHolder footholder = new FooterViewHolder(footview);
                return footholder;
            default:
                throw new RuntimeException("error view type for adapter");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooter(position))
            return VIEW_FOOTER;
        return VIEW_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder)
            setDataToView((ItemViewHolder) holder,position);
    }

    private void setDataToView(ItemViewHolder holder,int position) {
        Post post = mPostList.get(position);
        holder.mTvTitle.setText(post.getTitle());
        holder.mTvContent.setText(post.getContent());
        holder.mTvNickname.setText(post.getAuthor().getNickname());
        if (post.getAuthor().getAvatar() != null && !"null".equals(post.getAuthor().getAvatar())) {
            Log.d(TAG,"==will down "+ post.getAuthor().getAvatar());
            Glide.with(MyApp.getMyAppContext()).load(Constant.HOTCH_FILE+"down?filePath="+post.getAuthor().getAvatar())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(holder.mImageView);
        } else {
            holder.mImageView.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        if (mPostList == null)
            return 0;
        return mIsShowFooter?mPostList.size()+1:mPostList.size();
    }

    private boolean isFooter(int position){
        return (getItemCount()-1 == position);
    }

    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    public void hideFooter() {
        if (!mIsShowFooter) return;
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvNickname;
        TextView mTvTitle;
        TextView mTvContent;
        ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvNickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
