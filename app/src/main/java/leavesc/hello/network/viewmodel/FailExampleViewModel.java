package leavesc.hello.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.network.http.basis.callback.RequestMultiplyCallback;
import leavesc.hello.network.http.basis.exception.base.BaseException;
import leavesc.hello.network.http.datasource.FailExampleDataSource;
import leavesc.hello.network.http.repo.FailExampleRepo;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2019/1/30 13:08
 * 描述：
 */
public class FailExampleViewModel extends BaseViewModel {

    private MutableLiveData<String> test1LiveData = new MutableLiveData<>();

    private MutableLiveData<String> test2LiveData = new MutableLiveData<>();

    private FailExampleRepo failExampleRepo = new FailExampleRepo(new FailExampleDataSource(this));

    public void test1() {
        failExampleRepo.test1().observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                test1LiveData.setValue(s);
            }
        });
//        failExampleRepo.test1().observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                test1LiveData.setValue(s);
//            }
//        });
    }

    public void test2() {
        failExampleRepo.test2(new RequestMultiplyCallback<String>() {
            @Override
            public void onFail(BaseException e) {
                showToast("test2方法请求失败：" + e.getMessage());
                finish();
            }

            @Override
            public void onSuccess(String s) {
                test2LiveData.setValue(s);
            }
        });
    }

}