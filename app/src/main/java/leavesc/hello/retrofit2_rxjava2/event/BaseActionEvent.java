package leavesc.hello.retrofit2_rxjava2.event;

import leavesc.hello.retrofit2_rxjava2.event.base.BaseEvent;

/**
 * 作者：叶应是叶
 * 时间：2018/9/30 22:28
 * 描述：
 */
public class BaseActionEvent extends BaseEvent {

    public static final int SHOW_LOADING_DIALOG = 1;

    public static final int DISMISS_LOADING_DIALOG = 2;

    public static final int SHOW_TOAST = 3;

    public static final int FINISH = 4;

    public static final int FINISH_WITH_RESULT_OK = 5;

    private String message;

    public BaseActionEvent(int action) {
        super(action);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}