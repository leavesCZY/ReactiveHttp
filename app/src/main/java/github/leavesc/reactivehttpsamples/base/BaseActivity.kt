package github.leavesc.reactivehttpsamples.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import github.leavesc.reactivehttp.base.BaseReactiveActivity

/**
 * @Author: leavesC
 * @Date: 2020/10/22 10:29
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class BaseActivity : BaseReactiveActivity() {

    abstract val bind: ViewBinding?

    protected inline fun <reified T> getBind(): Lazy<T> where T : ViewBinding {
        return lazy {
            val clazz = T::class.java
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            method.invoke(null, layoutInflater) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind?.root?.apply {
            setContentView(this)
        }
    }

    protected inline fun <reified T : Activity> startActivity() {
        lContext?.apply {
            startActivity(Intent(this, T::class.java))
        }
    }

}