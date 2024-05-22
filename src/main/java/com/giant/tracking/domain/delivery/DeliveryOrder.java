package com.giant.tracking.domain.delivery;

import com.giant.tracking.entity.CarrierInvoice;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : com.giant.tracking.domain.delivery
 * fileName       : DeliveryOrder
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@Entity
@Getter
public class DeliveryOrder {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, name="order_number")
    private String orderNumber;

    @Column(name="external_number")
    private String externalNumber;


    @Column(name="order_name")
    private String orderName;

    @Column(name="order_tel")
    private String orderTel;


    @Column(name="carrier_code")
    private String carrierCode;

    @Builder.Default
    @Column(nullable = false)
    // private int prio = OrderPrio.NORMAL;
    private int prio = 50;

    @Column(length = 2000, name="picking_hint")
    private String pickingHint;

    @Column(length = 2000, name="packing_hint")
    private String packingHint;

    @Builder.Default
    private Instant started = Instant.now(Clock.systemUTC());

    private Instant finished;

    @Column(precision = 16, scale = 3)
    private BigDecimal weight;

    @Column(precision = 19, scale = 6)
    private BigDecimal volume;

    @Column(name="shipping_dt1")
    private Instant shippingDt1;

    @Column(name="shipping_dt2")
    private Instant shippingDt2;

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name="delivery_zip")
    private String deliveryZip;

    @Column(name="delivery_address")
    private String deliveryAddress;

    @Column(name="delivery_state")
    private String deliveryState;

    @Column(name="delivery_city")
    private String deliveryCity;

    @Column(name="delivery_street")
    private String deliveryStreet;

    @Column(name="delivery_address_detail")
    private String deliveryAddressDetail;

    @Column(name="delivery_name")
    private String deliveryName;

    @Column(name="delivery_tel")
    private String deliveryTel;

    @Column(name="delivery_tel2")
    private String deliveryTel2;

    @Column(name="delivery_number")
    private String deliveryNumber;

    @Builder.Default
    @Column(name="out_type")
    private String outType = "OT11";	// 출고구분(OT11-B2C, OT12-B2B, OT21-정품, OT22-비품)

    @Column(name="error_msg")
    private String errorMsg;

    @Column(name="channel_name")
    private String channelName;

    @Column(name="delivery_memo")
    private String deliveryMemo;

    @Column(name="solution_name")
    private String solutionName;

    @Column(name="op_number")
    private String opNumber;

    @Column(name="account_name")
    private String accountName;
}
