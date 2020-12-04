package leavesc.hello.network.event.base;

/**
 * 作者：leavesC
 * 时间：2018/9/30 22:17
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public class BaseEvent {

    private int action;

    public BaseEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}