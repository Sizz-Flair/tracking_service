package com.giant.tracking.domain.carrier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.giant.tracking.domain.carrier
 * fileName       : CarrierServiceTest
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@SpringBootTest
class CarrierServiceTest {
    @Autowired
    CarrierService carrierService;

    @Test
    @DisplayName("업체 주문번호로 CarrierInvoice 검색")
    public void findByExNumber() {
        carrierService.findByNumber("BIGC-1708082297-69548");
    }
}