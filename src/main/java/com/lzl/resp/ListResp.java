package com.lzl.resp;

import lombok.Data;

@Data
public class ListResp<T> extends Resp {
    private T data;
    private Long count;
}
