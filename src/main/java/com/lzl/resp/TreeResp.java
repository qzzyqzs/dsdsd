package com.lzl.resp;

import lombok.Data;

import java.util.List;

@Data
public class TreeResp<T> extends Resp {
    private T data;
    private List list;
}
