package com.artsgard.flightinfoapi.controller;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.service.MapperService;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoExternalServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.MapperServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author artsgard
 */

@ExtendWith(MockitoExtension.class)
public class RestTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightInfoServiceImpl flightService;
    
    @Mock
    private FlightInfoExternalServiceImpl flightExtService;

    @InjectMocks
    RestTemplateController flightController;
    
    @Mock
    HttpURLConnection connection;

    private JacksonTester<FlightInfo> jsonFlight;
    private JacksonTester<FlightInfoExResult> jsonFlightResult;
    private JacksonTester<List<FlightInfo>> jsonFlights;
    private FlightInfo flight;
    private List<FlightInfo> flights;
    private FlightInfoExResult flightExResult;

    private final MapperService mapperService = new MapperServiceImpl();

    @BeforeEach
    public void setup() {
        
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        flight = FlightInfoMock.generateFlightInfo();
        flights = new ArrayList();
        FlightInfoMock.generateFlights().forEach((flg) -> {
            flights.add(flg);
        });
        flightExResult = FlightInfoMock.generateFlightInfoExResult();
    }
    
    @Test
    public void testMockInjection() {
        
    }
    
    @Test
    public void testGetTailFlights() {
        
    }
}