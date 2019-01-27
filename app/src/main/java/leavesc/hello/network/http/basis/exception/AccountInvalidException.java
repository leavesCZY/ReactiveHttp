package leavesc.hello.network.http.basis.exception;

import leavesc.hello.network.http.basis.config.HttpCode;
import leavesc.hello.network.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/27 8:11
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super(HttpCode.CODE_ACCOUNT_INVALID, "账号或者密码错误");
    }

}
