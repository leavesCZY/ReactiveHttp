package leavesc.hello.retrofit2_rxjava2.http;

import leavesc.hello.retrofit2_rxjava2.viewmodel.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:10
 * 描述：
 */
public class BaseRepo<T> {

    protected T remoteDataSource;

    protected BaseViewModel baseViewModel;

    public BaseRepo(T remoteDataSource, BaseViewModel baseViewModel) {
        this.remoteDataSource = remoteDataSource;
        this.baseViewModel = baseViewModel;
    }

}