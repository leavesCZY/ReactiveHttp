package leavesc.hello.network.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.network.http.basis.BaseRepo;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.basis.callback.RequestMultiplyCallback;
import leavesc.hello.network.http.datasource.base.IFailExampleDataSource;

/**
 * 作者：leavesC
 * 时间：2019/1/30 13:06
 * 描述：
 */
public class FailExampleRepo extends BaseRepo<IFailExampleDataSource> {

    public FailExampleRepo(IFailExampleDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<String> test1() {
        MutableLiveData<String> newsPackMutableLiveData = new MutableLiveData<>();
        remoteDataSource.test1(new RequestCallback<String>() {
            @Override
            public void onSuccess(String newsPack) {
                newsPackMutableLiveData.setValue(newsPack);
            }
        });
        return newsPackMutableLiveData;
    }

    public void test2(RequestMultiplyCallback<String> callback) {
        remoteDataSource.test2(callback);
    }

}