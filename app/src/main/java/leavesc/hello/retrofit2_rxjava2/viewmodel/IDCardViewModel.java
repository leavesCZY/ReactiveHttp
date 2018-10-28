package leavesc.hello.retrofit2_rxjava2.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.retrofit2_rxjava2.http.datasource.IDCardDataSource;
import leavesc.hello.retrofit2_rxjava2.http.repo.IDCardRepo;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/28 10:35
 * 描述：
 */
public class IDCardViewModel extends BaseViewModel {

    private MutableLiveData<IDCard> idCardLiveData;

    private IDCardRepo idCardRepo;

    public IDCardViewModel() {
        idCardLiveData = new MutableLiveData<>();
        idCardRepo = new IDCardRepo(new IDCardDataSource(this), this);
    }

    public void queryIDCard(String cardNo) {
        idCardRepo.queryIDCard(cardNo).observe(lifecycleOwner, new Observer<IDCard>() {
            @Override
            public void onChanged(@Nullable IDCard idCard) {
                idCardLiveData.setValue(idCard);
            }
        });
    }

    public MutableLiveData<IDCard> getIdCardLiveData() {
        return idCardLiveData;
    }

}
