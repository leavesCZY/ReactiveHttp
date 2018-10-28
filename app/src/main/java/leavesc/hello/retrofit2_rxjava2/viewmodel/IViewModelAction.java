package leavesc.hello.retrofit2_rxjava2.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.event.BaseActionEvent;

/**
 * 作者：叶应是叶
 * 时间：2018/10/1 13:20
 * 描述：
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