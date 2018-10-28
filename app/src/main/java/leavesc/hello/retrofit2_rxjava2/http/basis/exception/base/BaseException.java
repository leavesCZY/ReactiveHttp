package leavesc.hello.retrofit2_rxjava2.http.basis.exception.base;

import leavesc.hello.retrofit2_rxjava2.http.basis.HttpCode;

/**
 * 作者：叶应是叶
 * 时间：2018/10/25 21:31
 * 描述：
 */
public class BaseException extends RuntimeException {

    private int errorCode = HttpCode.CODE_UNKNOWN;

    public BaseException() {
    }

    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}