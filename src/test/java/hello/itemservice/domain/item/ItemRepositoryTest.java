package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();


    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 1000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }


    @Test
    void findAll() {
        Item itemA = new Item("itemA", 2000, 10);
        Item itemB = new Item("itemB", 3000, 5);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        List<Item> itemList = itemRepository.findAll();

        Assertions.assertThat(itemList.size()).isEqualTo(2);
        Assertions.assertThat(itemList).contains(itemA, itemB);
    }

    @Test
    void update() {
        Item itemA = new Item("itemA", 2000, 10);

        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();


        Item updateParam = new Item("itemB", 3000, 5);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}