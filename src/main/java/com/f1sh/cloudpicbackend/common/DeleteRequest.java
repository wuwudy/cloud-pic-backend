package com.f1sh.cloudpicbackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * 删除的ID
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
