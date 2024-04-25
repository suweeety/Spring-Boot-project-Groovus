package com.groovus.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriveFileDTO {

    private String uuid;

    private String fName;

    private String path;

    public String getFileURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+fName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(path+"/s_"+uuid+"_"+fName,"UTF-8");
        } catch (UnsupportedEncodingException e){

        }
        return "";
    }
}
