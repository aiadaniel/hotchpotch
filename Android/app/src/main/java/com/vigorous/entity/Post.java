package com.vigorous.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxm.
 */

public class Post implements Parcelable {
    String id;
    String title;
    String content;
    User author;
    User authorLastReplied;
    int prise;
    int down;
    int replyCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthorLastReplied() {
        return authorLastReplied;
    }

    public void setAuthorLastReplied(User authorLastReplied) {
        this.authorLastReplied = authorLastReplied;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    protected Post(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        author = in.readParcelable(User.class.getClassLoader());
        authorLastReplied = in.readParcelable(User.class.getClassLoader());
        prise = in.readInt();
        down = in.readInt();
        replyCount = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeParcelable(author,flags);
        dest.writeParcelable(authorLastReplied,flags);
        dest.writeInt(prise);
        dest.writeInt(down);
        dest.writeInt(replyCount);
    }
}
