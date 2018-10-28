package leavesc.hello.retrofit2_rxjava2.http.dataSource;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.basis.BaseSubscriber;
import leavesc.hello.retrofit2_rxjava2.http.basis.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.http.basis.RequestWithFailCallback;
import leavesc.hello.retrofit2_rxjava2.http.dataSource.base.IIDCardDataSource;
import leavesc.hello.retrofit2_rxjava2.http.service.IDCardService;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/28 10:26
 * 描述：
 */
public class IDCardDataSource extends BaseRemoteDataSource implements IIDCardDataSource {

    public IDCardDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryIDCard(String cardNo, RequestWithFailCallback<IDCard> callback) {
        execute(getService(IDCardService.class, HttpConfig.BASE_URL_ID_CARD).queryIDCard(cardNo),
                new BaseSubscriber<>(baseViewModel, callback));
    }

}
