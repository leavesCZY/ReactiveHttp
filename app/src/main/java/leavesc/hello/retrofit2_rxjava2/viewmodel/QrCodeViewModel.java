package leavesc.hello.retrofit2_rxjava2.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.retrofit2_rxjava2.http.datasource.QrCodeDataSource;
import leavesc.hello.retrofit2_rxjava2.http.repo.QrCodeRepo;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:14
 * 描述：
 */
public class QrCodeViewModel extends BaseViewModel {

    private MutableLiveData<QrCode> qrCodeLiveData;

    private QrCodeRepo qrCodeRepo;

    public QrCodeViewModel() {
        qrCodeLiveData = new MutableLiveData<>();
        qrCodeRepo = new QrCodeRepo(new QrCodeDataSource(this));
    }

    public void createQrCode(String text, int width) {
        qrCodeRepo.createQrCode(text, width).observe(lifecycleOwner, new Observer<QrCode>() {
            @Override
            public void onChanged(@Nullable QrCode qrCode) {
                qrCodeLiveData.setValue(qrCode);
            }
        });
    }

    public MutableLiveData<QrCode> getQrCodeLiveData() {
        return qrCodeLiveData;
    }

}
