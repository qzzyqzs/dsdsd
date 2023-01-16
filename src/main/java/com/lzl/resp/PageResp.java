package com.lzl.resp;

import lombok.Data;

import java.util.List;

@Data
public class PageResp<T> extends Resp {
    private T data;
    private Long count;
}
