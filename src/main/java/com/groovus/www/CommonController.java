package com.groovus.www;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class CommonController {

    @GetMapping("/main")
    public void main() {
        log.info("메인페이지 이동");
    }

}
