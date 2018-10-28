package leavesc.hello.retrofit2_rxjava2.http;

import leavesc.hello.retrofit2_rxjava2.http.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 20:53
 * 描述：
 */
public interface RequestWithFailCallback<T> {

    void onSuccess(T t);

    void onFail(BaseException e);

}