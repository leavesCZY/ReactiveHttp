### 一、前言

在今年的二月份，我在发布本 Repo 的同时，也写了一篇博客介绍了项目特色以及实现思路：[ReactiveHttp_1.0](https://juejin.cn/post/6844903774033543176)，详细地介绍了一步步封装的过程，该版本以 **Java + RxJava + Jetpack** 作为实现基础，也陆续获得了一些读者的正向反馈，让我意识到了一些不足点，而到现在我公司的项目也逐渐地转为了 kotlin ，因此也促使我来写第二篇博客

### 二、变化

本 Repo 现如今对应着两个版本

- 1.0 版本即 **java_rxjava_jetpack** 分支，是使用 Java 语言写的，也是最初始的版本，其实现思路可以看这里：[ReactiveHttp_1.0](https://juejin.cn/post/6844903774033543176)，用了比较大的篇幅介绍了我封装此网络请求框架的思路，建议读者首先去看下该文章

- 2.0 版本即 **kotlin_rxjava_jetpack** 分支，顾名思义，我用 kotlin 重构了一遍，当然也不仅仅只是简单的转换了下语言而已，也解决了 1.0 版本不太理想或者不太合理的地方，也得益于 kotlin 语言的简洁性，使得整个基础库更加得短小精悍，表达能力也显得更加强大

  ![](https://upload-images.jianshu.io/upload_images/2552605-5490b0a621a732be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 本 repo 不会再维护 java_rxjava_jetpack  分支对应的 Java 版本，以后新功能也只会采用 kotlin 语言

### 三、优化点

#### 3.1、BaseView

看到我的第一篇博客的同学应该知道，在请求网络的过程中界面层的一些通用操作（startLoading、dismissLoading、showToast）是需要由 BaseActivity 与 BaseFragment 通过监听 LiveData 的数据变化来完成的，1.0 版本受限于 JDK 版本，在 BaseActivity 与 BaseFragment 需要分别写较多的重复代码而无法提取到接口中，而通过 Kotlin 来完成的话就会相对简洁很多

```kotlin
/**
 * 作者：leavesC
 * 时间：2019/5/31 9:38
 * 描述：
 */
interface IBaseViewModelEvent {

    fun showLoading(msg: String)

    fun showLoading() {
        showLoading("")
    }

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

    fun pop()

}

interface IIBaseViewModelEventObserver : IBaseViewModelEvent {

    fun initViewModel(): BaseViewModel? {
        return null
    }

    fun initViewModelList(): MutableList<BaseViewModel>? {
        return null
    }

    fun getLifecycleOwner(): LifecycleOwner

    fun initViewModelEvent() {
        var list: MutableList<BaseViewModel>? = null
        val initViewModelList = initViewModelList()
        if (initViewModelList.isNullOrEmpty()) {
            val baseViewModel = initViewModel()
            baseViewModel?.let {
                list = mutableListOf(baseViewModel)
            }
        } else {
            list = initViewModelList
        }
        list?.let {
            observeEvent(list!!)
        }
    }

    fun observeEvent(viewModelList: MutableList<BaseViewModel>) {
        for (viewModel in viewModelList) {
            viewModel.baseActionEvent.observe(getLifecycleOwner(), Observer { it ->
                it?.let {
                    when (it.action) {
                        BaseViewModelEvent.SHOW_LOADING_DIALOG -> {
                            showLoading(it.message)
                        }
                        BaseViewModelEvent.DISMISS_LOADING_DIALOG -> {
                            dismissLoading()
                        }
                        BaseViewModelEvent.SHOW_TOAST -> {
                            showToast(it.message)
                        }
                        BaseViewModelEvent.FINISH -> {
                            finishView()
                        }
                        BaseViewModelEvent.POP -> {
                            pop()
                        }
                    }
                }
            })
        }
    }

    fun getLContext(): Context

    fun <T> startActivity(clazz: Class<T>) {
        getLContext().startActivity(Intent(getLContext(), clazz))
    }

    override fun showToast(msg: String) {
        Toast.makeText(getLContext(), msg, Toast.LENGTH_SHORT).show()
    }

}
```

BaseActivity 中只要实现几个特定方法即可，BaseFragment 也一样如此

```kotlin
abstract class BaseActivity : AppCompatActivity(), IIBaseViewModelEventObserver {

    private var loadDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelEvent()
    }

    override fun getLContext(): Context = this

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = ProgressDialog(getLContext())
            loadDialog!!.setCancelable(false)
            loadDialog!!.setCanceledOnTouchOutside(false)
        }
        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun finishView() {
        finish()
    }

    override fun pop() {

    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) =
            ViewModelProviders.of(this).get(clazz)

}
```

#### 3.2、BaseRemoteDataSource

在 1.0 版本中，RetrofitManagement 与 BaseRemoteDataSource 之间牵扯较多，BaseRemoteDataSource 调用了几个本应不该由 RetrofitManagement 来实现的方法，使得逻辑较为复杂凌乱

在 2.0 版本中，RetrofitManagement 变为专职只负责提供 `ApiService.class` 对象， 具体的网络请求调用以及数据解析都只由 BaseRemoteDataSource 来中转控制

```kotlin
    @SuppressLint("CheckResult")
    private fun <T> execute(observable: Observable<BaseResponse<T>>, observer: Observer<OptionT<T>>, quietly: Boolean) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!quietly) {
                        showLoading()
                    }
                }.doFinally {
                    dismissLoading()
                }.flatMap(object : Function<BaseResponse<T>, ObservableSource<OptionT<T>>> {
                    override fun apply(t: BaseResponse<T>): ObservableSource<OptionT<T>> {
                        when {
                            t.code == HttpConfig.CODE_SUCCESS || t.message == "OK" -> {
                                val optional: OptionT<T> = OptionT(t.data)
                                return createData(optional)
                            }
                            else -> {
                                throw ServerResultException(t.message ?: "未知错误", t.code)
                            }
                        }
                    }
                }).subscribeWith(observer)
    }

    private fun <T> createData(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
```

#### 3.3、可空性

有时候，在请求一个单纯修改状态值的接口时，服务器返回的数据可能是这样的，此时接口只是通过 code 来反馈该接口已经请求成功，而不需要返回特定数据，因此 data 直接为 null

```json
    {
	    "code": 200,
	    "msg": "ok",
	    "data": null
    }
```

这就会衍生出一个问题，由于 RxJava 2.0 版本后不允许出现 `emitter.onNext(null)` 传递 null 值的情况，所以导致数据传递链路就会中断，虽然说此时只要要求后台同事返回一个空字符串即可解决问题，可有时候沟通并不总是那么理想……

因此就需要 app 端自己来解决了，2.0 版本是通过多加一个包装类 `OptionT` 来解决该问题的

```kotlin
    class OptionT<T>(val value: T)
```

在解析数据时，将我们实际需要的数据 data 包装在 OptionT 当中，此时就无需关心 data 是否为 null ，

```kotlin
    override fun apply(t: BaseResponse<T>): ObservableSource<OptionT<T>> {
            when {
                t.code == HttpConfig.CODE_SUCCESS || t.message == "OK" -> {
                    val optional: OptionT<T> = OptionT(t.data)
                    return createData(optional)
                }
                else -> {
                    throw ServerResultException(t.message ?: "未知错误", t.code)
                }
            }
    }

    private fun <T> createData(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
```

在 BaseSubscriber 中再将 data 取出来并传递给 RequestCallback ，以此规避 null 值不可传递的问题

```kotlin
class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>) :
    DisposableObserver<OptionT<T>>() {

    override fun onNext(t: OptionT<T>) {
        requestCallback.onSuccess(t.value)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        val msg = e.message ?: "未知错误"
        if (requestCallback is RequestMultiplyCallback) {
            if (e is BaseException) {
                requestCallback.onFail(e)
            } else {
                requestCallback.onFail(ServerResultException(msg))
            }
        } else {
            ToastHolder.showToast(msg = msg)
        }
    }

    override fun onComplete() {

    }

}
```

其实通过 `OptionT` 这个名字，很多读者应该就可以联想到 `JDK 1.8` 新增的 `Option` 类了吧？其实此处就是借鉴该了其思想

我觉得有必要介绍的优化点就以上三个了，其它地方就留待读者自己去发现了

### 四、结束语

这就是整个请求框架的大体架构了，也经过了实际项目的考验了，目前运行良好，但里面可能还会包含一些不合理的地方，欢迎大家指正反馈，如果觉得对你有所帮助，也欢迎 star

#### 源码点击这里查看：[ReactiveHttp](https://github.com/leavesC/ReactiveHttp)