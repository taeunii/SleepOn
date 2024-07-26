package bitc.fullstack.sleepon.repository;

import bitc.fullstack.sleepon.model.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {
}
