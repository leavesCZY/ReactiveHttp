package leavesc.hello.network.viewmodel.base;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * 作者：leavesC
 * 时间：2018/10/28 7:57
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class LViewModelProviders {

    public static <T extends BaseViewModel> T of(@NonNull FragmentActivity activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

    public static <T extends BaseViewModel> T of(@NonNull Fragment activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

}