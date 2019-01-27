package leavesc.hello.network.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.network.http.basis.BaseRepo;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.datasource.base.IQrCodeDataSource;
import leavesc.hello.network.model.QrCode;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:12
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class QrCodeRepo extends BaseRepo<IQrCodeDataSource> {

    public QrCodeRepo(IQrCodeDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<QrCode> createQrCode(String text, int width) {
        MutableLiveData<QrCode> liveData = new MutableLiveData<>();
        remoteDataSource.createQrCode(text, width, new RequestCallback<QrCode>() {
            @Override
            public void onSuccess(QrCode qrCode) {
                liveData.setValue(qrCode);
            }
        });
        return liveData;
    }

}
