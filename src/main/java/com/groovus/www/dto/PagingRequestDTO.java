package com.groovus.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingRequestDTO {

    private String direction; //페이징 방향 나타내는 필드
    private Long cursorId; //페이징 커서 id 나타내는 필드
}
