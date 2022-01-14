package github.leavesczy.reactivehttp.viewmodel

/**
 * @Author: leavesCZY
 * @Date: 2020/6/26 21:19
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
sealed class UIActionEvent

object ShowLoadingEvent : UIActionEvent()

object DismissLoadingEvent : UIActionEvent()

object FinishViewEvent : UIActionEvent()

class ShowToastEvent(val message: String) : UIActionEvent()