package hcmut.spss.be.repository;

import hcmut.spss.be.dtos.response.PrintJobStats;
import hcmut.spss.be.entity.printJob.PrintJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PrintJobRepository extends JpaRepository<PrintJob, Long> {
    List<PrintJob> findAllByStudent_UserId(Long userId);
    @Query("SELECT p FROM PrintJob p WHERE p.student.userId = :userId " +
            "AND FUNCTION('date_part', 'month', p.jobStartTime) = CAST(:month AS double)")
    List<PrintJob> findAllByStudent_UserIdAndJobStartTimeMonth(@Param("userId") Long userId,
                                                               @Param("month") double month);

    @Query(value = "SELECT * FROM print_job " +
            "WHERE EXTRACT(YEAR FROM job_start_time) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(WEEK FROM job_start_time) = EXTRACT(WEEK FROM CURRENT_DATE)",
            nativeQuery = true)
    List<PrintJob> findAllPrintJobsThisWeek();

    @Query(value = "SELECT EXTRACT(DOW FROM p.job_start_time) + 1, SUM(p.number_page_print), COUNT(*) " +
            "FROM print_job p " +
            "WHERE p.job_start_time >= :startOfWeek AND p.job_start_time < :endOfWeek " +
            "GROUP BY EXTRACT(DOW FROM p.job_start_time)",
            nativeQuery = true)
    List<Object[]> countPrintJobsPerDayInWeek(@Param("startOfWeek") LocalDateTime startOfWeek,
                                              @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query(value = "SELECT SUM(number_page_print) FROM print_job " +
            "WHERE job_start_time >= :startOfDay AND job_start_time < :endOfDay",
            nativeQuery = true)
    Integer countPrintPageInDay(@Param("startOfDay") LocalDateTime startOfDay,
                                @Param("endOfDay") LocalDateTime endOfDay);

    @Query(value = "SELECT DATE(job_start_time) AS print_date, SUM(number_page_print) AS total_pages " +
            "FROM print_job " +
            "GROUP BY DATE(job_start_time) " +
            "ORDER BY print_date ASC", nativeQuery = true)
    List<Object[]> countPrintPagesByDay();

    @Query(value = "SELECT DATE(job_start_time) AS print_date, SUM(number_page_print) AS total_pages, COUNT(*) AS total_print " +
            "FROM print_job " +
            "GROUP BY DATE(job_start_time) " +
            "ORDER BY print_date ASC", nativeQuery = true)
    List<Object[]> countPrintJobByDay();
}
