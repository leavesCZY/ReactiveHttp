package leavesc.hello.retrofit2_rxjava2.http.basis.callback;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:53
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

}
