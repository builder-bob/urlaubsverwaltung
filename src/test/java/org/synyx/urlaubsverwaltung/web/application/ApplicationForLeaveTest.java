package org.synyx.urlaubsverwaltung.web.application;

import org.joda.time.DateMidnight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.urlaubsverwaltung.core.application.domain.Application;
import org.synyx.urlaubsverwaltung.core.period.DayLength;
import org.synyx.urlaubsverwaltung.core.period.WeekDay;
import org.synyx.urlaubsverwaltung.core.person.Person;
import org.synyx.urlaubsverwaltung.core.workingtime.WorkDaysService;
import org.synyx.urlaubsverwaltung.test.TestDataCreator;

import java.math.BigDecimal;


/**
 * @author  Aljona Murygina - murygina@synyx.de
 */
public class ApplicationForLeaveTest {

    private WorkDaysService calendarService;

    @Before
    public void setUp() {

        calendarService = Mockito.mock(WorkDaysService.class);
    }


    @Test
    public void ensureCreatesCorrectApplicationForLeave() {

        Person person = TestDataCreator.createPerson();

        Application application = TestDataCreator.createApplication(person, new DateMidnight(2015, 3, 3),
                new DateMidnight(2015, 3, 6), DayLength.FULL);

        Mockito.when(calendarService.getWorkDays(Mockito.any(DayLength.class), Mockito.any(DateMidnight.class),
                    Mockito.any(DateMidnight.class), Mockito.any(Person.class)))
            .thenReturn(BigDecimal.TEN);

        ApplicationForLeave applicationForLeave = new ApplicationForLeave(application, calendarService);

        Mockito.verify(calendarService)
            .getWorkDays(application.getDayLength(), application.getStartDate(), application.getEndDate(), person);

        Assert.assertNotNull("Should not be null", applicationForLeave.getStartDate());
        Assert.assertNotNull("Should not be null", applicationForLeave.getEndDate());
        Assert.assertNotNull("Should not be null", applicationForLeave.getDayLength());

        Assert.assertEquals("Wrong start date", application.getStartDate(), applicationForLeave.getStartDate());
        Assert.assertEquals("Wrong end date", application.getEndDate(), applicationForLeave.getEndDate());
        Assert.assertEquals("Wrong day length", application.getDayLength(), applicationForLeave.getDayLength());

        Assert.assertNotNull("Should not be null", applicationForLeave.getWorkDays());
        Assert.assertEquals("Wrong number of work days", BigDecimal.TEN, applicationForLeave.getWorkDays());
    }


    @Test
    public void ensureApplicationForLeaveHasInformationAboutDayOfWeek() {

        Person person = TestDataCreator.createPerson();

        Application application = TestDataCreator.createApplication(person, new DateMidnight(2016, 3, 1),
                new DateMidnight(2016, 3, 4), DayLength.FULL);

        Mockito.when(calendarService.getWorkDays(Mockito.any(DayLength.class), Mockito.any(DateMidnight.class),
                    Mockito.any(DateMidnight.class), Mockito.any(Person.class)))
            .thenReturn(BigDecimal.valueOf(4));

        ApplicationForLeave applicationForLeave = new ApplicationForLeave(application, calendarService);

        Assert.assertNotNull("Missing day of week for start date", applicationForLeave.getWeekDayOfStartDate());
        Assert.assertEquals("Wrong day of week for start date", WeekDay.TUESDAY,
            applicationForLeave.getWeekDayOfStartDate());

        Assert.assertNotNull("Missing day of week for end date", applicationForLeave.getWeekDayOfEndDate());
        Assert.assertEquals("Wrong day of week for end date", WeekDay.FRIDAY,
            applicationForLeave.getWeekDayOfEndDate());
    }
}
