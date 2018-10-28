package leavesc.hello.retrofit2_rxjava2;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;

import leavesc.hello.retrofit2_rxjava2.view.BaseActivity;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    public void queryWeather(View view) {
        startActivity(QueryWeatherActivity.class);
    }

    public void queryIDCard(View view) {
        startActivity(IDCardActivity.class);
    }

    public void createQrCode(View view) {
        startActivity(QrCodeActivity.class);
    }
}