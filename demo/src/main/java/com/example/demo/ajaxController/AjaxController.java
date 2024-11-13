package com.example.demo.ajaxController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AjaxController {

    @GetMapping("/getData")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("服务器返回的数据");
    }

    @PostMapping("/postData")
    public ResponseEntity<String> postData(@RequestBody String data) {
        return ResponseEntity.ok("收到的数据: " + data);
    }
}