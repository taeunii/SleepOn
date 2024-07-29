package bitc.fullstack.sleepon.repository;

import bitc.fullstack.sleepon.model.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {
    List<UserReservation> findByUserId(String userId);
}
