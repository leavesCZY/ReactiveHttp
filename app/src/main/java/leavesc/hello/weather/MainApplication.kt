package leavesc.hello.weather

import android.app.Application

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:07
 * 描述：
 * GitHub：https://github.com/leavesC
 */
class MainApplication : Application() {

    companion object {

        lateinit var context: MainApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}