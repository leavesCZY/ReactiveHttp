package leavesc.hello.network.http.datasource.base;

import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.model.IDCard;

/**
 * 作者：leavesC
 * 时间：2018/10/28 10:23
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface IIDCardDataSource {

    void queryIDCard(String cardNo, RequestCallback<IDCard> callback);

}