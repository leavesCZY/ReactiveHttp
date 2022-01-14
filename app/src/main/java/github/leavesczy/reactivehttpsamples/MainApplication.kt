package github.leavesczy.reactivehttpsamples

import android.app.Application

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @Github：https://github.com/leavesCZY
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