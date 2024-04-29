package com.groovus.www.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
abstract class BaseEntity {
    //테이블에 공통되는 부분을 상속해줄 클래스

    @CreatedDate // 게시물 생성할 때 동작
    @Column(name = "regdate" , updatable = false) //db테이블에 필드명 지정 , 업데이트 안됨
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regDate ; //게시물 등록 시간

    @LastModifiedDate //게시물 수정시 동작
    @Column(name = "moddate") // db테이블에 필드명 지정
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modDate ; //게시물 수정 시간

}
