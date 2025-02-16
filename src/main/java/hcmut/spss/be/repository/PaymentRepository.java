package hcmut.spss.be.repository;

import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudent(User student);

    @Query(value = "SELECT SUM(num_pages_bought) FROM payment " +
            "WHERE payment_date >= :startOfDay AND payment_date < :endOfDay",
            nativeQuery = true)
    Integer countPapersInDay(@Param("startOfDay") LocalDateTime startOfDay,
                             @Param("endOfDay") LocalDateTime endOfDay);


    @Query(value = "SELECT EXTRACT(MONTH FROM p.payment_date) AS month, " +
            "EXTRACT(YEAR FROM p.payment_date) AS year, " +
            "SUM(p.amount_paid) AS totalAmount " +
            "FROM payment p " +
            "WHERE p.payment_date >= CURRENT_DATE - INTERVAL '6 months' " +
            "GROUP BY year, month " +
            "ORDER BY year DESC, month DESC", nativeQuery = true)
    List<Object[]> countTotalAmountLast6Months();


    @Query(value = "SELECT CAST(payment_date AS DATE) AS payment_date, " +
            "COUNT(*) AS num_of_payments, " +
            "SUM(amount_paid) AS amount_paid, " +
            "SUM(num_pages_bought) AS num_pages_bought " +
            "FROM payment " +
            "GROUP BY CAST(payment_date AS DATE) " +
            "ORDER BY payment_date ASC",
            nativeQuery = true)
    List<Object[]> countTodayPayments();
}
