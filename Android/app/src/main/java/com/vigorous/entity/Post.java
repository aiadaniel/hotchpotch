package com.vigorous.entity;

/**
 * Created by lxm.
 */

public class Post {
    String postid;
    String title;
    String content;
    User author;
    User authorLastReplied;
    int prise;
    int down;
    int replyCount;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
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
}
