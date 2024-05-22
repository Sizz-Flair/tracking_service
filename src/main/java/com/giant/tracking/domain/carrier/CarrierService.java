package com.giant.tracking.domain.carrier;

import com.giant.tracking.entity.CarrierInvoice;
import com.giant.tracking.entity.QCarrierInvoice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * packageName    : com.giant.tracking.domain.carrier
 * fileName       : CarrierService
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@Service
@RequiredArgsConstructor
public class CarrierService {
    private final JPAQueryFactory em;

    @Transactional(readOnly = true)
    public CarrierInvoice findByNumber(final String deliveryOrderNumber) {
        CarrierInvoice carrierInvoice = em.select(QCarrierInvoice.carrierInvoice)
                .from(QCarrierInvoice.carrierInvoice)
                .where(QCarrierInvoice.carrierInvoice.deliveryOrder.externalNumber.eq(deliveryOrderNumber))
                .fetchOne();

        return carrierInvoice;
    }

    @Transactional(readOnly = true)
    public CarrierInvoice findByCarrierNumber(final String number) {
        CarrierInvoice carrierInvoice = em.select(QCarrierInvoice.carrierInvoice)
                .from(QCarrierInvoice.carrierInvoice)
                .where(QCarrierInvoice.carrierInvoice.number.eq(number)).fetchOne();

        return carrierInvoice;
    }

    @Transactional(readOnly = true)
    public List<CarrierInvoice> findByOrderNumberList(final List<String> deliveryOrderList) {
        assert !deliveryOrderList.isEmpty();

        List<CarrierInvoice> carrierInvoices = em.select(QCarrierInvoice.carrierInvoice)
                .from(QCarrierInvoice.carrierInvoice)
                .where(QCarrierInvoice.carrierInvoice.deliveryOrder.externalNumber.in(deliveryOrderList))
                .fetch();

        return Collections.unmodifiableList(carrierInvoices);
    }
}
