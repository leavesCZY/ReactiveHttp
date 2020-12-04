package leavesc.hello.network.http.datasource.base;

import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.model.QrCode;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:10
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public interface IQrCodeDataSource {

    void createQrCode(String text, int width, RequestCallback<QrCode> callback);

}
