package bitc.fullstack.sleepon.service;

import bitc.fullstack.sleepon.dto.FullDataItemDTO;
import bitc.fullstack.sleepon.dto.infor.DataComItemDTO;
import bitc.fullstack.sleepon.dto.event.FullEventDataItemDTO;

import java.util.List;
import java.util.Map;

public interface TourService {
    List<FullDataItemDTO> getItemListUrl(String serviceUrl) throws Exception;

    List<FullEventDataItemDTO> getEventItemListUrl(String serviceUrl) throws Exception;

    Map<String, String> getSigunguMap(String areaCode) throws Exception;
    String getSigunguName(String areaCode, String key) throws Exception;
    String getAreaName(String areaCode) throws Exception;

    List<DataComItemDTO> getInforItemList(String serviceUrl) throws Exception;
}