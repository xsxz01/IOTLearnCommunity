package cn.iotlearn.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你要找的问题不在了，换一个试试。");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
