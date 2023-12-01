package cn.ilovecry.cloudpractice.common;

/**
 * R
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/11/30 20:03
 */
public class R<T> {
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;
    public static final int UNAUTHORIZED_CODE = 401;
    private int code;
    private String message;
    private T data;

    public R() {
        this.code = SUCCESS_CODE;
    }

    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> success(T data) {
        return new R<T>(SUCCESS_CODE, "success", data);
    }

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> error(int code, String message) {
        return new R<T>(code, message, null);
    }

    public static <T> R<T> error(String message) {
        return error(ERROR_CODE, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
