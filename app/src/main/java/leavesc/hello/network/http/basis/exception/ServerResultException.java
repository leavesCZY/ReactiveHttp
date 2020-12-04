package leavesc.hello.network.http.basis.exception;

import leavesc.hello.network.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/27 8:14
 * 描述：
 * GitHub：https://github.com/leavesC
 */
public class ServerResultException extends BaseException {

    public ServerResultException(int code, String message) {
        super(code, message);
    }

}
