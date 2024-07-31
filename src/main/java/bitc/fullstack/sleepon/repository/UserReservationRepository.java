package bitc.fullstack.sleepon.repository;

import bitc.fullstack.sleepon.model.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {
    List<UserReservation> findByUserId(String userId);

    @Query("SELECT u FROM UserReservation u WHERE u.user.id = :userId ORDER BY u.idx DESC, u.reservCancel ASC")
    List<UserReservation> findByUserIdOrderByReservDataDesc(@Param("userId") String userId);

    // 지난 예약 목록
    @Query("SELECT u FROM UserReservation u WHERE u.user.id = :userId AND u.reservCancel = 'N' ORDER BY u.idx DESC, u.reservCancel ASC")
    List<UserReservation> findByUserIdLastReserv(@Param("userId") String userId);
}
