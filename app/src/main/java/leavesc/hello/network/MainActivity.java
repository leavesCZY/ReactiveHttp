package leavesc.hello.network;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;

import leavesc.hello.network.view.BaseActivity;

/**
 * 作者：leavesC
 * 时间：2018/10/29 21:24
 * 描述：
 * GitHub：https://github.com/leavesC
 */
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

    public void createQrCode(View view) {
        startActivity(QrCodeActivity.class);
    }

    public void queryNews(View view) {
        startActivity(QueryNewsActivity.class);
    }

    public void failExample(View view) {
        startActivity(FailExampleActivity.class);
    }

}