package org.jc.framework.converter.entity;

import org.jc.framework.converter.annotation.Properties;

import java.util.List;
import java.util.Map;

/**
 * @author xiayc
 * @date 2019/3/14
 */
public class User {
    @Properties(defaultValue = "${100}")
    private long id;
    private String name;
    private List<Integer> askIdList;
    private List<Long> commentIdList;
    private Map<String, String> logMap;

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

    public Map<String, String> getLogMap() {
        return logMap;
    }

    public void setLogMap(Map<String, String> logMap) {
        this.logMap = logMap;
    }
}
