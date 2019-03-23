package entity;

import java.util.List;
import java.util.Map;

/**
 * @author xiayc
 * @date 2019/3/14
 */
public class UserInfo {
    private Long id;
    private String name;
    private List<Integer> askIdList;
    private List<Integer> commentIdList;
    private Map<String, String> logMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Integer> getCommentIdList() {
        return commentIdList;
    }

    public void setCommentIdList(List<Integer> commentIdList) {
        this.commentIdList = commentIdList;
    }

    public Map<String, String> getLogMap() {
        return logMap;
    }

    public void setLogMap(Map<String, String> logMap) {
        this.logMap = logMap;
    }
}
