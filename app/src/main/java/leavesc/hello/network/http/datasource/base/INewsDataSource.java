package leavesc.hello.network.http.datasource.base;

import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.model.NewsPack;

/**
 * 作者：leavesC
 * 时间：2019/1/30 0:46
 * 描述：
 */
public interface INewsDataSource {

    void getNews(RequestCallback<NewsPack> callback);

}
