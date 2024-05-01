package com.groovus.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> { //제네릭 타입 T를 갖는 클래스

    private boolean success; //성공 여부를 나타내는 필드
    private T data; //데이터를 담는 필드
    private Error error; //에러정보를 담는 필드

    //성공한 응답을 생성하는 정적 팩토리 메서드
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(true, data, null);
    }

    //실패한 응답을 생성하는 정적 팩토리 메서드
    public static <T> ResponseDTO<T> fail(String code, String message) {
        return new ResponseDTO<>(false, null, new Error(code, message));
    }
    @Getter
    @AllArgsConstructor
    static class Error {
        private String code; //에러 코드
        private String message; //에러 메시지
    }

}
