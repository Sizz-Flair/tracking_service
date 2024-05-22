package com.giant.tracking.domain.carrier;

import com.giant.tracking.entity.CarrierInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * packageName    : com.giant.tracking.domain.carrier
 * fileName       : CarrierRepository
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@Repository
public interface CarrierRepository extends JpaRepository<CarrierInvoice, Long> {
    CarrierInvoice findByNumber(String number);
}
