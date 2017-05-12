package com.vigorous.entity;

/**
 * Created by lxm.
 */

public class Board {
    String boardid;
    String boardName;

    public Board(String boardid, String boardName) {
        this.boardid = boardid;
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }
}
