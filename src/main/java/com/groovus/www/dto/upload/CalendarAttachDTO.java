package com.groovus.www.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarAttachDTO implements Serializable { // 여러개의 파일이 업로드되면 결과도 여러개 발생 하게 되고 여러 정보로 반환해야 함
    // 별도의 dto를 구성하여 객체로 반환 처리 용

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL(){
        // 차후에 json 처리 될 때 link라는 속성으로 자동 처리
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}