package leavesc.hello.network;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import leavesc.hello.network.model.NewsPack;
import leavesc.hello.network.view.BaseActivity;
import leavesc.hello.network.viewmodel.NewsViewModel;
import leavesc.hello.network.viewmodel.base.LViewModelProviders;

/**
 * 作者：leavesC
 * 时间：2019/1/30 0:54
 * 描述：
 */
public class QueryNewsActivity extends BaseActivity {

    private static final String TAG = "QueryNewsActivity";

    private NewsViewModel newsViewModel;

    private TextView tv_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_news);
        tv_news = findViewById(R.id.tv_news);
    }

    @Override
    protected ViewModel initViewModel() {
        newsViewModel = LViewModelProviders.of(this, NewsViewModel.class);
        newsViewModel.getNewsPackMutableLiveData().observe(this, this::handlerNews);
        return newsViewModel;
    }

    private void handlerNews(NewsPack newsPack) {
        StringBuilder result = new StringBuilder();
        for (NewsPack.News news : newsPack.getData()) {
            result.append("\n\n").append(new Gson().toJson(news));
        }
        tv_news.setText(result.toString());
    }

    public void queryNews(View view) {
        tv_news.setText(null);
        newsViewModel.getNews();
    }

}