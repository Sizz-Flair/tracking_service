package com.giant.tracking.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.giant.tracking.dto
 * fileName       : TrackingDto
 * author         : akfur
 * date           : 2024-03-29
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-03-29         akfur
 */
@Builder
@Getter
public class TrackingDto {
    private String hwb;
    private String agentName;
    private String deliveryNumber;
    private String deliveryCode;
    private String deliveryStatus;
    private String deliveryStatusCode;
    private String deliverDay;
    private String deliveryWeight;
    private String DeliveryPrice;
    private String deliveryCon;
}
