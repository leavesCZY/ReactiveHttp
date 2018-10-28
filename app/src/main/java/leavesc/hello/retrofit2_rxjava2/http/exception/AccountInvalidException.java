package leavesc.hello.retrofit2_rxjava2.http.exception;

import leavesc.hello.retrofit2_rxjava2.http.HttpCode;
import leavesc.hello.retrofit2_rxjava2.http.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 8:11
 * 描述：
 */
public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super(HttpCode.CODE_ACCOUNT_INVALID, "账号或者密码错误");
    }

}
