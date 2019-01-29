package leavesc.hello.network.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.network.http.basis.BaseRepo;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.datasource.base.INewsDataSource;
import leavesc.hello.network.model.NewsPack;

/**
 * 作者：leavesC
 * 时间：2019/1/30 0:51
 * 描述：
 */
public class NewsRepo extends BaseRepo<INewsDataSource> {

    public NewsRepo(INewsDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<NewsPack> getNews() {
        MutableLiveData<NewsPack> newsPackMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getNews(new RequestCallback<NewsPack>() {
            @Override
            public void onSuccess(NewsPack newsPack) {
                newsPackMutableLiveData.setValue(newsPack);
            }
        });
        return newsPackMutableLiveData;
    }

}
