package com.rabbiter.bms.exception;

/**
 * 库存不足异常
 * 也就是 设备已经借走
 */
public class NotEnoughException extends RuntimeException{
    public NotEnoughException() {
        super();
    }

    public NotEnoughException(String message) {
        super(message);
    }
}
