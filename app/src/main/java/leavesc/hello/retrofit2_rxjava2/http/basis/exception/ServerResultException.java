package leavesc.hello.retrofit2_rxjava2.http.basis.exception;

import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 8:14
 * 描述：
 */
public class ServerResultException extends BaseException {

    public ServerResultException(int code, String message) {
        super(code, message);
    }

}
