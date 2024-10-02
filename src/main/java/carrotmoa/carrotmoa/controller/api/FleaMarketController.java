package carrotmoa.carrotmoa.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/fleamarket")
@RestController
public class FleaMarketController {

    @GetMapping
    public void test() {
        System.out.println("test");
    }
}
