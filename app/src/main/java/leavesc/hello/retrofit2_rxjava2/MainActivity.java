package leavesc.hello.retrofit2_rxjava2;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.retrofit2_rxjava2.http.RetrofitManagement;
import leavesc.hello.retrofit2_rxjava2.http.WeatherService;
import leavesc.hello.retrofit2_rxjava2.http.model.BaseResponseBody;
import leavesc.hello.retrofit2_rxjava2.view.BaseActivity;
import leavesc.hello.retrofit2_rxjava2.viewmodel.LViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected ViewModel initViewModel() {
        weatherViewModel = LViewModelProviders.of(this, WeatherViewModel.class);
        weatherViewModel.getWeatherLiveData().observe(this, this::handlerWeather);
        return weatherViewModel;
    }

    private void handlerWeather(Weather weather) {
        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
            Log.e(TAG, "onResponse weather : " + new Gson().toJson(nearestWeather));
        }
    }

    public void get(View view) {
        WeatherService weatherService = RetrofitManagement.getInstance().getService(WeatherService.class);
        Call<BaseResponseBody<Weather>> call = weatherService.queryWeather("广州", "afc28ae28c6f1b520dab5d1ed537f6c0");
        call.enqueue(new Callback<BaseResponseBody<Weather>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseBody<Weather>> call, @NonNull Response<BaseResponseBody<Weather>> response) {
                Weather weather = response.body().getData();
                for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
                    Log.e(TAG, "onResponse weather : " + new Gson().toJson(nearestWeather));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseBody<Weather>> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable : " + t.getMessage());
            }
        });
    }

    public void getWithRxJava(View view) {
        WeatherService weatherService = RetrofitManagement.getInstance().getService(WeatherService.class);
        weatherService.queryWeatherWithRxJava("广州", "afc28ae28c6f1b520dab5d1ed537f6c0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResponseBody<Weather>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onNext(BaseResponseBody<Weather> response) {
                        Weather weather = response.getData();
                        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
                            Log.e(TAG, "onResponse weather : " + new Gson().toJson(nearestWeather));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void getWithRxJava2(View view) {
        weatherViewModel.queryWeather("广州", "afc28ae28c6f1b520dab5d1ed537f6c0");
    }

}
