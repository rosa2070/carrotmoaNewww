package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Hit;
import carrotmoa.carrotmoa.model.request.SaveHitRequest;
import carrotmoa.carrotmoa.repository.HitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HitService {

    @Autowired
    HitRepository hitRepository;

    public Long save(SaveHitRequest saveHitRequest) {
        Hit hit = hitRepository.save(saveHitRequest.toHitEntity());

        return hit.getId();
    }
}
