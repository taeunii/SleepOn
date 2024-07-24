package bitc.fullstack.sleepon.controller;

import bitc.fullstack.sleepon.dto.FullDataItemDTO;
import bitc.fullstack.sleepon.dto.infor.DataComItemDTO;
import bitc.fullstack.sleepon.dto.event.FullEventDataItemDTO;
import bitc.fullstack.sleepon.service.TourService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/SleepOn")
public class TourController {

    @Autowired
    TourService tourService;

    @Value("${SleepOn.service.key}")
    private String APIkey;

    @Value("${SleepOn.service.Url}")
    private String APIUrl;

    // 숙소 기본 정부 ( 시설 정보, 이용 안내)
    @Value("${SleepOn.service.Infor.Url}")
    private String APIInforUrl;

    // 숙소 세부 정부 (객실, 가격)
    @Value("${SleepOn.service.detail.Url}")
    private String APIDetailUrl;

    // 지역 행사
    @Value("${SleepOnEvent.service.Url}")
    private String eventApiurl;

    @RequestMapping(value = {"", "/"})
    public String SleepOnService() throws Exception {
        return "page/SleepOnMain";
    }

    // 숙소 정보 보기 ajax 통신
    @ResponseBody
    @RequestMapping("/selectArea")
    public Object SleepOnService(@RequestParam("areaCode") String areaCode, @RequestParam("sigunguCode") String sigunguCode, @RequestParam("arrange") String arrange, @RequestParam("numOfRows") String numOfRows, @RequestParam("pageNo") String pageNo) throws Exception {
        System.out.println("main 페이지에서 선택 : " + areaCode);
//http://apis.data.go.kr/B551011/KorService1/searchStay1?areaCode=&sigunguCode=&ServiceKey=인증키&listYN=Y&MobileOS=ETC&MobileApp=AppTest&arrange=A&numOfRows=12&pageNo=1
        String opt1 = "?areaCode="; // 지역코드
        String opt2 = "&sigunguCode="; // 시군구코드
        String opt3 = "&ServiceKey=";
        String opt4 = "&listYN=Y&MobileOS=ETC&MobileApp=AppTest";
        String opt5 = "&arrange="; // 정렬순(사진있는거만)
        String opt6 = "&numOfRows=";
        String opt7 = "&pageNo=";

        List<FullDataItemDTO> itemList = tourService.getItemListUrl(APIUrl + opt1 + areaCode + opt2 + sigunguCode + opt3 + APIkey + opt4 + opt5 + arrange + opt6 + numOfRows + opt7 + pageNo);

        return itemList;
    }
    
    // 지역 축제 조회 ajax 통신
    @ResponseBody
    @RequestMapping("/FestivalArea")
    public Object SearchFestival(@RequestParam("areaCode") String areaCode) throws Exception{
        //https://apis.data.go.kr/B551011/KorService1/searchFestival1?areaCode=1&serviceKey=&numOfRows=200&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=R&eventStartDate=20240101

        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println("\n 날짜 : " + dateFormat);

        String opt1 = "?areaCode=";
        String opt2 = "&serviceKey=";
        String opt3 = "&numOfRows=200&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=R&eventStartDate=" + dateFormat;

        List<FullEventDataItemDTO> itemList = tourService.getEventItemListUrl(eventApiurl + opt1 + areaCode + opt2 + APIkey + opt3);

        return itemList;
    }

    // 지역 축제
    @RequestMapping("/SearchFestival")
    public String SearchFestival(@RequestParam("areaCode") String areaCode, HttpSession session) throws Exception{
        System.out.println("searchFestival : " + areaCode);
        String areaName = tourService.getAreaName(areaCode);
        session.setAttribute("areaCode", areaCode);
        session.setAttribute("areaName", areaName);

        return "redirect:/SleepOn/Festival";
    }

    // 지역 축제 View 페이지
    @RequestMapping("/Festival")
    public ModelAndView AreaFestival(HttpSession session) throws Exception{
        ModelAndView mv = new ModelAndView("page/SleepOnFestival");

        String areaCode = (String) session.getAttribute("areaCode");
        mv.addObject("areaCode", areaCode);

        String areaName = (String) session.getAttribute("areaName");
        mv.addObject("areaName", areaName);

        return mv;
    }

    // 지역 선택시 시군구
    @ResponseBody
    @RequestMapping("/SigunguSelect")
    public Map<String, String> selectSigungu(@RequestParam("areaCode") String areaCode) throws Exception {
        System.out.println("\nsearch 창에 지역 선택 : " + areaCode + "\n");

        Map<String, String> sigunguMap = tourService.getSigunguMap(areaCode);

        return sigunguMap;
    }

    // 검색 창에 지역, 시군구, 날짜, 인원 선택하면 세션 저장하여 리다이렉트
    @RequestMapping("/Search")
    public String SleepOnSearch(@RequestParam("areaCode") String areaCode, @RequestParam("sigunguCode") String sigunguCode, @RequestParam("checkIn") String checkIn, @RequestParam("checkOut") String checkOut, @RequestParam("userCnt") String userCnt, HttpSession session) throws Exception {

        // 값 저장
        session.setAttribute("areaCode", areaCode);
        session.setAttribute("sigunguCode", sigunguCode);
        session.setAttribute("checkIn", checkIn);
        session.setAttribute("checkOut", checkOut);
        session.setAttribute("userCnt", userCnt);

        String areaName = tourService.getAreaName(areaCode);
        String sigunguName = tourService.getSigunguName(areaCode, sigunguCode);

        session.setAttribute("areaName", areaName);
        session.setAttribute("sigunguName", sigunguName);

        return "redirect:/SleepOn/SleepOnList";
    }

    @ResponseBody
    @RequestMapping("/SleepOnList")
    public ModelAndView SleepOnList(HttpSession session) throws Exception {
        // 세션에서 값 가져오기
        String areaCode = (String) session.getAttribute("areaCode");
        String sigunguCode = (String) session.getAttribute("sigunguCode");
        String checkIn = (String) session.getAttribute("checkIn");
        String checkOut = (String) session.getAttribute("checkOut");
        String userCnt = (String) session.getAttribute("userCnt");
        String areaName = (String) session.getAttribute("areaName");
        String sigunguName = (String) session.getAttribute("sigunguName");

        ModelAndView mv = new ModelAndView("page/SleepOnList");
        mv.addObject("areaCode", areaCode);
        mv.addObject("sigunguCode", sigunguCode);
        mv.addObject("checkIn", checkIn);
        mv.addObject("checkOut", checkOut);
        mv.addObject("userCnt", userCnt);
        mv.addObject("areaName", areaName);
        mv.addObject("sigunguName", sigunguName);

        return mv;
    }
    
    // 숙소 상세정보, contentId, areaCode, sigunguCode 값 받아오기
    @RequestMapping("/Detail")
    public String SleepOnDetail(@RequestParam("areaCode") String areaCode, @RequestParam("contentId") String contentId, HttpSession session) throws Exception{
        session.setAttribute("areaCode", areaCode);
        session.setAttribute("contentId", contentId);

        return "redirect:/SleepOn/HotelDetail";
    }

    @RequestMapping("/HotelDetail")
    public ModelAndView SleepOnDetail(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("page/SleepOnHotelDetail");

        String areaCode = (String) session.getAttribute("areaCode");
        String contentId = (String) session.getAttribute("contentId");

        mv.addObject("areaCode", areaCode);
        mv.addObject("contentId", contentId);

        return mv;
    }

    @ResponseBody
    @RequestMapping("/HotelDetailInfo")
    public Object SleepOnDetail(@RequestParam("contentId") String contentId) throws Exception{

        // 공통 정보
        //http://apis.data.go.kr/B551011/KorService1/detailCommon1?ServiceKey=인증키&contentTypeId=32&contentId=142785&MobileOS=ETC&MobileApp=AppTest&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y

        String opt1 = "?ServiceKey=";
        String opt2 = "&contentTypeId=32";
        String opt3 = "&contentId=";
        String opt4 = "&MobileOS=ETC&MobileApp=AppTest&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y";

        List<DataComItemDTO> itemList = tourService.getInforItemList(APIInforUrl + opt1 + APIkey + opt2 + opt3 + contentId + opt4);

        return itemList;
    }

    // 객실 요금
    //http://apis.data.go.kr/B551011/KorService1/detailInfo1?ServiceKey=인증키&contentTypeId=32&contentId=142785&MobileOS=ETC&MobileApp=AppTest
}