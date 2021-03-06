package org.synyx.urlaubsverwaltung.core.overtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.synyx.urlaubsverwaltung.core.person.Person;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Allows access to overtime records.
 *
 * @author  Aljona Murygina - murygina@synyx.de
 * @since  2.11.0
 */
public interface OvertimeDAO extends JpaRepository<Overtime, Integer> {

    List<Overtime> findByPerson(Person person);


    @Query("SELECT SUM(overtime.hours) FROM Overtime overtime WHERE overtime.person = :person")
    BigDecimal calculateTotalHoursForPerson(@Param("person") Person person);


    @Query(
        "SELECT overtime FROM Overtime overtime WHERE overtime.person = :person "
        + "AND ((overtime.startDate BETWEEN :start AND :end) "
        + "OR (overtime.endDate BETWEEN :start AND :end) "
        + "OR (overtime.startDate < :start and overtime.endDate > :end)) "
        + "ORDER BY overtime.startDate"
    )
    List<Overtime> findByPersonAndPeriod(@Param("person") Person person,
        @Param("start") Date start,
        @Param("end") Date end);
}
