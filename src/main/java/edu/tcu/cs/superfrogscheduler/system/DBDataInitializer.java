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

    private final AppearanceRepository appearanceRepository;

    private final AppearanceService appearanceService;

    public DBDataInitializer(UserService userService, AppearanceService appearanceService, AppearanceRepository appearanceRepository) {
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
        u1.setPassword("superfrogswag");
        u1.setActive(true);
        u1.setInternational(false);
        u1.setFirstName("Kendall");
        u1.setLastName("Roy");
        u1.setPaymentPreference(PaymentPreference.MAILCHECK);
        u1.setPhoneNumber("4697672085");
        u1.setAddress("123 Waystar Dr");
        this.userService.save(u1);

        User u2 = new User();
        u2.setEmail("barryberkman@hbo.com");
        u2.setRoles("SUPERFROG");
        u2.setPassword("superfrogswag");
        u2.setActive(false);
        u2.setInternational(false);
        u2.setFirstName("Barry");
        u2.setLastName("Berkman");
        u2.setPaymentPreference(PaymentPreference.MAILCHECK);
        u2.setPhoneNumber("8176751210");
        u2.setAddress("123 Sepulveda Dr");
        this.userService.save(u2);

        User u3 = new User();
        u3.setEmail("walterwhite@gmail.com");
        u3.setRoles("SUPERFROG");
        u3.setPassword("superfrogswag");
        u3.setActive(true);
        u3.setInternational(false);
        u3.setFirstName("Walter");
        u3.setLastName("White");
        u3.setPaymentPreference(PaymentPreference.MAILCHECK);
        u3.setPhoneNumber("8176761210");
        u3.setAddress("3708 Negra Arroyo Lane");
        this.userService.save(u3);

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
        this.userService.assign(a3.getRequestId(), u1.getId());

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
        a4.setDesc("This is for a funeral");
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
        this.userService.assign(a4.getRequestId(), u1.getId());
        this.userService.completeAppearance(a4.getRequestId());
        this.appearanceService.complete(a4.getRequestId());

        Appearance a5 = new Appearance();
        a5.setReqFirstName("Gerri");
        a5.setReqLastName("Kellman");
        a5.setAddress("123 Kellman Way");
        a5.setReqPhoneNumber("8171542857");
        a5.setReqEmail("gerrikellman@waystar.com");
        a5.setTitle("Company Brunch");
        a5.setEventDate(LocalDate.parse("2023-12-22"));
        a5.setStartTime(LocalTime.parse("17:23:00"));
        a5.setEndTime(LocalTime.parse("19:23:00"));
        a5.setDesc("This is for a brunch");
        a5.setOnCampus(true);
        a5.setMileage(1.0);
        a5.setOrgName("Waystar Royco");
        a5.setInstructions("there are none");
        a5.setExpenses("none");
        a5.setOutsideOrg("no outside organizations");
        a5.setStatus(AppearanceStatus.PENDING);
        a5.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a5);

        Appearance a6 = new Appearance();
        a6.setReqFirstName("Tom");
        a6.setReqLastName("Wambsgams");
        a6.setAddress("123 Froggy Way");
        a6.setReqPhoneNumber("8174642817");
        a6.setReqEmail("tomw@waystar.com");
        a6.setTitle("Bachelor Party");
        a6.setEventDate(LocalDate.parse("2023-12-20"));
        a6.setStartTime(LocalTime.parse("17:23:00"));
        a6.setEndTime(LocalTime.parse("19:23:00"));
        a6.setDesc("This is for a bachelor party");
        a6.setOnCampus(false);
        a6.setMileage(45.0);
        a6.setOrgName("Waystar Royco");
        a6.setInstructions("there are none");
        a6.setExpenses("none");
        a6.setOutsideOrg("no outside organizations");
        a6.setStatus(AppearanceStatus.APPROVED);
        a6.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a6);

        Appearance a7 = new Appearance();
        a7.setReqFirstName("Jim");
        a7.setReqLastName("Moss");
        a7.setAddress("123 Mossy Dr");
        a7.setReqPhoneNumber("8174642217");
        a7.setReqEmail("jimmoss@hbo.com");
        a7.setTitle("Police Brunch");
        a7.setEventDate(LocalDate.parse("2023-11-20"));
        a7.setStartTime(LocalTime.parse("17:23:00"));
        a7.setEndTime(LocalTime.parse("19:23:00"));
        a7.setDesc("This is for a police brunch");
        a7.setOnCampus(false);
        a7.setMileage(25.0);
        a7.setOrgName("FYPD");
        a7.setInstructions("there are none");
        a7.setExpenses("none");
        a7.setOutsideOrg("no outside organizations");
        a7.setStatus(AppearanceStatus.APPROVED);
        a7.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a7);

        Appearance a8 = new Appearance();
        a8.setReqFirstName("Sally");
        a8.setReqLastName("Reed");
        a8.setAddress("123 Reed Dr");
        a8.setReqPhoneNumber("8174642887");
        a8.setReqEmail("sallyreed@waystar.com");
        a8.setTitle("Acting Class");
        a8.setEventDate(LocalDate.parse("2023-11-19"));
        a8.setStartTime(LocalTime.parse("17:23:00"));
        a8.setEndTime(LocalTime.parse("19:23:00"));
        a8.setDesc("This is for an acting class");
        a8.setOnCampus(false);
        a8.setMileage(15.0);
        a8.setOrgName("Acting School");
        a8.setInstructions("there are none");
        a8.setExpenses("none");
        a8.setOutsideOrg("no outside organizations");
        a8.setStatus(AppearanceStatus.PENDING);
        a8.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a8);

        Appearance a9 = new Appearance();
        a9.setReqFirstName("Gene");
        a9.setReqLastName("Cousineau");
        a9.setAddress("123 Cousineau Ave");
        a9.setReqPhoneNumber("8174642818");
        a9.setReqEmail("genem@hbo.com");
        a9.setTitle("Filming Wrap Party");
        a9.setEventDate(LocalDate.parse("2023-10-20"));
        a9.setStartTime(LocalTime.parse("17:23:00"));
        a9.setEndTime(LocalTime.parse("19:23:00"));
        a9.setDesc("This is for an filming wrap party");
        a9.setOnCampus(false);
        a9.setMileage(45.0);
        a9.setOrgName("HBO");
        a9.setInstructions("there are none");
        a9.setExpenses("none");
        a9.setOutsideOrg("no outside organizations");
        a9.setStatus(AppearanceStatus.APPROVED);
        a9.setAppearanceType(AppearanceType.PRIVATE);
        this.appearanceRepository.save(a9);

        Appearance a10 = new Appearance();
        a10.setReqFirstName("Monrow");
        a10.setReqLastName("Fuches");
        a10.setAddress("123 San Quentin Dr");
        a10.setReqPhoneNumber("8174442817");
        a10.setReqEmail("m.fuches@hbo.com");
        a10.setTitle("Prison Party");
        a10.setEventDate(LocalDate.parse("2023-11-10"));
        a10.setStartTime(LocalTime.parse("17:23:00"));
        a10.setEndTime(LocalTime.parse("19:23:00"));
        a10.setDesc("This is for a prison party");
        a10.setOnCampus(false);
        a10.setMileage(45.0);
        a10.setOrgName("Federal Prison");
        a10.setInstructions("there are none");
        a10.setExpenses("none");
        a10.setOutsideOrg("no outside organizations");
        a10.setStatus(AppearanceStatus.ASSIGNED);
        a10.setAppearanceType(AppearanceType.PRIVATE);
        a10.setAssignedSuperFrog(u1);
        a10.setAssignedId(u1.getId());
        a10.setAssignedName(u1.getFirstName() + " " + u1.getLastName());
        this.appearanceRepository.save(a10);
        this.userService.assign(a10.getRequestId(), u1.getId());

        Appearance a11 = new Appearance();
        a11.setReqFirstName("Tony");
        a11.setReqLastName("Soprano");
        a11.setAddress("123 Soprano Dr");
        a11.setReqPhoneNumber("8174642317");
        a11.setReqEmail("tonysoprano@badabing.com");
        a11.setTitle("Waste Management Event");
        a11.setEventDate(LocalDate.parse("2023-12-08"));
        a11.setStartTime(LocalTime.parse("17:23:00"));
        a11.setEndTime(LocalTime.parse("19:23:00"));
        a11.setDesc("This is for a bachelor party");
        a11.setOnCampus(false);
        a11.setMileage(45.0);
        a11.setOrgName("Bada Bing");
        a11.setInstructions("there are none");
        a11.setExpenses("none");
        a11.setOutsideOrg("no outside organizations");
        a11.setStatus(AppearanceStatus.COMPLETED);
        a11.setAppearanceType(AppearanceType.PRIVATE);
        a11.setAssignedSuperFrog(u1);
        a11.setAssignedId(u1.getId());
        a11.setAssignedName(u1.getFirstName() + " " + u1.getLastName());
        this.appearanceRepository.save(a11);
        this.userService.assign(a11.getRequestId(), u3.getId());
        this.userService.completeAppearance(a11.getRequestId());
        this.appearanceService.complete(a11.getRequestId());

        Appearance a12 = new Appearance();
        a12.setReqFirstName("Gus");
        a12.setReqLastName("Fring");
        a12.setAddress("123 Pollo Hermanos Dr");
        a12.setReqPhoneNumber("8173342317");
        a12.setReqEmail("gusfring@polloshermanos.com");
        a12.setTitle("Employee Party");
        a12.setEventDate(LocalDate.parse("2023-12-08"));
        a12.setStartTime(LocalTime.parse("17:23:00"));
        a12.setEndTime(LocalTime.parse("19:23:00"));
        a12.setDesc("This is for an employee party");
        a12.setOnCampus(false);
        a12.setMileage(45.0);
        a12.setOrgName("Los Pollos Hermanos");
        a12.setInstructions("there are none");
        a12.setExpenses("none");
        a12.setOutsideOrg("no outside organizations");
        a12.setStatus(AppearanceStatus.COMPLETED);
        a12.setAppearanceType(AppearanceType.PRIVATE);
        a12.setAssignedSuperFrog(u1);
        a12.setAssignedId(u1.getId());
        a12.setAssignedName(u1.getFirstName() + " " + u1.getLastName());
        this.appearanceRepository.save(a12);
        this.userService.assign(a12.getRequestId(), u1.getId());
        this.userService.completeAppearance(a12.getRequestId());
        this.appearanceService.complete(a12.getRequestId());
    }
}
