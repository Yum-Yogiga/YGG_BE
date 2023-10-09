package com.yogiga.yogiga.global.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3 client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    //파일명 중복방지
    private String createUniqueFileName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    public String uploadImage(MultipartFile multipartFile) {
        String uniqueFileName = createUniqueFileName(multipartFile.getOriginalFilename());
        // 메타데이터 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            client.putObject(new PutObjectRequest(bucket, uniqueFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.error("S3 파일 업로드에 실패했습니다. {}", e.getMessage());
            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR, "파일 업로드를 실패했습니다. ");
        }
        return client.getUrl(bucket, uniqueFileName).toString();

    }

    public void deleteImage(String uniqueFileName)  {
        client.deleteObject(bucket, uniqueFileName);
    }

}
