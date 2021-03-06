
package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.repository.FlightInfoRepository;
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
public class FightInfoServiceMockitoTest {

    @Mock
    private FlightInfoRepository flightInfoRepo;

    @InjectMocks
    FlightInfoServiceImpl flightInfoService;

    @Mock
    private MapperService mapperService;

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
    }

    @Test
    public void testFindAllFlights() {
        given(flightInfoRepo.findAll()).willReturn(flightEntities);
        given(mapperService.mapFlightInfoEntityToFlightInfoDTO(any(FlightInfoEntity.class))).willReturn(flight1);
        List<FlightInfo> list = flightInfoService.findAllFlightInfos();
        assertThat(list).isNotEmpty().hasSize(3);
    }

    @Test
    public void testFindAllFlights_not_found() {
        List<FlightInfoEntity> emptyList = new ArrayList();
        given(flightInfoRepo.findAll()).willReturn(emptyList);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            flightInfoService.findAllFlightInfos();
        });
    }

    @Test
    public void testFindFlightById() {
        flight1.setId(EXISTING_ID);
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.of(flightEntity1));
        given(mapperService.mapFlightInfoEntityToFlightInfoDTO(any(FlightInfoEntity.class))).willReturn(flight1);
        FlightInfo sc = flightInfoService.findFlightInfoById(any(Long.class));
        assertThat(sc).isNotNull();
        assertThat(sc.getFaFlightId()).isEqualTo(flightEntity1.getFaFlightId());
        assertThat(sc.getDestinationCity()).isEqualTo(flightEntity1.getDestinationCity());
    }

    @Test
    public void testFindFlightById_not_found() {
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            flightInfoService.findFlightInfoById(any(Long.class));
        });
    }
    
    @Test
    public void testFindFlightsInfoByIdent() {
        flight1.setId(EXISTING_ID);
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.of(flightEntity1));
        given(mapperService.mapFlightInfoEntityToFlightInfoDTO(any(FlightInfoEntity.class))).willReturn(flight1);
        FlightInfo sc = flightInfoService.findFlightInfoById(any(Long.class));
        assertThat(sc).isNotNull();
        assertThat(sc.getFaFlightId()).isEqualTo(flightEntity1.getFaFlightId());
        assertThat(sc.getDestinationCity()).isEqualTo(flightEntity1.getDestinationCity());
    }

    @Test
    public void testFindFlightsInfoByIdent_not_found() {
       given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            flightInfoService.findFlightInfoById(any(Long.class));
        });
    }

    @Test
    public void testSaveAllFlights() {
        flightEntity1.setId(EXISTING_ID);
        given(flightInfoRepo.save(any(FlightInfoEntity.class))).willReturn(flightEntity1);
        given(mapperService.mapFlightInfoDTOToFlightInfoEntity(any(FlightInfo.class))).willReturn(flightEntity1);
        given(flightInfoService.saveFlightInfo(flight1, any(AirportDisplay.class),  any(AirportDisplay.class))).willReturn(flight1);
        //BookerDTO sc = flightInfoService.saveBooking(flightEntity1);
        //assertThat(sc).isNotNull();
    }

    @Test
    public void testUpdateFlight() {
        flight1.setId(EXISTING_ID);
        flight2.setId(EXISTING_ID);
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.of(flightEntity2));
        given(flightInfoRepo.save(any(FlightInfoEntity.class))).willReturn(flightEntity2);
        given(mapperService.mapFlightInfoDTOToFlightInfoEntity(any(FlightInfo.class))).willReturn(flightEntity2);
        //given(flightInfoService.updateBooking(flightInfoDTOMock)).willReturn(flightInfoDTOMock);
        FlightInfo flght = flightInfoService.updateFlightInfo(flight1);
        assertThat(flght).isNotNull(); // why is this null!!!!!
    }

    @Test
    public void testUpdateFlight_not_found() {
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            flight1.setId(any(Long.class));
            flightInfoService.updateFlightInfo(flight1);
        });
    }

    @Test
    public void testDeleteFlightById() {
        flightInfoRepo.deleteById(EXISTING_ID);
        verify(flightInfoRepo, times(1)).deleteById(eq(EXISTING_ID));
    }

    @Test
    public void testDeleteFlightById_not_found() {
        given(flightInfoRepo.findById(any(Long.class))).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            flightInfoService.deleteFlightInfoById(any(Long.class));
        });
    }

}
