package com.giant.tracking.controller;

import com.giant.tracking.dto.TrackingDto;
import com.giant.tracking.util.RestClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * packageName    : com.giant.tracking.controller
 * fileName       : TrackingController
 * author         : akfur
 * date           : 2024-03-29
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-03-29         akfur
 */
@Controller
@RequiredArgsConstructor
public class TrackingController {

    private final RestClientService restClientService;

    @GetMapping("/tracking")
    public String trackingPage() {
        return "/tracking";
    }

    @GetMapping("/test")
    public String trackingInfo(Model model, @RequestParam("hwbNo") String hwbNo) {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("apikey", "935ac5b93ac1faf3d8beb75c7b76dae0");
        parts.add("req_function","getTrackStatus");
        parts.add("send_data","EFS1004061775");
        String data = restClientService.postFormUrlEncoded(parts);

        String[] responseDataSplit = data.split("\\|");
        String[] hwbInfo = responseDataSplit[1].split("\r\n");
        String conInfo = responseDataSplit[11].split("\r\n")[0];

        TrackingDto trackingDto = TrackingDto.builder()
                .hwb(hwbInfo[1]).agentName(responseDataSplit[3])

        System.out.println(hwbNo);
        List<String> modelList = new ArrayList<>();

        model.addAttribute("data", data.split("\\|"));
        model.addAttribute("hwbNo", hwbNo);

        return "/trackingList";
    }
}
