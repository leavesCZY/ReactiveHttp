package leavesc.hello.retrofit2_rxjava2.http.datasource;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.http.datasource.base.IQrCodeDataSource;
import leavesc.hello.retrofit2_rxjava2.http.service.ApiService;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:48
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class QrCodeDataSource extends BaseRemoteDataSource implements IQrCodeDataSource {

    public QrCodeDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void createQrCode(String text, int width, RequestCallback<QrCode> callback) {
        execute(getService(ApiService.class, HttpConfig.BASE_URL_QR_CODE).createQrCode(text, width), callback);
    }
}
