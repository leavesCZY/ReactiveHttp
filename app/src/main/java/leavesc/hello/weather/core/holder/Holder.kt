package leavesc.hello.weather.core.holder

import android.content.Context
import android.widget.Toast
import leavesc.hello.weather.MainApplication

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:07
 * 描述：
 */

class ContextHolder {

    companion object {

        val context: Context by lazy { MainApplication.context }

    }

}

class ToastHolder {

    companion object {
        fun showToast(context: Context = ContextHolder.context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}