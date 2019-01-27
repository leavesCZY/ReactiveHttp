package leavesc.hello.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.network.http.datasource.QrCodeDataSource;
import leavesc.hello.network.http.repo.QrCodeRepo;
import leavesc.hello.network.model.QrCode;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:14
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
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
