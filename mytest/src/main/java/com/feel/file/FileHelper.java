package com.feel.file;

/*import com.chinaredstar.perseus.utils.PropertiesUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;*/
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;


public class FileHelper {
/*    //应用名
    private static final String APP_NAME = PropertiesUtil.getProperty("system.properties").get("app_name");
    //内部文件服务器地址
    private static final String INSIDE_FILE_HOST = PropertiesUtil.getProperty("system.properties").get("inside_file_host");

    *//**
     * 上传文件
     *
     * @param restTemplate
     * @param userId
     * @param upFile
     * @return 文件url
     *//*
    public static String uploadFile(RestTemplate restTemplate, String userId, MultipartFile upFile) {
        final String name = upFile.getOriginalFilename();
        final long size = upFile.getSize();
        try {
            InputStreamResource file = new InputStreamResource(upFile.getInputStream()) {
                public String getFilename() {
                    return name;
                }

                public long contentLength() throws IOException {
                    return size;
                }
            };

            MultiValueMap map = new LinkedMultiValueMap<>();
            map.add("userId", userId);
            map.add("appName", APP_NAME);
            map.add("file", file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity entity = new HttpEntity(map, headers);
            ResponseEntity<UploadResultVo> resultResponseEntity = restTemplate.postForEntity(INSIDE_FILE_HOST, entity, UploadResultVo.class);
            if (resultResponseEntity.getBody().getCode().equals("200")) {
                String fileUrl = (String) resultResponseEntity.getBody().getValue().get("fileUrl");
//                Long fileId = (Long) resultResponseEntity.getBody().getValue().get("fileId");
                return fileUrl;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void downloadFile(String fileUrl, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //设置文件MIME类型
        response.setContentType(request.getServletContext().getMimeType(fileName));
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 构造URL
        URL url = new URL(fileUrl);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(60 * 1000);
        // 输入流
        InputStream in = con.getInputStream();
        //读取文件
        OutputStream out = response.getOutputStream();

        //写文件
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }

        in.close();
        out.close();
    }

    public static class UploadResultVo {
        private String code;
        private Map<String, Object> value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Map<String, Object> getValue() {
            return value;
        }

        public void setValue(Map<String, Object> value) {
            this.value = value;
        }
    }*/
}
