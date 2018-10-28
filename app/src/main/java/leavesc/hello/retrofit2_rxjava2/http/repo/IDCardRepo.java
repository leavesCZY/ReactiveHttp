package leavesc.hello.retrofit2_rxjava2.http.repo;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRepo;
import leavesc.hello.retrofit2_rxjava2.http.basis.RequestWithFailCallback;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;
import leavesc.hello.retrofit2_rxjava2.http.dataSource.IDCardDataSource;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:12
 * 描述：
 */
public class IDCardRepo extends BaseRepo<IDCardDataSource> {

    public IDCardRepo(IDCardDataSource remoteDataSource, BaseViewModel baseViewModel) {
        super(remoteDataSource, baseViewModel);
    }

    public MutableLiveData<IDCard> queryIDCard(String cardNo) {
        MutableLiveData<IDCard> liveData = new MutableLiveData<>();
        remoteDataSource.queryIDCard(cardNo, new RequestWithFailCallback<IDCard>() {
            @Override
            public void onFail(BaseException e) {
                Log.e("TAG", "onFail: " + e.getMessage());
            }

            @Override
            public void onSuccess(IDCard idCard) {
                liveData.setValue(idCard);
            }
        });
        return liveData;
    }

}
