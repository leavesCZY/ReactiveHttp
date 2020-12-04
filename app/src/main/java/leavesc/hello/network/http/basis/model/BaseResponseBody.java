package leavesc.hello.network.http.basis.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：leavesC
 * 时间：2018/10/25 22:18
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public class BaseResponseBody<T> {

    @SerializedName("error_code")
    private int code;

    @SerializedName("reason")
    private String msg;

    @SerializedName("result")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
