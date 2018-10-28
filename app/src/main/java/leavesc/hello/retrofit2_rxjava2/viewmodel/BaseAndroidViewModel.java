package leavesc.hello.retrofit2_rxjava2.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.event.BaseActionEvent;

/**
 * 作者：叶应是叶
 * 时间：2018/9/30 22:43
 * 描述：
 */
public class BaseAndroidViewModel extends AndroidViewModel implements IViewModelAction {

    private MutableLiveData<BaseActionEvent> actionLiveData;

    public BaseAndroidViewModel(Application application) {
        super(application);
        actionLiveData = new MutableLiveData<>();
    }

    @Override
    public void startLoading() {
        startLoading(null);
    }

    @Override
    public void startLoading(String message) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_LOADING_DIALOG);
        baseActionEvent.setMessage(message);
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void dismissLoading() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.DISMISS_LOADING_DIALOG));
    }

    @Override
    public void showToast(String message) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_TOAST);
        baseActionEvent.setMessage(message);
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void finish() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH));
    }

    @Override
    public void finishWithResultOk() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH_WITH_RESULT_OK));
    }

    @Override
    public MutableLiveData<BaseActionEvent> getActionLiveData() {
        return actionLiveData;
    }

}