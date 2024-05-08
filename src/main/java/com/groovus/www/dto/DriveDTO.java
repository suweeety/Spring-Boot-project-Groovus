package com.groovus.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriveDTO {

    private Long bno;
    private String title;

    @Builder.Default //화면에 이미지들도 같이 수집을 해야 함 리스트 이용해서 수집
    //빌더로 인스턴스 생성시 초기화할 값을 정할수 있다.
    private List<DriveImageDTO> imageDTOList = new ArrayList<>();

    private LocalDateTime regDate ;

    private LocalDateTime modDate ;

}
