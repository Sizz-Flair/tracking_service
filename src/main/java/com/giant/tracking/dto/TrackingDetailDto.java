package com.giant.tracking.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.giant.tracking.dto
 * fileName       : TrackingDetailDto
 * author         : akfur
 * date           : 2024-04-01
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-01         akfur
 */
@Builder
@Getter
public class TrackingDetailDto {
    private String hwb;
    private String deliveryStatusCode;
    private String deliveryCon;
    private String deliveryStatus;
    private String deliveryDate;
}