package com.covid.dashboard.util;

import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRepository;
import com.covid.dashboard.service.PatientService;
import com.covid.dashboard.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@RestController
public class DummyUserUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/batchInsert")
    public void addUsersAndPatients() throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        connection.setAutoCommit(false);
        String userSql="insert into user (city, country, enabled, first_name, last_name, password, role, state, verification_code, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String patientSql = "insert into patient(corona_positive,dead,symptoms,email,patient_id,recovered,report_status) values(?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(userSql);
        PreparedStatement preparedStatement2 = connection.prepareCall(patientSql);

        File usersFile = null;
        File patientsFile = null;

        Map<String ,Map<String,String[]>> countryMap = new HashMap();
        Map<String,String[]> indiaStateCityMap = new HashMap();
        indiaStateCityMap.put("Maharashtra",new String[]{"Pune","Mumbai","Nagpur","Kolhapur"});
        indiaStateCityMap.put("Madhya Pradesh",new String[]{"Bhopal","Indore"});
        indiaStateCityMap.put("Delhi",new String[]{"Delhi"});
        countryMap.put("India",indiaStateCityMap);
        Map<String,String[]> englandStateCityMap = new HashMap();
        englandStateCityMap.put("London",new String[]{"Camden","Haringey"});
        englandStateCityMap.put("Cambridgeshire",new String[]{"Cambridge","Royston"});
        countryMap.put("United Kingdom",englandStateCityMap);

        Map<String,String[]> unitedStatesStatesMap = new HashMap();
        unitedStatesStatesMap.put("California",new String[]{"Los Angeles","San Diego","San Francisco"});
        unitedStatesStatesMap.put("Texas",new String[]{"Houston","Austin"});
        countryMap.put("United States",unitedStatesStatesMap);

        String countries[] = {"India","United Kingdom","United States"};
        Random random = new Random();
        List<User> userEntities = new ArrayList<>();
        String encodedPassword = passwordEncoder.encode("abc");
        String roles[] = {"ROLE_PHYSICIAN","ROLE_PATIENT"};
        for(int i = 1;i<=100000;i++){


            int rd = random.nextInt(countries.length);
            Map<String, String[]> statesMaps = countryMap.get(countries[rd]);
            Object[] keys = statesMaps.keySet().toArray();
            int keyRand = random.nextInt(keys.length);
            String[] strings = statesMaps.get(keys[keyRand]);
            int valueRand = random.nextInt(strings.length);
            int rolesRand = random.nextInt(roles.length);
            String role = roles[rolesRand];
            RandomString randomString = new RandomString();
            String randomStr = randomString.make(60);
            preparedStatement.setString(1,strings[valueRand]);
            preparedStatement.setString(2,countries[rd]);
            preparedStatement.setBoolean(3,true);
            preparedStatement.setString(4,"kunal"+i);
            preparedStatement.setString(5,"Shah");
            preparedStatement.setString(6,encodedPassword);
            preparedStatement.setString(7,role);
            preparedStatement.setString(8,(String)keys[keyRand]);
            preparedStatement.setString(9,randomStr);
            preparedStatement.setString(10,"abc"+i+"@gmail.com");
            preparedStatement.addBatch();
           if(i%500==0){
               preparedStatement.executeBatch();
           }

        if(role.equals("ROLE_PATIENT")) {
            String post = "N";
            String recovered = "";
            String reports = "";
            boolean dead = false;
            if (random.nextBoolean() == true) {
                post = "Y";
                dead = random.nextBoolean();
            }else{
                recovered = "Y";
                reports = "POSITIVE";
            }
            preparedStatement2.setString(1, post);
            preparedStatement2.setBoolean(2, dead);
            preparedStatement2.setString(3, "fever");
            preparedStatement2.setString(4, "abc" + i + "@gmail.com");
            preparedStatement2.setInt(5, i);
            preparedStatement2.setString(6,recovered);
            preparedStatement2.setString(7,reports);

            preparedStatement2.addBatch();
            if (i % 500 == 0) {
                preparedStatement2.executeBatch();
            }
        }
            /*Patient patient = new Patient();
            User user1  = new User();
            user1.setEmail("abc"+i+"@gmail.com");
            patient.setUser(user1);
            String post="N";
            if(random.nextBoolean()==true){
                post="Y";
            }
            patient.setCoronaPositive(post);
            patient.setSymptoms("fever");
            patient.setDead(random.nextBoolean());
            physicianService.addCoronaPatient(patient);*/



        }

        preparedStatement.executeBatch();
        preparedStatement2.executeBatch();
        connection.commit();
        preparedStatement.clearBatch();
        preparedStatement2.clearBatch();


        /*try {
            usersFile = ResourceUtils.getFile("classpath:users.json");
            String userJson = new String(Files.readAllBytes(usersFile.toPath()));
            List<UserRegistrationRequest> userRegistrationRequests = new ObjectMapper().readValue(userJson, new TypeReference<List<UserRegistrationRequest>>() {
            });
            userRegistrationRequests.forEach(user -> userService.registerUser(user,httpServletRequest));

            patientsFile = ResourceUtils.getFile("classpath:patients.json");
            String patientJson = new String(Files.readAllBytes(patientsFile.toPath()));
            List<Patient> patients = new ObjectMapper().readValue(patientJson, new TypeReference<List<Patient>>() {
            });
            patients.forEach(patient -> physicianService.addCoronaPatient(patient));



        }catch (Exception e){
                e.printStackTrace();
        }*/

    }

}
