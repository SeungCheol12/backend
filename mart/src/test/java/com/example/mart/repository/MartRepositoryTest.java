package com.example.mart.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;

@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void insertItemTest() {
        // 5개의 Item 삽입
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder().name("Item" + i)
                    .price(50000)
                    .quantity(i * 5)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void insertMemberTest() {
        // 5개의 Item 삽입
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder().name("user" + i)
                    .city("서울")
                    .street("570-6" + i)
                    .zipcode("1650" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void orderTest() {
        // 주문

        // 1번 상품을 2번 고객이 주문

        Member member = memberRepository.findById(2L).get();
        Item item = itemRepository.findById(1L).get();

        Order order = Order.builder().member(member).orderStatus(OrderStatus.ORDER).build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item).order(order).orderPrice(50000)
                .count(1).build();
        orderItemRepository.save(orderItem);
    }

    @Transactional(readOnly = true)
    @Test
    public void orderReadTest() {
        // 2번 고객 주문 내역 조회
        Member member = Member.builder().id(2L).build();
        Order order = orderRepository.findByMember(member).get();

        System.out.println(order);

        order.getOrderItems().forEach(i -> {
            System.out.println(i);
            // 주문상품 상세 정보 조회
            System.out.println(i.getItem());
        });
    }

}
