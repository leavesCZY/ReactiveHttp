package leavesc.hello.retrofit2_rxjava2.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRepo;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.datasource.IDCardDataSource;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:12
 * 描述：
 */
public class IDCardRepo extends BaseRepo<IDCardDataSource> {

    public IDCardRepo(IDCardDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<IDCard> queryIDCard(String cardNo) {
        MutableLiveData<IDCard> liveData = new MutableLiveData<>();
        remoteDataSource.queryIDCard(cardNo, new RequestCallback<IDCard>() {
            @Override
            public void onSuccess(IDCard idCard) {
                liveData.setValue(idCard);
            }
        });
        return liveData;
    }

}
