package leavesc.hello.retrofit2_rxjava2.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRepo;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.datasource.base.IQrCodeDataSource;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:12
 * 描述：
 */
public class QrCodeRepo extends BaseRepo<IQrCodeDataSource> {

    public QrCodeRepo(IQrCodeDataSource remoteDataSource, BaseViewModel baseViewModel) {
        super(remoteDataSource, baseViewModel);
    }

    public MutableLiveData<QrCode> createQrCode(String text,int width) {
        MutableLiveData<QrCode> liveData = new MutableLiveData<>();
        remoteDataSource.createQrCode(text,width, new RequestCallback<QrCode>() {
            @Override
            public void onSuccess(QrCode qrCode) {
                liveData.setValue(qrCode);
            }
        });
        return liveData;
    }

}
