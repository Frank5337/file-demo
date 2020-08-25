package com.zbl.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zbl
 * @Date: Created in 17:01 2020/8/25
 * @Description:
 * @Version: $
 */
@Controller
@RequestMapping("/upload")
public class UpLoadController {

    @GetMapping
    public String toPage() {
        return "upload";
    }
}
