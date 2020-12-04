package leavesc.hello.network.http.repo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.network.http.basis.BaseRepo;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.datasource.base.IQrCodeDataSource;
import leavesc.hello.network.model.QrCode;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:12
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public class QrCodeRepo extends BaseRepo<IQrCodeDataSource> {

    public QrCodeRepo(IQrCodeDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<QrCode> createQrCode(String text, int width) {
        MutableLiveData<QrCode> liveData = new MutableLiveData<>();
        remoteDataSource.createQrCode(text, width, new RequestCallback<QrCode>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(QrCode qrCode) {
                Observable.create(new ObservableOnSubscribe<Bitmap>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Bitmap> emitter) throws Exception {
                        Bitmap bitmap = base64ToBitmap(qrCode.getBase64_image());
                        emitter.onNext(bitmap);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void accept(@NonNull Bitmap bitmap) throws Exception {
                                qrCode.setBitmap(bitmap);
                                liveData.setValue(qrCode);
                            }
                        });
            }
        });
        return liveData;
    }

    private static Bitmap base64ToBitmap(String base64String) {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

}