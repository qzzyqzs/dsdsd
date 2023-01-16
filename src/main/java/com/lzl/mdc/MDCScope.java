package com.lzl.mdc;

import com.google.common.base.Preconditions;
import org.slf4j.MDC;

import java.util.Map;

public class MDCScope implements AutoCloseable {

    private final Map<String, String> map = MDC.getCopyOfContextMap();

    public MDCScope put(String key, String value) {
        Preconditions.checkNotNull(key, "key为空");
        if (value != null) {
            MDC.put(key, value);
        } else if (MDC.get(key) != null) {
            MDC.remove(key);
        }
        return this;
    }

    @Override
    public void close() throws Exception {
        if (this.map != null) {
            MDC.setContextMap(this.map);
        } else {
            MDC.clear();
        }
    }
}
