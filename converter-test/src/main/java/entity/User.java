package entity;

import java.util.List;

/**
 * @author xiayc
 * @date 2019/3/14
 */
public class User {
    private long id;
    private String name;
    private List<Integer> askIdList;
    private List<Long> commentIdList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAskIdList() {
        return askIdList;
    }

    public void setAskIdList(List<Integer> askIdList) {
        this.askIdList = askIdList;
    }

    public List<Long> getCommentIdList() {
        return commentIdList;
    }

    public void setCommentIdList(List<Long> commentIdList) {
        this.commentIdList = commentIdList;
    }
}
