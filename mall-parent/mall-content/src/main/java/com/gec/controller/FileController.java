package com.gec.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin("*") //跨域访问
public class FileController   {

    @Value("mall-xww")
    private String bucketName;
    @Value("oss-cn-beijing.aliyuncs.com")
    private String endpoint;
    @Value("LTAI5tDfijtMizY8tmosHApD")
    private String accessKey;
    @Value("nJMgvJwJHuhZuyAKKxGj9yOU8fMuyX")
    private String secretKey;
    @Value("https://mall-xww.oss-cn-beijing.aliyuncs.com")
    private String bucketEndpoint;

    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile file) {
        //创建ossClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKey,secretKey);
        //创建Bucket
        if (ossClient.doesBucketExist(bucketName)) {
            System.out.println("你已创建Bucket: " + bucketName);
        } else {
            ossClient.createBucket(bucketName);
        }
        System.out.println(file.getOriginalFilename());
        //设计一个存储对象（路径+文件名）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filename = file.getOriginalFilename();
        String objectName = sdf.format(new Date()) + "/" + filename;

        //存储对象
        try {
            ossClient.putObject(bucketName,objectName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String httpUrl = bucketEndpoint + "/" + objectName;
        System.out.println(httpUrl);
        return httpUrl;
    }
}