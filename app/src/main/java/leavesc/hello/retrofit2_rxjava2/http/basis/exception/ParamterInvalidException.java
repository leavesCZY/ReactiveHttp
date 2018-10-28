package leavesc.hello.retrofit2_rxjava2.http.basis.exception;

import leavesc.hello.retrofit2_rxjava2.http.basis.HttpCode;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/25 21:39
 * 描述：
 */
public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "参数有误");
    }

}
