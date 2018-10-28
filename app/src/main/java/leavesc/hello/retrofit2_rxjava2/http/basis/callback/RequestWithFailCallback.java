package leavesc.hello.retrofit2_rxjava2.http.basis.callback;

import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 20:53
 * 描述：
 */
public interface RequestWithFailCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);

}