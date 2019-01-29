package leavesc.hello.network.http.datasource;

import leavesc.hello.network.http.basis.BaseRemoteDataSource;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.basis.config.HttpConfig;
import leavesc.hello.network.http.datasource.base.INewsDataSource;
import leavesc.hello.network.http.service.ApiService;
import leavesc.hello.network.model.NewsPack;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2019/1/30 0:49
 * 描述：
 */
public class NewsDataSource extends BaseRemoteDataSource implements INewsDataSource {

    public NewsDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getNews(RequestCallback<NewsPack> callback) {
        execute(getService(ApiService.class, HttpConfig.BASE_URL_NEWS).getNews(), callback);
    }

}