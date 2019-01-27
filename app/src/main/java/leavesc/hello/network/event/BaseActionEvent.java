package leavesc.hello.network.event;

import leavesc.hello.network.event.base.BaseEvent;

/**
 * 作者：leavesC
 * 时间：2018/10/29 20:38
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
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