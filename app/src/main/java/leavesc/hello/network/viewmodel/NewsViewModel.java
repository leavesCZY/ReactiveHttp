package leavesc.hello.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.network.http.datasource.NewsDataSource;
import leavesc.hello.network.http.repo.NewsRepo;
import leavesc.hello.network.model.NewsPack;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2019/1/30 0:50
 * 描述：
 */
public class NewsViewModel extends BaseViewModel {

    private MutableLiveData<NewsPack> newsPackMutableLiveData = new MutableLiveData<>();

    private NewsRepo newsRepo = new NewsRepo(new NewsDataSource(this));

    public void getNews() {
        newsRepo.getNews().observe(lifecycleOwner, new Observer<NewsPack>() {
            @Override
            public void onChanged(@Nullable NewsPack newsPack) {
                newsPackMutableLiveData.setValue(newsPack);
            }
        });
    }

    public MutableLiveData<NewsPack> getNewsPackMutableLiveData() {
        return newsPackMutableLiveData;
    }

}
