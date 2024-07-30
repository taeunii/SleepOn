package bitc.fullstack.sleepon.service;

import bitc.fullstack.sleepon.dto.FullDataItemDTO;
import bitc.fullstack.sleepon.dto.detail.DataItemDTO;
import bitc.fullstack.sleepon.dto.infor.DataComItemDTO;
import bitc.fullstack.sleepon.dto.event.FullEventDataItemDTO;
import bitc.fullstack.sleepon.model.UserCancel;
import bitc.fullstack.sleepon.model.UserReservation;

import java.util.List;
import java.util.Map;

public interface TourService {
    List<FullDataItemDTO> getItemListUrl(String serviceUrl) throws Exception;

    List<FullEventDataItemDTO> getEventItemListUrl(String serviceUrl) throws Exception;

    Map<String, String> getSigunguMap(String areaCode) throws Exception;
    String getSigunguName(String areaCode, String key) throws Exception;
    String getAreaName(String areaCode) throws Exception;

    List<DataComItemDTO> getInforItemList(String serviceUrl) throws Exception;

    List<DataItemDTO> getDetailItemList(String serviceUrl) throws Exception;

    // 예약
    void saveReservation(UserReservation reservation);
    // 예약 정보 가져오기
    List<UserReservation> getUserReservation(String userId) throws Exception;
    // 고객 전용 예약 정보
    List<UserReservation> getUserReservationDesc(String userId) throws Exception;

    // 관리자 전용 상담 게시글 목록
    List<UserCancel> getAdminCancelList() throws Exception;
    // 관리자 전용 답변 업로드
    void saveReply(int id, String reply) throws Exception;

    // 문의 내역 개수 조회
    int getCountCancelUser(String id) throws Exception;

    // 고객 전용 내가 작성한 문의 내역 목록
    List<UserCancel> getUserCancelList(String id) throws Exception;
    // 고객 전용 문의 작성
    void saveUserCancel(UserCancel userCancel) throws Exception;
    // 고객 문의 삭제
    void deleteUserCancel(int id) throws Exception;
}
