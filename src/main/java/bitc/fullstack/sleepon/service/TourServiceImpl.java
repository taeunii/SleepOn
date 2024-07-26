package bitc.fullstack.sleepon.service;

import bitc.fullstack.sleepon.dto.FullDataItemDTO;
import bitc.fullstack.sleepon.dto.FullDataResponseDTO;
import bitc.fullstack.sleepon.dto.detail.DataItemDTO;
import bitc.fullstack.sleepon.dto.detail.DataResponseDTO;
import bitc.fullstack.sleepon.dto.infor.DataComItemDTO;
import bitc.fullstack.sleepon.dto.infor.DataComResponseDTO;
import bitc.fullstack.sleepon.dto.event.FullEventDataItemDTO;
import bitc.fullstack.sleepon.dto.event.FullEventDataResponseDTO;
import bitc.fullstack.sleepon.mapper.LocationMapper;
import bitc.fullstack.sleepon.model.UserReservation;
import bitc.fullstack.sleepon.repository.UserReservationRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TourServiceImpl implements TourService{
    @Autowired
    LocationMapper locationMapper;

    @Autowired
    UserReservationRepository reservationRepository;

    @Override
    public List<FullDataItemDTO> getItemListUrl(String serviceUrl) throws Exception {
        List<FullDataItemDTO> itemList = new ArrayList<>();
        URL url = null;
        HttpURLConnection urlConn = null;

        try{
            url = new URL(serviceUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            JAXBContext jc = JAXBContext.newInstance(FullDataResponseDTO.class);
            Unmarshaller um = jc.createUnmarshaller();

            FullDataResponseDTO fullData = (FullDataResponseDTO)
 um.unmarshal(url);
            itemList = fullData.getBody().getItems().getItemList();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve data from the service URL: " + serviceUrl, e);
        }
        finally {
            if (urlConn != null) { urlConn.disconnect(); }
        }
        return itemList;
    }
    // 지역 행사
    @Override
    public List<FullEventDataItemDTO> getEventItemListUrl(String serviceUrl) throws Exception {
        System.out.println("\n지역행사 : " + serviceUrl);
        List<FullEventDataItemDTO> itemList = new ArrayList<>();
        URL url = null;
        HttpURLConnection urlConn = null;

        try {
            url = new URL(serviceUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            JAXBContext jc = JAXBContext.newInstance(FullEventDataResponseDTO.class);
            Unmarshaller um = jc.createUnmarshaller();

            FullEventDataResponseDTO fullData = (FullEventDataResponseDTO) um.unmarshal(url);
            itemList = fullData.getBody().getItems().getItemList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve data from the service URL: " + serviceUrl, e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return itemList;
    }

    @Override
    public Map<String, String> getSigunguMap(String areaCode) {
        return locationMapper.getSigunguMapList(areaCode);
    }

    @Override
    public String getSigunguName(String areaCode, String key) {
        return locationMapper.getSigunguMapList(areaCode).get(key);
    }

    @Override
    public String getAreaName(String areaCode) throws Exception {
        return locationMapper.getAreaMap(areaCode);
    }

    // 숙소 공통 정보
    @Override
    public List<DataComItemDTO> getInforItemList(String serviceUrl) throws Exception {
        List<DataComItemDTO> itemList = new ArrayList<>();
        URL url = null;
        HttpURLConnection urlConn = null;

        try{
            url = new URL(serviceUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            JAXBContext jc = JAXBContext.newInstance(DataComResponseDTO.class);
            Unmarshaller um = jc.createUnmarshaller();

            DataComResponseDTO fullData = (DataComResponseDTO)
                    um.unmarshal(url);
            itemList = fullData.getBody().getItems().getItemList();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve data from the service URL: " + serviceUrl, e);
        }
        finally {
            if (urlConn != null) { urlConn.disconnect(); }
        }
        return itemList;
    }

    // 숙소 세부 정보 (객실 가격)
    @Override
    public List<DataItemDTO> getDetailItemList(String serviceUrl) throws Exception {
        List<DataItemDTO> itemList = new ArrayList<>();
        URL url = null;
        HttpURLConnection urlConn = null;

        try {
            url = new URL(serviceUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            JAXBContext jc = JAXBContext.newInstance(DataResponseDTO.class);
            Unmarshaller um = jc.createUnmarshaller();

            DataResponseDTO fullData = (DataResponseDTO) um.unmarshal(url);
            itemList = fullData.getBody().getItems().getItemList();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve data from the service URL: " + serviceUrl, e);
        }
        finally {
            if (urlConn != null) { urlConn.disconnect(); }
        }
        return itemList;
    }

    @Override
    public void saveReservation(UserReservation reservation) {
        reservationRepository.save(reservation);
    }
}
