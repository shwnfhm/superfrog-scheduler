package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.appearance.*;
import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserService userService;

    private final AppearanceService appearanceService;

    private final AppearanceRepository appearanceRepository;

    public DBDataInitializer(UserService userService, AppearanceRepository appearanceRepository, AppearanceService appearanceService) {
        this.userService = userService;
        this.appearanceRepository = appearanceRepository;
        this.appearanceService = appearanceService;
    }

    @Override
    public void run(String... args) throws Exception {
        User spiritDirector = new User();
        spiritDirector.setEmail("superfrogschedulercite30363@gmail.com");
        spiritDirector.setRoles("SPIRITDIRECTOR SUPERFROG");
        spiritDirector.setPassword("spiritdirector");
        spiritDirector.setActive(true);
        spiritDirector.setInternational(false);
        spiritDirector.setFirstName("Spirit");
        spiritDirector.setLastName("Director");
        spiritDirector.setPaymentPreference(PaymentPreference.MAILCHECK);
        spiritDirector.setPhoneNumber("4697682085");
        spiritDirector.setAddress("123 P Sherman Way");
        this.userService.save(spiritDirector);

        User u1 = new User();
        u1.setEmail("kendallroy@waystar.com");
        u1.setRoles("SUPERFROG");
        u1.setPassword("superfrog");
        u1.setActive(true);
        u1.setInternational(false);
        u1.setFirstName("Kendall");
        u1.setLastName("Roy");
        u1.setPaymentPreference(PaymentPreference.MAILCHECK);
        u1.setPhoneNumber("4697672085");
        u1.setAddress("123 Waystar Dr");
        this.userService.save(u1);

        Appearance a1 = new Appearance();
        a1.setReqFirstName("Logan");
        a1.setReqLastName("Roy");
        a1.setAddress("123 LtotheOG Way");
        a1.setReqPhoneNumber("8174542857");
        a1.setReqEmail("loganroy@waystar.com");
        a1.setTitle("Company Retreat");
        a1.setEventDate(LocalDate.parse("2023-12-17"));
        a1.setStartTime(LocalTime.parse("17:23:00"));
        a1.setEndTime(LocalTime.parse("19:23:00"));
        a1.setDesc("This is for a lunch reception");
        a1.setOnCampus(false);
        a1.setMileage(99.0);
        a1.setOrgName("Waystar Royco");
        a1.setInstructions("there are none");
        a1.setExpenses("none");
        a1.setOutsideOrg("no outside organizations");
        a1.setStatus(AppearanceStatus.PENDING);
        a1.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a1);

        Appearance a2 = new Appearance();
        a2.setReqFirstName("Roman");
        a2.setReqLastName("Roy");
        a2.setAddress("123 Rome Way");
        a2.setReqPhoneNumber("8174542817");
        a2.setReqEmail("romanroy@waystar.com");
        a2.setTitle("Birthday Party");
        a2.setEventDate(LocalDate.parse("2023-12-18"));
        a2.setStartTime(LocalTime.parse("17:23:00"));
        a2.setEndTime(LocalTime.parse("19:23:00"));
        a2.setDesc("This is for a birthday party");
        a2.setOnCampus(false);
        a2.setMileage(75.0);
        a2.setOrgName("Waystar Royco");
        a2.setInstructions("there are none");
        a2.setExpenses("none");
        a2.setOutsideOrg("no outside organizations");
        a2.setStatus(AppearanceStatus.APPROVED);
        a2.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a2);

        Appearance a3 = new Appearance();
        a3.setReqFirstName("Shiv");
        a3.setReqLastName("Roy");
        a3.setAddress("123 Shiboan Way");
        a3.setReqPhoneNumber("8174542897");
        a3.setReqEmail("shivroy@waystar.com");
        a3.setTitle("Wedding Party");
        a3.setEventDate(LocalDate.parse("2023-12-14"));
        a3.setStartTime(LocalTime.parse("17:23:00"));
        a3.setEndTime(LocalTime.parse("19:23:00"));
        a3.setDesc("This is for a wedding reception");
        a3.setOnCampus(false);
        a3.setMileage(65.0);
        a3.setOrgName("Waystar Royco");
        a3.setInstructions("there are none");
        a3.setExpenses("none");
        a3.setOutsideOrg("no outside organizations");
        a3.setStatus(AppearanceStatus.ASSIGNED);
        a3.setAppearanceType(AppearanceType.PRIVATE);
        a3.setAssignedSuperFrog(u1);
        a3.setAssignedId(u1.getId());
        a3.setAssignedName(u1.getFirstName() + " " + u1.getLastName());
        this.appearanceRepository.save(a3);

        Appearance a4 = new Appearance();
        a4.setReqFirstName("Connor");
        a4.setReqLastName("Roy");
        a4.setAddress("123 Conhead Dr");
        a4.setReqPhoneNumber("8175542897");
        a4.setReqEmail("connorroy@waystar.com");
        a4.setTitle("Funeral");
        a4.setEventDate(LocalDate.parse("2023-12-21"));
        a4.setStartTime(LocalTime.parse("17:23:00"));
        a4.setEndTime(LocalTime.parse("19:23:00"));
        a4.setDesc("This is for a funera");
        a4.setOnCampus(false);
        a4.setMileage(55.0);
        a4.setOrgName("Waystar Royco");
        a4.setInstructions("there are none");
        a4.setExpenses("none");
        a4.setOutsideOrg("no outside organizations");
        a4.setStatus(AppearanceStatus.COMPLETED);
        a4.setAppearanceType(AppearanceType.PRIVATE);
        a4.setAssignedSuperFrog(u1);
        a4.setAssignedId(u1.getId());
        a4.setAssignedName(u1.getFirstName() + " " + u1.getLastName());
        this.appearanceRepository.save(a4);
    }
}
