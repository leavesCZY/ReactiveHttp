package leavesc.hello.retrofit2_rxjava2.viewmodel.base;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.event.BaseActionEvent;

/**
 * 作者：leavesC
 * 时间：2018/10/1 13:20
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface IViewModelAction {

    void startLoading();

    void startLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void finish();

    void finishWithResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();

}