
package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.repository.AirportDisplayRepository;
import com.artsgard.flightinfoapi.repository.FlightInfoRepository;
import com.artsgard.flightinfoapi.serviceimpl.AirportDisplayServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.MapperServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AirportDisplayServiceMockitoTest {
    @Mock
    private AirportDisplayRepository airportRepo;

    @InjectMocks
    AirportDisplayServiceImpl airportService;

    @Mock
    private MapperService mapperService;

    private final MapperService realMapperService = new MapperServiceImpl();

    private AirportDisplayEntity airportEntityOrg;
    private AirportDisplayEntity airportEntityDest;
    private AirportDisplay airportOrg;
    private AirportDisplay airportDest;
    private FlightInfo flight1;
    private FlightInfo flight2;
    private List<AirportDisplayEntity> flightEntities;
    private List<AirportDisplay> airports;
    private List<AirportDisplayEntity> airportEntities;
    public static final Long NON_EXISTING_ID = 7000L;
    public static final Long EXISTING_ID = 1L;
    public static final String EXISTING_NAME = "name";
    public static final String NON_EXISTING_NAME = "SDFSDFSFSDFSDF";

    @BeforeEach
    public void setup() {
        flight1 = FlightInfoMock.generateFlightInfo();
        airportEntityOrg = FlightInfoMock.generateAirportOrigen();
        airportEntityDest = FlightInfoMock.generateAirportDestination(); 
        
        airportOrg = realMapperService.mapAirportDisplayEntityToAirportDisplayDTO(airportEntityOrg);
        airportDest = realMapperService.mapAirportDisplayEntityToAirportDisplayDTO(airportEntityDest);
        airports = new ArrayList();
        airportEntities = new ArrayList();
        airports.add(airportOrg);
        airports.add(airportDest);
        airportEntities.add(airportEntityOrg);
        airportEntities.add(airportEntityDest);
    }

    @Test
    public void testFindAllAirports() {
        given(airportRepo.findAll()).willReturn(airportEntities);
        given(mapperService.mapAirportDisplayEntityToAirportDisplayDTO(any(AirportDisplayEntity.class))).willReturn(airportOrg);
        List<AirportDisplay> list = airportService.findAllAirportDisplays();
        assertThat(list).isNotEmpty().hasSize(2);
    }

    @Test
    public void testFindAllAirports_not_found() {
        List<AirportDisplayEntity> emptyList = new ArrayList();
        given(airportRepo.findAll()).willReturn(emptyList);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            airportService.findAllAirportDisplays();
        });
    }

    @Test
    public void testFindAirportById() {
        flight1.setId(EXISTING_ID);
        given(airportRepo.findById(any(Long.class))).willReturn(Optional.of(airportEntityOrg));
        given(mapperService.mapAirportDisplayEntityToAirportDisplayDTO(any(AirportDisplayEntity.class))).willReturn(airportOrg);
        AirportDisplay arp = airportService.findAirportDisplayById(any(Long.class));
        assertThat(arp).isNotNull();
        assertThat(arp.getName()).isEqualTo(airportEntityOrg.getName());
    }

    @Test
    public void testFindAirportById_not_found() {
        given(airportRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            airportService.findAirportDisplayById(any(Long.class));
        });
    }
    
    @Test
    public void testFindAirportByName() {
        airportOrg.setName(EXISTING_NAME);
        airportEntityOrg.setName(EXISTING_NAME);
        given(airportRepo.findByName(EXISTING_NAME)).willReturn(Optional.of(airportEntityOrg));
        given(mapperService.mapAirportDisplayEntityToAirportDisplayDTO(any(AirportDisplayEntity.class))).willReturn(airportOrg);
        AirportDisplay arp = airportService.findAirportDisplayByName(EXISTING_NAME);
        assertThat(arp).isNotNull();
        assertThat(arp.getName()).isEqualTo(airportEntityOrg.getName());
    }

    @Test
    public void testFindAirportByName_not_found() {
       given(airportRepo.findByName(NON_EXISTING_NAME)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            airportService.findAirportDisplayByName(NON_EXISTING_NAME);
        });
    }
    
    @Test
    public void testSaveAirport() {
        airportOrg.setId(EXISTING_ID);
        given(airportRepo.save(airportEntityOrg)).willReturn(airportEntityOrg);
        given(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(airportOrg)).willReturn(airportEntityOrg);
        AirportDisplay sc = airportService.saveAirportDisplay(airportOrg);
        assertThat(sc).isNotNull(); // why is this null!!!!!
    }

    @Test
    public void testUpdateAirport() {
        airportOrg.setId(EXISTING_ID);
        airportDest.setId(EXISTING_ID);
        given(airportRepo.findById(any(Long.class))).willReturn(Optional.of(airportEntityOrg));
        given(airportRepo.save(any(AirportDisplayEntity.class))).willReturn(airportEntityOrg);
        given(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(any(AirportDisplay.class))).willReturn(airportEntityOrg);
        //given(airportService.updateBooking(flightInfoDTOMock)).willReturn(flightInfoDTOMock);
        AirportDisplay flght = airportService.updateAirportDisplay(airportOrg);
        assertThat(flght).isNotNull(); // why is this null!!!!!
    }

    @Test
    public void testUpdateAirport_not_found() {
        given(airportRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            airportOrg.setId(any(Long.class));
            airportService.updateAirportDisplay(airportOrg);
        });
    }

    @Test
    public void testDeleteAirportById() {
        airportRepo.deleteById(EXISTING_ID);
        verify(airportRepo, times(1)).deleteById(eq(EXISTING_ID));
    }

    @Test
    public void testDeleteAirportById_not_found() {
        given(airportRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            airportService.deleteAirportDisplayById(any(Long.class));
        });
    }

}
