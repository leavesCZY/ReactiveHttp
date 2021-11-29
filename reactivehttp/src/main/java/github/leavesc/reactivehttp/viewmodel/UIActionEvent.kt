package github.leavesc.reactivehttp.viewmodel

/**
 * @Author: leavesC
 * @Date: 2020/6/26 21:19
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
sealed class UIActionEvent {

    object ShowLoadingEvent : UIActionEvent()

    object DismissLoadingEvent : UIActionEvent()

    object FinishViewEvent : UIActionEvent()

    class ShowToastEvent(val message: String) : UIActionEvent()

}