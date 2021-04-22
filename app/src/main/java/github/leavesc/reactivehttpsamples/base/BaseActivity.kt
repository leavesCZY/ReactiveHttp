package github.leavesc.reactivehttpsamples.base

import android.app.Activity
import android.content.Intent
import github.leavesc.reactivehttp.base.BaseReactiveActivity

/**
 * @Author: leavesC
 * @Date: 2020/10/22 10:29
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
open class BaseActivity : BaseReactiveActivity() {

    protected inline fun <reified T : Activity> startActivity() {
        lContext?.apply {
            startActivity(Intent(this, T::class.java))
        }
    }

}