package leavesc.hello.retrofit2_rxjava2.http.exception;

import leavesc.hello.retrofit2_rxjava2.http.HttpCode;
import leavesc.hello.retrofit2_rxjava2.http.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/25 21:37
 * 描述：
 */
public class ResultInvalidException extends BaseException {

    public ResultInvalidException() {
        super(HttpCode.CODE_RESULT_INVALID, "无效请求");
    }

}
