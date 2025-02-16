package hcmut.spss.be.repository;

import hcmut.spss.be.entity.user.UserVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

    @Query(value = "SELECT COUNT(*) FROM user_visit " +
            "WHERE creation_date >= :startOfDay AND creation_date < :endOfDay",
            nativeQuery = true)
    Integer countVisitsInDay(@Param("startOfDay") LocalDateTime startOfDay,
                             @Param("endOfDay") LocalDateTime endOfDay);


    @Query(value = "SELECT CAST(creation_date AS DATE) AS visit_date, COUNT(*) AS visit_count " +
            "FROM user_visit " +
            "GROUP BY CAST(creation_date AS DATE) " +
            "ORDER BY visit_date ASC",
            nativeQuery = true)
    List<Object[]> countVisitsByDay();

}
