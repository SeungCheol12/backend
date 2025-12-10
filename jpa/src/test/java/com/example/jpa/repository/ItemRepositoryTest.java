package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.constant.SellStatus;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertTest() {
        for (int i = 1; i < 10; i++) {
            Item item = Item.builder()
                    .code("P00" + i)
                    .itemPrice(1000 * i)
                    .stockNumber(10)
                    .itemDetail("Item Detail" + i)
                    .itemSellStatus(SellStatus.SELL)
                    .itemNm("Item" + i)
                    .build();

            itemRepository.save(item);
        }
    }

    @Test
    public void updateTes1t() {
        // item 상태
        Optional<Item> result = itemRepository.findById("P005");

        result.ifPresent(item -> {
            item.changeItemStatus(SellStatus.SOLDOUT);
            itemRepository.save(item);
        });
    }

    @Test
    public void updateTest2() {
        // 재고수량 변경
        // 1번과 같은 코드를 다르게 표현했다
        Item item = itemRepository.findById("P006").get();
        item.changeStockNumber(5);
        itemRepository.save(item);
    }

    @Test
    public void deleteTest() {
        itemRepository.deleteById("9");
    }

    @Test
    public void readTest() {
        System.out.println(itemRepository.findById("P009").get());
    }

    @Test
    public void readTest2Test() {
        itemRepository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void aggrTest() {
        List<Object[]> list = itemRepository.aggr();
        for (Object[] objects : list) {
            System.out.println("아이템의 수 " + objects[0]);
            System.out.println("합계 " + objects[1]);
            System.out.println("평균 " + objects[2]);
            System.out.println("최대 " + objects[3]);
            System.out.println("최소 " + objects[4]);
        }
    }
}
