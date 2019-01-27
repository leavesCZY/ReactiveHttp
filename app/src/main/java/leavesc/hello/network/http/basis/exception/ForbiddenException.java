package leavesc.hello.network.http.basis.exception;

import leavesc.hello.network.http.basis.config.HttpCode;
import leavesc.hello.network.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/25 21:46
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "404错误");
    }

}