package com.example.movietalk.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Setter
public class MovieImageDTO {
    private Long inum;

    private String uuid;

    private String path;

    private String imgName;

    // 썸네일 이미지
    public String getThumbnailURL() {
        // null 체크 추가
        if (path == null || uuid == null || imgName == null) {
            return null;
        }
        String thumbFullPath = "";
        // java.net.URL~
        try {
            thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return thumbFullPath;
    }

    public String getImageURL() {
        // null 체크 추가
        if (path == null || uuid == null || imgName == null) {
            return null;
        }
        String fullPath = "";

        // java.net.URL~
        try {
            fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fullPath;
    }
}
