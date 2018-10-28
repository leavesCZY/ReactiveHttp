package leavesc.hello.retrofit2_rxjava2.event.base;

/**
 * 作者：叶应是叶
 * 时间：2018/9/30 22:17
 * 描述：
 */
public class BaseEvent {

    private int action;

    public BaseEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

}