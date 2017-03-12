package com.booboomx.hotvideo.model.net;

/**
 * Created by booboomx on 17/3/12.
 */

/**
 * 统一返回的数据类型
 * @param <T>
 */
public class VideoHttpResponse<T> {
    private int code;
    private String msg;
      T ret;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRet() {
        return ret;
    }

    public void setRet(T ret) {
        this.ret = ret;
    }
}
