package com.groovus.www.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarAttachDTO {

    private String uuid;
    private String fileName;
    private boolean img;
    public String getLink() {
        if(img) {
            return "s_" + uuid + "_" + fileName; // 이미지인 경우에 섬네일 사용
        } else {
            return uuid + "_" + fileName;
        }
    }
}
