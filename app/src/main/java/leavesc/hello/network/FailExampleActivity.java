package leavesc.hello.network;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.view.View;

import leavesc.hello.network.view.BaseActivity;
import leavesc.hello.network.viewmodel.FailExampleViewModel;
import leavesc.hello.network.viewmodel.base.LViewModelProviders;

/**
 * 作者：leavesC
 * 时间：2019/1/30 12:58
 * 描述：
 */
public class FailExampleActivity extends BaseActivity {

    private FailExampleViewModel failExampleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_example);
    }

    @Override
    protected ViewModel initViewModel() {
        failExampleViewModel = LViewModelProviders.of(this, FailExampleViewModel.class);
        return failExampleViewModel;
    }

    public void test1(View view) {
        failExampleViewModel.test1();
    }

    public void test2(View view) {
        failExampleViewModel.test2();
    }

}
