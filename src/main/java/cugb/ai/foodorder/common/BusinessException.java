package cugb.ai.foodorder.common;

public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String detailMsg) {
        super(detailMsg);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
