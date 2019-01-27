package leavesc.hello.network.holder;

import android.content.Context;

import leavesc.hello.network.BaseApplication;

/**
 * 作者：leavesC
 * 时间：2019/1/27 18:00
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ContextHolder {

    public static Context getContext() {
        return BaseApplication.getAppContext();
    }

}
