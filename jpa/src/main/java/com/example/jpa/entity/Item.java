package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.SellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 테이블명 : itemtbl
// 컬럼 : 상품코드(code - P0001), 상품명(item_nm), 가격(item_price), 재고수량(stock_number)
//        상세설명(item_detail), 판매상태(item_sell_status) : SELL, SOLDOUT
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Builder
@Table(name = "itemtbl")
public class Item {

    @Id
    private String code;

    @Column(nullable = false)
    private String itemNm;

    @Column(nullable = false)
    private int itemPrice;

    @Column(nullable = false)
    private int stockNumber;

    private String itemDetail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SellStatus itemSellStatus;

    @CreatedDate
    private LocalDateTime createDateTime2;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public void changeItemStatus(SellStatus itemSellStatus) {
        this.itemSellStatus = itemSellStatus;
    }

    public void changeStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

}
