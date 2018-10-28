package leavesc.hello.retrofit2_rxjava2.http.datasource.base;

import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:10
 * 描述：
 */
public interface IQrCodeDataSource {

    void createQrCode(String text, int width, RequestCallback<QrCode> callback);

}
