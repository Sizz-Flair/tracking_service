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
        parts.add("send_data",hwbNo);
        String data = restClientService.postFormUrlEncoded(parts);

        String[] responseDataSplit = data.split("\\|");
        String[] hwbInfo = responseDataSplit[1].split("\r\n");
        String conInfo = responseDataSplit[11].split("\r\n")[0];

//        0		Post 데이터 확인 메시지
//        1		송장번호(예약번호)
//        2		발송품 참조 번호
//        3		배송 회사명
//        4		배송 번호
//        5		최종 배송 상태 코드1)
//        6		최종 배송 상태 값1)
//        7		최종 배송 상태 일시
//        8		실측 무게 2)
//        9		적용 서비스 타입 3)
//        10		배송비 4)
//        11		배송 상태 국가


        TrackingDto trackingDto = TrackingDto.builder()
                .hwb(hwbInfo[1])
                .agentName(responseDataSplit[3])
                .deliveryStatusCode(responseDataSplit[5])
                .deliveryStatus(responseDataSplit[6])
                .deliverDay(responseDataSplit[7])
                .deliveryCode(responseDataSplit[4])
                .deliveryWeight(responseDataSplit[8])
                .DeliveryPrice(responseDataSplit[10])
                .deliveryCon(conInfo)
                .build();

        System.out.println(hwbNo);
        List<String> modelList = new ArrayList<>();

        model.addAttribute("data", data.split("\\|"));
        model.addAttribute("trackingDto", trackingDto);
        model.addAttribute("hwbNo", hwbNo);

        return "/trackingList";
    }
}
