package com.eb.homecode.managersystem.vo;

import java.util.List;

public class UserParam {

    private Integer userId;

    // 资源列表
    private List<String> endpoint;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(List<String> endpoint) {
        this.endpoint = endpoint;
    }
}
