package leavesc.hello.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.network.http.datasource.IDCardDataSource;
import leavesc.hello.network.http.repo.IDCardRepo;
import leavesc.hello.network.model.IDCard;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/28 10:35
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class IDCardViewModel extends BaseViewModel {

    private MutableLiveData<IDCard> idCardLiveData;

    private IDCardRepo idCardRepo;

    public IDCardViewModel() {
        idCardLiveData = new MutableLiveData<>();
        idCardRepo = new IDCardRepo(new IDCardDataSource(this));
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
