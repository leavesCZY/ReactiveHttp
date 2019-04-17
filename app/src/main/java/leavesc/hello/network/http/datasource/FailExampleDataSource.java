package leavesc.hello.network.http.datasource;

import leavesc.hello.network.http.basis.BaseRemoteDataSource;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.basis.callback.RequestMultiplyCallback;
import leavesc.hello.network.http.basis.exception.base.BaseException;
import leavesc.hello.network.http.datasource.base.IFailExampleDataSource;
import leavesc.hello.network.http.service.ApiService;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2019/1/30 13:02
 * 描述：
 */
public class FailExampleDataSource extends BaseRemoteDataSource implements IFailExampleDataSource {

    public FailExampleDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void test1(RequestCallback<String> callback) {
        execute(getService(ApiService.class).test1(), callback);
    }

    @Override
    public void test2(RequestCallback<String> callback) {
        execute(getService(ApiService.class).test2(), callback);
    }

}