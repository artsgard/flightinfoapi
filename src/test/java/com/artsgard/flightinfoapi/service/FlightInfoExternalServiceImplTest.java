package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.serviceimpl.FlightInfoExternalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FlightInfoExternalServiceImplTest {

    @Spy
    FlightInfoExternalServiceImpl flightInfoExternalServiceImpl;

    @Mock
    HttpURLConnection connection;

    @Test
    public void testGetConnection() throws IOException { //String url, String requestMethod, String contentType HttpURLConnection
        int expectedStatus = 200;
        given(flightInfoExternalServiceImpl.getConnection(any(String.class), any(String.class), any(String.class))).willReturn(connection);
        given(connection.getResponseCode()).willReturn(expectedStatus);
        String urlString = "https://flightxml.flightaware.com/json/FlightXML2/FlightInfoEx?ident=EC-MYT&howMany=15&offset=0";
        HttpURLConnection con = flightInfoExternalServiceImpl.getConnection(urlString, "GET", "application/json; charset=UTF-8"); 
        int status = con.getResponseCode();

        Assertions.assertEquals(expectedStatus, status);
    }

    HttpURLConnection connect(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    //@Test
    public void testGetFlightInfo() { //String tailnumber, int offSet FlightInfoExResult 

    }
}
