package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.SaveFleaMarketPostRequest;
import carrotmoa.carrotmoa.service.FleaMarketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "중고거래", description = "중고거래 페이지")
@RequestMapping("/api/fleamarket")
@RestController
public class FleaMarketController {

    @Autowired
    FleaMarketService fleaMarketService;

    @PostMapping
    public Long createPost(@RequestBody SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        //TODO : 로그인된 유저의 id를 가져오는 과정 추가 필요
        return fleaMarketService.savePost(saveFleaMarketPostRequest);
    }
}
