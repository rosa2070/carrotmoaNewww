package carrotmoa.carrotmoa;

import carrotmoa.carrotmoa.repository.AccommodationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class test {
    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    void find(){
//        accommodationRepository.findAccommodationsWithImagesAndSpaceCount("456");
    }
}