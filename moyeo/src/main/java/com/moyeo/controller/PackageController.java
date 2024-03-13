package com.moyeo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.moyeo.dto.Pack;
import com.moyeo.dto.PackHeart;
import com.moyeo.dto.Review;
import com.moyeo.security.CustomUserDetails;
import com.moyeo.service.PackageHeartService;
import com.moyeo.service.PackageService;
import com.moyeo.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/package")
@Slf4j
public class PackageController {

   private final WebApplicationContext context;
   private final PackageService packageService; // 패키지 등록 관련 service
   private final PackageHeartService packageHeartService;
   private final ReviewService reviewService;
   
   //패키지 리스트 페이지 이동
   @RequestMapping(value = "/")
   public String packageMainGET(@RequestParam Map<String, Object> map, Model model) {
      model.addAttribute("result", packageService.getPackageListUser(map));
      model.addAttribute("search", map);

      return "package/mo_package";
   }

   // 패키지 상세 페이지 이동 - 패키지 상세 정보 select
   @RequestMapping(value = "/detail/{packIdx}", method = RequestMethod.GET)
   public String packageDetailGET(@PathVariable("packIdx") Integer packIdx,
           Model model,
           Authentication authentication) {

       CustomUserDetails userinfo = null;
       if (authentication != null) {
           userinfo = (CustomUserDetails) authentication.getPrincipal();
           log.info("userinfoVal = " + userinfo);
       }

       if (packIdx == null) {
           return "redirect:/error"; 
       }

       Pack pack = packageService.getPackageInfo(packIdx);
       if (pack == null) {
           return "redirect:/error"; 
       }

       // 로그인한 유저인 경우 isLoggedin 변수를 true로 설정
       boolean isLoggedin = userinfo != null;

       // 로그인 유저가 찜 했는지 확인
       PackHeart packHeart = packageHeartService.getPackIdxWithId(packIdx, userinfo != null ? userinfo.getId() : null);
       boolean isHeartAdded = packHeart != null;

       // 최신 리뷰 3개
       List<Review> latestReviews = packageService.getLatestReviews(pack.getPackTitle());

       model.addAttribute("pack", pack);
       model.addAttribute("packHeartList", packHeart);
       model.addAttribute("latestReviews", latestReviews);
       model.addAttribute("isHeartAdded", isHeartAdded);
       model.addAttribute("isLoggedin", isLoggedin);
       model.addAttribute("userinfo", userinfo);
       model.addAttribute("userinfoId", userinfo);
       
       return "package/mo_package_animal";
   }

   
   /* 관리자 */
   // 패키지 등록 페이지 이동
   @RequestMapping(value = "/form", method = RequestMethod.GET)
   public String addPackageGET() {
      return "package/mo_package_form";
   }

   // 패키지 등록
   @RequestMapping(value = "/addPackage", method = RequestMethod.POST)
   public String addPackagePOST(@ModelAttribute Pack pack,
         @RequestParam("packPreviewImgFile") MultipartFile packPreviewImgFile,
         @RequestParam("packSlideImg1File") MultipartFile packSlideImg1File,
         @RequestParam("packSlideImg2File") MultipartFile packSlideImg2File,
         @RequestParam("packSlideImg3File") MultipartFile packSlideImg3File,
         @RequestParam("packContentImg1File") MultipartFile packContentImg1File,
         @RequestParam("packContentImg2File") MultipartFile packContentImg2File,
         @RequestParam("packContentImg3File") MultipartFile packContentImg3File,
         @RequestParam("packCalendarImgFile") MultipartFile packCalendarImgFile,
         Model model, HttpSession session) throws IllegalStateException, IOException {
      
      if(packPreviewImgFile.isEmpty() || packSlideImg1File.isEmpty() || packSlideImg2File.isEmpty() 
            || packSlideImg3File.isEmpty() || packContentImg1File.isEmpty() || packContentImg2File.isEmpty()
            || packContentImg2File.isEmpty() || packContentImg3File.isEmpty() || packCalendarImgFile.isEmpty()) {
         //이미지 파일이 업로드되지 않은 경우 처리
         model.addAttribute("message","파일이 업로드되지 않았습니다.");
         return "redirect:/package/";
      }
      
      //전달파일을 저장하기 위한 서버 디렉토리의 시스템 경로 반환
      String uploadDirectory = context.getServletContext().getRealPath("/resources/assets/img/upload");
      
      //서버 디렉토리에 업로드 처리되며 저장된 파일의 이름을 반환하여 Command 객체의 필드값 변경
      String uploadPreview = UUID.randomUUID().toString()+"-"+packPreviewImgFile.getOriginalFilename();
      pack.setPackPreviewImg(uploadPreview);
      
      String uploadSlide1 = UUID.randomUUID().toString()+"-"+packSlideImg1File.getOriginalFilename();
      pack.setPackSlideImg1(uploadSlide1);
      
      String uploadSlide2 = UUID.randomUUID().toString()+"-"+packSlideImg2File.getOriginalFilename();
      pack.setPackSlideImg2(uploadSlide2);
      
      String uploadSlide3 = UUID.randomUUID().toString()+"-"+packSlideImg3File.getOriginalFilename();
      pack.setPackSlideImg3(uploadSlide3);
      
      String uploadContent1 = UUID.randomUUID().toString()+"-"+packContentImg1File.getOriginalFilename();
      pack.setPackContentImg1(uploadContent1);
      
      String uploadContent2 = UUID.randomUUID().toString()+"-"+packContentImg2File.getOriginalFilename();
      pack.setPackContentImg2(uploadContent2);
      
      String uploadContent3 = UUID.randomUUID().toString()+"-"+packContentImg3File.getOriginalFilename();
      pack.setPackContentImg3(uploadContent3);
      
      String uploadCalendar = UUID.randomUUID().toString()+"-"+packCalendarImgFile.getOriginalFilename();
      pack.setPackCalendarImg(uploadCalendar);
      
      //파일 업로드 처리 - 복붙해서 넣어주는게 아니라 서버에 넣어줌
      packPreviewImgFile.transferTo(new File(uploadDirectory,uploadPreview));
      packSlideImg1File.transferTo(new File(uploadDirectory,uploadSlide1));
      packSlideImg2File.transferTo(new File(uploadDirectory,uploadSlide2));
      packSlideImg3File.transferTo(new File(uploadDirectory,uploadSlide3));
      packContentImg1File.transferTo(new File(uploadDirectory,uploadContent1));
      packContentImg2File.transferTo(new File(uploadDirectory,uploadContent2));
      packContentImg3File.transferTo(new File(uploadDirectory,uploadContent3));
      packCalendarImgFile.transferTo(new File(uploadDirectory,uploadCalendar));
      
      //테이블에 행 삽입
      packageService.addPackage(pack);

      return "redirect:/package/";  
   }

   /*패키지 찜 기능 구현*/
  
   //찜 목록에 추가
   @PostMapping("/addToPackageHeartList")
   public ResponseEntity<String> addPackageHeart(@RequestParam int packIdx, 
         @RequestParam String userinfoId) {

      try {
         PackHeart packHeart = new PackHeart();
         packHeart.setPackIdx(packIdx);
         packHeart.setUserinfoId(userinfoId);
         packageHeartService.addPackageHeart(packHeart);
         return ResponseEntity.ok("찜 목록에 추가 되었습니다.");
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 목록 추가에 실패했습니다.");
      }
   }
   
   //찜 목록에서 제거
   @PostMapping("/removeFromPackageHeartList")
   public ResponseEntity<String> removePackageHeart(@RequestParam String userinfoId,
         @RequestParam(name = "packHeartIdx") String packHeartIdxStr) {
      try {
         int packHeartIdx = Integer.parseInt(packHeartIdxStr); // 문자열을 정수로 변환
         PackHeart packHeart = new PackHeart();
         packHeart.setUserinfoId(userinfoId);
         packHeart.setPackHeartIdx(packHeartIdx);
         packageHeartService.removePackageHeart(packHeart);
         return ResponseEntity.ok("찜 목록에서 삭제되었습니다.");
      } catch (NumberFormatException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다. packHeartIdx를 정수로 변환할 수 없습니다.");
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜 삭제에 실패했습니다.");
      }
   }
}