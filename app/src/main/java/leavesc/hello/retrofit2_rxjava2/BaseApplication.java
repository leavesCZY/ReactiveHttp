package leavesc.hello.retrofit2_rxjava2;

import android.app.Application;

/**
 * 作者：leavesC
 * 时间：2018/10/26 23:24
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class BaseApplication extends Application {

    private static Application appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Application getAppContext() {
        return appContext;
    }

}
