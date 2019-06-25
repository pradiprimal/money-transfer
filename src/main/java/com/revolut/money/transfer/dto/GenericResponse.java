package com.revolut.money.transfer.dto;

import java.io.Serializable;

public class GenericResponse<T> implements Serializable {
    private long code;

    private T data;

    public GenericResponse(long code, T data) {
        this.code = code;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
