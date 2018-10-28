package leavesc.hello.retrofit2_rxjava2.http.datasource;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.http.datasource.base.IIDCardDataSource;
import leavesc.hello.retrofit2_rxjava2.http.service.ApiService;
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
    public void queryIDCard(String cardNo, RequestCallback<IDCard> callback) {
        execute(getService(ApiService.class, HttpConfig.BASE_URL_ID_CARD).queryIDCard(cardNo), callback);
    }

}