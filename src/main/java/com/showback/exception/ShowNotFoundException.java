package com.showback.exception;

public class ShowNotFoundException extends RuntimeException {
    public ShowNotFoundException(Long showId) {
        super("올바른 회원를 찾을 수 없습니다.: " + showId);
    }
}
