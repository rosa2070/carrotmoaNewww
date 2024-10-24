package carrotmoa.carrotmoa.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pension")
@Controller
public class PensionViewController {

    @GetMapping
    public String getFleaMarket() {
        // pension 시작하면서 추천에 걸릴 매물 여기에서 미리 던져주기
        return "pension";
    }

    // 추천 매물 테이블에서 위치정보 가져와서 띄우기
}
