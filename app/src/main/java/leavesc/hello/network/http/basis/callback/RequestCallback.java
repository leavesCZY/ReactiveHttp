package leavesc.hello.network.http.basis.callback;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:53
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

}
