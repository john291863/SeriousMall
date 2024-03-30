package com.example.seriousMall.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UploadFileModel {
    private String extraField;

    /**
     *
     * 單一檔案使用MultipartFile資料型態
     * 多檔案上傳可使用MultipartFile
     */
    private MultipartFile[] files;
}
