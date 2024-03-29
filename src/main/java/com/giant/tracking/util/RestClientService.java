package com.giant.tracking.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.giant.tracking.util
 * fileName       : RestClientService
 * author         : akfur
 * date           : 2024-03-29
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-03-29         akfur
 */
@RequiredArgsConstructor
@Component
public class RestClientService {
    public String postFormUrlEncoded(MultiValueMap<String, Object> parts) {

        ResponseEntity<String> response = RestClient
                .create()
                .post()
                .uri("http://www.efs.asia:200/api/in/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(parts).retrieve().toEntity(String.class);

        return response.getBody();
    }
}
