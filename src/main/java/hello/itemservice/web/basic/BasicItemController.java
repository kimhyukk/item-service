package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemid}")
    public String item(@PathVariable long itemid, Model model) {
        Item findItem = itemRepository.findById(itemid);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }


//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }


//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model) {

        itemRepository.save(item);

//        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String update(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";

    }


    /**
     * ModelAttribute 생략 가능 클래스이름의 첫 글자를 소문자로 하면 모델이름이됨 (Item -> item)
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item) {

        itemRepository.save(item);

//        model.addAttribute("item", item);

        return "redirect:/basic/items/"+item.getId();
    }


    /**
     * redirectAttributes.addAttribute("name", "string") --> 대체할 "name"이 있으면 대체 // 없으면 쿼리파라미터로 넘어감 ?name=string
     */
    @PostMapping("/add")
    public String addItemV4(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 5));
        itemRepository.save(new Item("itemB", 20000, 10));
    }

}
