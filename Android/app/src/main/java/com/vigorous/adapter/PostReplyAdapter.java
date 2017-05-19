package com.vigorous.adapter;

import android.graphics.drawable.Drawable;
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
import com.vigorous.entity.Reply;
import com.vigorous.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by lxm.
 */

public class PostReplyAdapter extends RecyclerView.Adapter {

    private List<Reply> mReplyList = new ArrayList<>();

    public PostReplyAdapter(List<Reply> list) {
        this.mReplyList = list;
    }

    public void setReplyList(List<Reply> list) {
        this.mReplyList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_reply, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(rootview);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder)
            setDataToView((ItemViewHolder) holder,position);
    }

    private void setDataToView(ItemViewHolder holder,int position) {
        Reply reply = mReplyList.get(position);
        holder.mTvNickname.setText(reply.getAuthor().getNickname());
        if (reply.getAuthor().getAvatar() != null && !"null".equals(reply.getAuthor().getAvatar())) {
            Log.d(TAG,"==will down "+ reply.getAuthor().getAvatar());
            Glide.with(MyApp.getMyAppContext()).load(Constant.HOTCH_FILE+"down?filePath="+reply.getAuthor().getAvatar())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(holder.mIvAvatar);
        } else {
            holder.mIvAvatar.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }
        holder.mTvTitle.setText(reply.getTitle());
        holder.mTvContent.setText(reply.getContent());
        int bound = 45;
        Drawable shareLeftDrawable = holder.mTvShare.getCompoundDrawables()[0];
        shareLeftDrawable.setBounds(0,0,bound,bound);
        holder.mTvShare.setText(""+999);
        Drawable replyLeftDrawable = holder.mTvReply.getCompoundDrawables()[0];
        replyLeftDrawable.setBounds(0,0,bound,bound);
        holder.mTvReply.setText(""+15);
        holder.mTvPriseNum.setText(""+8267);
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            mTvNickname = (TextView) itemView.findViewById(R.id.tv_username);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mIvContent = (ImageView) itemView.findViewById(R.id.iv_content);
            mTvShare = (TextView) itemView.findViewById(R.id.tv_share);
            mTvReply = (TextView) itemView.findViewById(R.id.tv_reply);
            mIvPrise = (ImageView) itemView.findViewById(R.id.iv_prise);
            mIvDown = (ImageView) itemView.findViewById(R.id.iv_down);
            mTvPriseNum = (TextView) itemView.findViewById(R.id.tv_prise_num);
        }
    }
}
