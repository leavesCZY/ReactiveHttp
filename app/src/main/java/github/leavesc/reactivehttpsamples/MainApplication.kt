package github.leavesc.reactivehttpsamples

import android.app.Application

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MainApplication : Application() {

    companion object {

        lateinit var context: Application

    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}