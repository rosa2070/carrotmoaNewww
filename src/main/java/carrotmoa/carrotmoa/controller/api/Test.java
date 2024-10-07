package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.SaveHitRequest;
import carrotmoa.carrotmoa.service.HitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
@RequiredArgsConstructor
public class Test {

    @Autowired
    HitService hitService;

    @PostMapping
    public Long test(@RequestBody SaveHitRequest saveHitRequest) {
        return hitService.save(saveHitRequest);
    }

}
