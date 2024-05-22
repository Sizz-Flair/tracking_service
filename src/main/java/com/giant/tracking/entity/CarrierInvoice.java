package com.giant.tracking.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giant.tracking.domain.delivery.DeliveryOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * packageName    : com.giant.tracking.entity
 * fileName       : CarrierInvoice
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@Entity
@Table(name = "carrier_invoice")
@Getter
@ToString
public class CarrierInvoice {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "main_number")
    private String mainNumber;

    @Column(name = "return_number")
    private String returnNumber;

    @Column(name = "carrier_code")
    private String carrierCode;

    @Column(name = "shipper_name")
    private String shipperName;

    @Column(name = "shipper_tel")
    private String shipperTel;

    @Column(name = "shipper_address")
    private String shipperAddress;

    @Column(name = "shipper_address_detail")
    private String shipperAddressDetail;

    @Column(name = "shipper_zip")
    private String shipperZip;

    @Column(name = "consignee_name")
    private String consigneeName;

    @Column(name = "consignee_tel")
    private String consigneeTel;

    @Column(name = "consignee_tel2")
    private String consigneeTel2;

    @Column(name = "consignee_address")
    private String consigneeAddress;

    @Column(name = "consignee_address_detail")
    private String consigneeAddressDetail;

    @Column(name = "consignee_zip")
    private String consigneeZip;

    @Column(name = "goods_names")
    private String goodsNames;

    @Column(name = "goods_qty")
    private Long goodsQty;

    @Column(name = "goods_weight")
    private BigDecimal goodsWeight;

    @Column(name = "goods_volume")

    private BigDecimal goodsVolume;

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    @Column(name = "goods_brands")
    private String goodsBrands;

    @Column(name = "send_dt")
    private Instant sendDt;

    @Column(name = "send_return_dt")
    private Instant sendReturnDt;

    @Column(name = "print_data")
    private String printData;

    @Column(name = "error_msg")
    private String errorMsg;

    private Instant started;

    private Instant finished;

    @Column(name = "delivery_memo")
    private String deliveryMemo;

    @Column(name = "driver")
    private String driver;

    @Column(name = "driver_com")
    private String driverCom;

    @Column(name = "driver_tel")
    private String driverTel;

    @Column(name = "return_reason")
    private String returnReason;

    @Column(name = "request_return_dt")
    private Instant requestReturnDt;

    @Column(name = "scg_tracking_start")
    private String scgTrackingStart;

    @Column(name = "agent_name_modified")
    private String agentNameModified;

    @Column(name = "shipper_addr_modified")
    private String shipperAddrModified;


    @Fetch(FetchMode.JOIN)
    @ManyToOne(optional = true, fetch = FetchType.LAZY) // default fetch type = EAGER
    @JoinColumn(name = "delivery_order_id")
    private DeliveryOrder deliveryOrder;
}
