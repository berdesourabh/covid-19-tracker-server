package com.covid.dashboard.controller;

import com.covid.dashboard.CovidtrackerApplication;
import com.covid.dashboard.dto.CoronaReport;
import com.covid.dashboard.dto.LoginResponseDto;
import com.covid.dashboard.dto.UserRegisterResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CovidtrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    String jwt="";

    @BeforeEach
    void initialSetup(){

        String body = "{\n" +
                "\n" +
                "\"email\":\"abc123@gmail.com\",\n" +
                "\"firstName\":\"kunal\",\n" +
                "\"lastName\":\"Shah\",\n" +
                "\"country\":\"India\",\n" +
                "\"state\":\"Maharashtra\",\n" +
                "\"city\":\"Pune\",\n" +
                "\"password\":\"abc\"\n" +
                "\t\n" +
                "}";

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, newHeaders);

        ResponseEntity<UserRegisterResponse> response = restTemplate.exchange(
                createURLWithPort("/user/register"),
                HttpMethod.POST, entity, UserRegisterResponse.class);
        System.out.println(response.getBody());

        String loginBody = "{\n" +
                "\t\"email\":\"abc123@gmail.com\",\n" +
                "\t\"password\":\"abc\"\n" +
                "\t\n" +
                "}";
        HttpEntity<String> loginEntity = new HttpEntity<String>(loginBody, newHeaders);

        ResponseEntity<LoginResponseDto> loginResponse = restTemplate.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, loginEntity, LoginResponseDto.class);

        LoginResponseDto loginResponseDto = loginResponse.getBody();
        jwt = loginResponseDto.getJwtToken();
        System.out.println(jwt);

        String patientJson = "{\n" +
                "\n" +
                "\n" +
                "    \"user\":{\n" +
                "      \"email\":\"abc123@gmail.com\"\n" +
                "\n" +
                "    },\n" +
                "    \"coronaPositive\":\"Y\",\n" +
                "    \"symptoms\":\"fever\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "  }";

        HttpHeaders patientHeaders = new HttpHeaders();
        patientHeaders.setContentType(MediaType.APPLICATION_JSON);
        patientHeaders.add("Authorization","Bearer "+jwt);
        HttpEntity<String> patientRequestEntity = new HttpEntity<String>(patientJson, patientHeaders);

        restTemplate.exchange(
                createURLWithPort("/patient"),
                HttpMethod.POST, patientRequestEntity, Void.class);


    }

    @Test
    void getCoronaPatientsData() {


        headers.add("Authorization","Bearer "+jwt);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<CoronaReport> response = restTemplate.exchange(
                createURLWithPort("/dashboard/patients?country=India"),
                HttpMethod.GET, entity, CoronaReport.class);
        CoronaReport coronaReport = response.getBody();
        long coronaCases = coronaReport.getCountryReports().get(0).getStateReports().get(0).getCoronaData().getCoronaCases();
        Assert.assertEquals(1,coronaCases);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}