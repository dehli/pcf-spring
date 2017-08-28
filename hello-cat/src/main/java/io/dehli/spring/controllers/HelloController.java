package io.dehli.spring.controllers;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    int counter = 0;

    @RequestMapping("/")
    public ResponseEntity<byte[]> index() {
        try {
            URL url = new URL("http://thecatapi.com/api/images/get?format=src");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String contentType = connection.getContentType();
            InputStream in = connection.getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", contentType);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("/counter")
    public HashMap<String, Integer> counter() {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("x", counter++);
        return result;
    }
}
