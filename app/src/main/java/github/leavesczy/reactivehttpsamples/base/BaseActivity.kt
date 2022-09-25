package github.leavesczy.reactivehttpsamples.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/22 10:29
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
abstract class BaseActivity : AppCompatActivity() {

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
        startActivity(Intent(this, T::class.java))
    }

}