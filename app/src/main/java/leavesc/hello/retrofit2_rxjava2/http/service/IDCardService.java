package leavesc.hello.retrofit2_rxjava2.http.service;

import io.reactivex.Observable;
import leavesc.hello.retrofit2_rxjava2.http.basis.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.http.basis.model.BaseResponseBody;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 作者：叶应是叶
 * 时间：2018/10/28 10:27
 * 描述：
 */
public interface IDCardService {

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_ID_CARD})
    @GET("idcard/index")
    Observable<BaseResponseBody<IDCard>> queryIDCard(@Query("cardno") String cardNo);

}
