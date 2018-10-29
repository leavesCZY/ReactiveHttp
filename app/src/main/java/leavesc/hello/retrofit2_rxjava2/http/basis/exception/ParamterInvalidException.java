package leavesc.hello.retrofit2_rxjava2.http.basis.exception;

import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpCode;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/25 21:39
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "参数有误");
    }

}
