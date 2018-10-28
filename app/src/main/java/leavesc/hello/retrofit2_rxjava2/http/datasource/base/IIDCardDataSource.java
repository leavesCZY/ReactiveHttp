package leavesc.hello.retrofit2_rxjava2.http.datasource.base;

import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;

/**
 * 作者：叶应是叶
 * 时间：2018/10/28 10:23
 * 描述：
 */
public interface IIDCardDataSource {

    void queryIDCard(String cardNo, RequestCallback<IDCard> callback);

}