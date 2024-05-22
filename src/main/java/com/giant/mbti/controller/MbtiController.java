package com.giant.mbti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.giant.mbti.controller
 * fileName       : MbtiController
 * author         : akfur
 * date           : 2024-05-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-05-02         akfur
 */
@Controller
public class MbtiController {


    @GetMapping("/ujoong")
    public String mbtiTest() {
        return "/ujoong";
    }
}
