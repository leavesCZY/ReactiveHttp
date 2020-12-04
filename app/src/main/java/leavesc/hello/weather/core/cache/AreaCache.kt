package leavesc.hello.weather.core.cache

import android.content.Context

/**
 * 作者：leavesC
 * 时间：2019/6/4 21:22
 * 描述：
 */
class AreaCache {

    companion object {

        private const val NAME_SP = "nameSP"

        private const val KEY_AD_CODE = "keyAdCode"

        fun saveAdCode(context: Context, adCode: String) {
            val sharedPreferences = context.getSharedPreferences(NAME_SP, Context.MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.putString(KEY_AD_CODE, adCode)
            edit.apply()
        }

        fun getAdCode(context: Context): String {
            val sharedPreferences = context.getSharedPreferences(NAME_SP, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_AD_CODE, "") ?: ""
        }

    }

}