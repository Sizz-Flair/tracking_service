package com.giant.tracking.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.giant.tracking.dto
 * fileName       : TrackingCjDto
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
public class TrackingCjDto {
    private String hwb;
    private String date;
    private String time;
    private String Area;
    private String status;
}
