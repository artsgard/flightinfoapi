/*
package com.artsgard.flightinfoapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    
    public static final Long NON_EXISTING_ID = 7000L;
    public static final String NON_EXISTING_USERNAME = "SDFSDFSFSDFSDF";
    
    private final MapperService mapperService = new MapperServiceImpl();
    
    @BeforeEach
    public void setup() {
        FlightInfoEntity flightInfo1 = new FlightInfoEntity(null, new Date(), new Date(),
                FlightInfoEntity.RoomType.DOUBLE, true, new Date(), 3, new BigDecimal("100.00"),
                MockData.generateClient(),
                MockData.generateHotel());
         FlightInfoEntity flightInfo2 = new FlightInfoEntity(null, new Date(), new Date(),
                FlightInfoEntity.RoomType.DOUBLE, false, new Date(), 3, new BigDecimal("200.00"),
                MockData.generateClient(),
                MockData.generateHotel());
         List<FlightInfoEntity> list = new ArrayList();
         list.add(flightInfo1);
         list.add(flightInfo2);
         flightInfoRepo.saveAll(list);
    }
 
    @Test
    public void testGetAllFlightInfos() {
        List<FlightInfoEntity> flightInfos = flightInfoRepo.findAll();
        assertThat(flightInfos).isNotEmpty();
        assertThat(flightInfos).hasSize(2);
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
        FlightInfoEntity flightInfo = new FlightInfoEntity(null, new Date(), new Date(),
                FlightInfoEntity.RoomType.SINGLE, true, new Date(), 7, new BigDecimal("100.00"),
                MockData.generateClient(),
                MockData.generateHotel());
        flightInfoRepo.save(flightInfo);
        assertThat(flightInfo.getId()).isNotNull();
        assertThat(flightInfo.getRoomType()).isEqualTo(RoomType.SINGLE);
    }

    @Test
    public void testUpdateFlightInfo() {
         FlightInfoEntity flightInfo = new FlightInfoEntity(null, new Date(), new Date(),
                FlightInfoEntity.RoomType.SINGLE, true, new Date(), 7, new BigDecimal("100.00"),
                MockData.generateClient(),
                MockData.generateHotel());
         flightInfoRepo.save(flightInfo);
        Optional<FlightInfoEntity> optFlightInfo = flightInfoRepo.findById(flightInfo.getId());
        FlightInfoEntity updateFlightInfo = optFlightInfo.get();
        updateFlightInfo.setNights(7);
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
        FlightInfoEntity flightInfo = new FlightInfoEntity(null, new Date(), new Date(),
                FlightInfoEntity.RoomType.SINGLE, true, new Date(), 12, new BigDecimal("100.00"),
                MockData.generateClient(),
                MockData.generateHotel());
        flightInfoRepo.save(flightInfo);
        Long id = flightInfo.getId();
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
*/