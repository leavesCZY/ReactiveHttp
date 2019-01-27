package leavesc.hello.network.http.basis.callback;

import leavesc.hello.network.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:53
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface RequestMultiplyCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);

}