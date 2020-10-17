package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.repository.AirportDisplayRepository;
import com.artsgard.flightinfoapi.repository.FlightInfoRepository;
import com.artsgard.flightinfoapi.serviceimpl.MapperServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource({"classpath:application-test.properties"})
@DataJpaTest
public class FlightInfoServiceDataJpaTest {

    @Autowired
    private FlightInfoRepository flightInfoRepo;
    
    @Autowired
    private AirportDisplayRepository airportRepo;

    private final MapperService mapperService = new MapperServiceImpl();

    private final MapperService realMapperService = new MapperServiceImpl();

    private FlightInfoEntity flightEntity1;
    private FlightInfoEntity flightEntity2;
    private FlightInfo flight1;
    private FlightInfo flight2;
    private List<FlightInfoEntity> flightEntities;
    private List<FlightInfo> flights;
    public static final Long NON_EXISTING_ID = 7000L;
    public static final Long EXISTING_ID = 1L;
    public static final String EXISTING_USERNAME = "username";
    public static final String NON_EXISTING_USERNAME = "SDFSDFSFSDFSDF";

    @BeforeEach
    public void setup() {
        flight1 = FlightInfoMock.generateFlightInfo();
        flightEntity1 = FlightInfoMock.generateFlightInfoEntity();
        flightEntity2 = realMapperService.mapFlightInfoDTOToFlightInfoEntity(flight1);
        flights = new ArrayList();
        flightEntities = new ArrayList();
        FlightInfoMock.generateFlights().forEach((flg) -> {
            flightEntities.add(realMapperService.mapFlightInfoDTOToFlightInfoEntity(flg));
        });
        FlightInfoMock.generateFlights().forEach((flg) -> {
            flights.add(flg);
        });
        airportRepo.save(flightEntity1.getOrigin());
        airportRepo.save(flightEntity1.getDestination());
        flightInfoRepo.saveAll(flightEntities);
        
    }

    @Test
    public void testGetAllFlightInfos() {
        List<FlightInfoEntity> flightInfos = flightInfoRepo.findAll();
        assertThat(flightInfos).isNotEmpty();
        assertThat(flightInfos).hasSize(3);
    }

    @Test
    public void testGetAllFlightInfos_not_found() {
        flightInfoRepo.deleteAll();
        List<FlightInfoEntity> flightInfos = flightInfoRepo.findAll();
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void testGetFlightInfoById() {
        FlightInfoEntity sc = flightInfoRepo.getOne(1L);
        assertThat(sc).isNotNull();
    }

    @Test
    public void findFlightInfoByIdTest_not_found() {
        FlightInfoEntity sc = flightInfoRepo.getOne(NON_EXISTING_ID);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void testSaveFlightInfo() {
        AirportDisplayEntity origin = new AirportDisplayEntity(null, "new-name-airport-origin", "new-location-origin", 12.12D, 12.12D, "time-zone");
        AirportDisplayEntity destination = new AirportDisplayEntity(null, "new-name-airport-destination", "new-location-destination", 12.12D, 12.12D, "time-zone");
        airportRepo.save(origin);
        airportRepo.save(destination);
        FlightInfoEntity newFlight = new FlightInfoEntity(null, "new-faFlightId", "new-ident", "new-aircraftType", "new-filedEte", 1223L, 1234L,
                123, "new-filedAirSpeedMach", 1234, "new-route", 1234L, 1234L, 1234L, "new-diverted", origin, destination,
                "new-originName", "new-originCity", "new-destinationName", "new-destinationCity");
        flightInfoRepo.save(newFlight);
        assertThat(newFlight.getId()).isNotNull();
        assertThat(newFlight.getFaFlightId()).isEqualTo("new-faFlightId");
    }

    @Test
    public void testUpdateFlightInfo() {
        AirportDisplayEntity origin = new AirportDisplayEntity(null, "new-name-airport-origin", "new-location-origin", 12.12D, 12.12D, "time-zone");
        AirportDisplayEntity destination = new AirportDisplayEntity(null, "new-name-airport-destination", "new-location-destination", 12.12D, 12.12D, "time-zone");
        airportRepo.save(origin);
        airportRepo.save(destination);
        FlightInfoEntity updateFlight = new FlightInfoEntity(null, "new-faFlightId", "new-ident", "new-aircraftType", "new-filedEte", 1223L, 1234L,
                123, "new-filedAirSpeedMach", 1234, "new-route", 1234L, 1234L, 1234L, "new-diverted", origin, destination,
                "new-originName", "new-originCity", "new-destinationName", "new-destinationCity");
        flightInfoRepo.save(updateFlight);
        Optional<FlightInfoEntity> optFlightInfo = flightInfoRepo.findById(updateFlight.getId());
        FlightInfoEntity updateFlightInfo = optFlightInfo.get();
        updateFlightInfo.setFaFlightId("update-FaFlightId");
        FlightInfoEntity updatedFlightInfoFromDB = flightInfoRepo.save(updateFlightInfo);
        assertThat(optFlightInfo.get()).isEqualTo(updatedFlightInfoFromDB);
    }

    @Test
    public void testUpdateFlightInfo_not_found() {
        FlightInfoEntity flightInfo = flightInfoRepo.getOne(NON_EXISTING_ID);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    public void testDeleteFlightInfoById() {
         AirportDisplayEntity origin = new AirportDisplayEntity(null, "new-name-airport-origin", "new-location-origin", 12.12D, 12.12D, "time-zone");
        AirportDisplayEntity destination = new AirportDisplayEntity(null, "new-name-airport-destination", "new-location-destination", 12.12D, 12.12D, "time-zone");
        airportRepo.save(origin);
        airportRepo.save(destination);
        FlightInfoEntity deleteFlight = new FlightInfoEntity(null, "new-faFlightId", "new-ident", "new-aircraftType", "new-filedEte", 1223L, 1234L,
                123, "new-filedAirSpeedMach", 1234, "new-route", 1234L, 1234L, 1234L, "new-diverted", origin, destination,
                "new-originName", "new-originCity", "new-destinationName", "new-destinationCity");
        flightInfoRepo.save(deleteFlight);
        Long id = deleteFlight.getId();
        flightInfoRepo.deleteById(id);
        Optional<FlightInfoEntity> deletedFlightInfo = flightInfoRepo.findById(id);
        assertThat(deletedFlightInfo.isPresent()).isFalse();
    }

    @Test
    public void testDeleteFlightInfoById_not_found() {
        FlightInfoEntity flightInfo = flightInfoRepo.getOne(NON_EXISTING_ID);
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }
}
