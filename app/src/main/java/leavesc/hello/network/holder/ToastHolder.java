package leavesc.hello.network.holder;

import android.content.Context;
import android.widget.Toast;

/**
 * 作者：leavesC
 * 时间：2019/4/17 16:53
 * 描述：
 */
public class ToastHolder {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String msg) {
        showToast(ContextHolder.getContext(), msg);
    }

}
