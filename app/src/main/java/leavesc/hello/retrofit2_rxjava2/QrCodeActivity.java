package leavesc.hello.retrofit2_rxjava2;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;
import leavesc.hello.retrofit2_rxjava2.view.BaseActivity;
import leavesc.hello.retrofit2_rxjava2.viewmodel.QrCodeViewModel;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.LViewModelProviders;

public class QrCodeActivity extends BaseActivity {

    private EditText et_text;

    private ImageView iv_qrCode;

    private QrCodeViewModel qrCodeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        et_text = findViewById(R.id.et_text);
        iv_qrCode = findViewById(R.id.iv_qrCode);
    }

    @Override
    protected ViewModel initViewModel() {
        qrCodeViewModel = LViewModelProviders.of(this, QrCodeViewModel.class);
        qrCodeViewModel.getQrCodeLiveData().observe(this, this::handleQrCode);
        return qrCodeViewModel;
    }

    @SuppressLint("CheckResult")
    private void handleQrCode(QrCode qrCode) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = base64ToBitmap(qrCode.getBase64_image());
                e.onNext(bitmap);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(@NonNull Bitmap bitmap) throws Exception {
                        iv_qrCode.setImageBitmap(bitmap);
                    }
                });
    }

    public void createQrCode(View view) {
        iv_qrCode.setImageBitmap(null);
        qrCodeViewModel.createQrCode(et_text.getText().toString(), 600);
    }

    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

}
