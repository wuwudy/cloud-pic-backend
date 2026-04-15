package com.f1sh.cloudpicbackend.common;

import lombok.Data;

/**
 * 通用分页请求
 */
@Data
public class PageRequest {
    /**
     * 当前页号 默认 1
     */
    private int current = 1;

    /**
     * 页面大小 默认 10
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序 ascend(升序), descend(降序) 默认 descend
     */
    private String sortOrder = "descend";
}
