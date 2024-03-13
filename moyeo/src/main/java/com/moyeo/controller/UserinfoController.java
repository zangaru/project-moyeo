package com.moyeo.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moyeo.dto.Diy;
import com.moyeo.dto.Orders;
import com.moyeo.dto.Pack;
import com.moyeo.dto.Qa;
import com.moyeo.dto.Review;
import com.moyeo.dto.Userinfo;
import com.moyeo.exception.LoginAuthFailException;
import com.moyeo.exception.UserinfoNotFoundException;
import com.moyeo.security.CustomUserDetails;
import com.moyeo.service.DiyService;
import com.moyeo.service.MailSendService;
import com.moyeo.service.OrdersService;
import com.moyeo.service.PackageHeartService;
import com.moyeo.service.QaService;
import com.moyeo.service.ReviewService;
import com.moyeo.service.UserinfoService;

@Controller
@RequestMapping(value = "/user")
public class UserinfoController {
   private static final Logger logger = LoggerFactory.getLogger(UserinfoController.class);

   @Autowired
   private UserinfoService userinfoservice;

   @Autowired
   private MailSendService mailService;

   @Autowired
   private BCryptPasswordEncoder pwEncoder;

   @Autowired
   private PackageHeartService packageHeartService;

   @Autowired
   private QaService qaService;

   @Autowired
   private ReviewService reviewService;

   @Autowired
   private DiyService diyService;
   
   @Autowired
   private OrdersService ordersService;

   // 회원가입 페이지 이동
   @RequestMapping(value = "/join", method = RequestMethod.GET)
   public String joinGET(Model model) {
      model.addAttribute("Userinfo", new Userinfo());
      return "userinfo/join";
   }

   // 회원가입
   @RequestMapping(value = "/join", method = RequestMethod.POST)
   public String joinPOST(@Valid @ModelAttribute("Userinfo") Userinfo userinfo,
         @RequestParam("userinfoRole") String userinfoRole, BindingResult bindingResult) throws Exception {
      if (bindingResult.hasErrors()) {// 유효성 검사 실패
    	 logger.info("errors = {} ", bindingResult);
         logger.info(bindingResult.getAllErrors().get(0).getDefaultMessage());
         return "userinfo/join"; // 유효성 검사 실패 시 다시 회원가입 페이지로
      }

      userinfoservice.registerUser(userinfo, userinfoRole); 
      return "redirect:/user/login"; 
   }

   // 아이디 중복검사
   @RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
   @ResponseBody
   public String memberIdChkPOST(@RequestParam String id) throws Exception {

      logger.info("memberIdChk() 진입");
      int result = userinfoservice.idCheck(id);

      logger.info("결과값 = " + result);

      if (result != 0) {
         return "fail"; // 중복 아이디가 존재
      } else {
         return "success"; // 중복 아이디 x
      }
   } 
   

   // 이메일 중복검사
   @RequestMapping(value = "/memberEmailChk", method = RequestMethod.POST)
   @ResponseBody
   public String memberEmailChkPOST(@RequestParam String email) throws Exception {

      logger.info("memberEmailChk() 진입");
      int result = userinfoservice.emailCheck(email);

      logger.info("결과값 = " + result);

      if (result != 0) {
         return "fail"; // 중복 이메일이 존재
      } else {
         return "success"; // 중복 이메일 x
      }
   }

   // 이메일 인증
   @GetMapping("/mailCheck")
   @ResponseBody
   public String mailCheck(@RequestParam String email) throws Exception {
	  logger.info("이메일 인증 요청이 들어옴!");
      logger.info("이메일 인증 이메일 : " + email);
      return mailService.joinEmail(email);
   }

   /* 로그인 */

   // 로그인 페이지 이동
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String loginGET() {

      return "userinfo/login";
   }
   
   //로그인
   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public String loginPOST(@ModelAttribute Userinfo userinfo,
		   				   RedirectAttributes rttr, HttpSession session) throws Exception {

      session.setAttribute("userinfoId", userinfo.getId());

      Userinfo lto = userinfoservice.userLogin(userinfo);

      if (lto != null) {
         if (lto.getStatus() == 3) {
            // 탈퇴 회원은 로그인 차단
            rttr.addFlashAttribute("result", 0);
            return "redirect:/user/login";
         } else if (lto.getStatus() == 2) {
            // 휴면 계정은 활성화 요구 페이지로 바로 이동
            return "redirect:/user/dormantAccount";
         }

         String rawPw = userinfo.getPw();
         String encodePw = lto.getPw();

         if (pwEncoder.matches(rawPw, encodePw)) {
            lto.setPw("");
            session.setAttribute("userinfo", lto);
            userinfoservice.updateUserLogindate(lto.getId());
            return "redirect:/";
         } else {
            rttr.addFlashAttribute("result", 0);
            return "redirect:/user/login";
         }
      } else {
         rttr.addFlashAttribute("result", 0);
         return "redirect:/user/login";
      }
   }

   /* 아이디 찾기 */

   // 아이디 찾기 메인으로 이동
   @RequestMapping(value = "/findId", method = RequestMethod.GET)
   public String findIdForm() {
      return "/userinfo/findId"; // ID 찾기 페이지로 이동
   }

   // 아이디 찾기
   @RequestMapping(value = "/findId", method = RequestMethod.POST)
   public String findId(@RequestParam("email") String email, Model model) {
      Userinfo userinfo = userinfoservice.findUserByEmail(email);

      if (userinfo != null && userinfo.getStatus() != 3) { // status 값이 3인 경우 검색하지 않음
         model.addAttribute("foundId", userinfo.getId());
      } else {
         model.addAttribute("notFound", true);
      }
      return "/userinfo/findIdResult"; // ID 찾기 결과 페이지로 이동
   }

   /* 비밀번호 찾기 */

   // 비밀번호 찾기 메인
   @RequestMapping(value = "/findPw", method = RequestMethod.GET)
   public String findPwForm() {
      return "/userinfo/findPw";
   }

   // 비밀번호 찾기
   @RequestMapping(value = "/findPw", method = RequestMethod.POST)
   public String findPw(@RequestParam("email") String email, @RequestParam("id") String enteredUserId, Model model) {
	   
      Userinfo userinfo = userinfoservice.findUserByEmail(email);

      if (userinfo != null && userinfo.getStatus() != 3) {
         // 아이디와 이메일이 일치하는지 검증
         if (enteredUserId.equals(userinfo.getId())) {
            String newPassword = generateRandomPassword(); // 임시 비밀번호 생성
            mailService.sendPwEmail(email, newPassword); // 메일 전송 서비스 이용

            String encryptedPassword = pwEncoder.encode(newPassword); // 비밀번호 암호화
            userinfoservice.updatePasswordByEmail(email, encryptedPassword); // 비밀번호 변경

            model.addAttribute("foundUserinfoId", userinfo.getId());
            model.addAttribute("foundPw", newPassword);

            return "/userinfo/findPwResult"; // 비밀번호 찾기 결과 페이지로 이동
         } else {
            model.addAttribute("notFound", true);
            return "/userinfo/findPwResult"; // 비밀번호 찾기 결과 페이지로 이동
         }
      } else {
         model.addAttribute("notFound", true);
         return "/userinfo/findPwResult"; // 비밀번호 찾기 결과 페이지로 이동
      }
   }

   // 임시 비밀번호 생성 함수
   private String generateRandomPassword() {
      Random r = new Random();
      int passwordLength = 6;
      String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      StringBuilder newPassword = new StringBuilder();

      for (int i = 0; i < passwordLength; i++) {
         int index = r.nextInt(characters.length());
         newPassword.append(characters.charAt(index));
      }
      return newPassword.toString();
   }

   /* 관리자 */

   // 사용자 정보, 마지막 로그인 시간 가져오기
   @RequestMapping(value = "/user", method = RequestMethod.GET)
   public String userGET(Model model, HttpSession session) {
      // 사용자 정보와 마지막 로그인 시간 가져오기
      Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");

      if (userinfo == null) {
         // 로그인되지 않은 상태에서 main 페이지 접근 시 처리 (예: 로그인 페이지로 리디렉션)
         return "redirect:/user/login";
      }

      // 사용자 정보에서 마지막 로그인 시간 가져오기
      String lastLoginDate = userinfo.getLogdate(); // 사용자 정보의 마지막 로그인 시간 필드를 가져온다고 가정

      // 모델에 데이터 추가
      model.addAttribute("lastLoginDate", lastLoginDate);

      return "userinfo/user";
   }

   /* 마이페이지 */

   // 마이페이지 메인으로 이동
   @RequestMapping(value = "/mypage", method = RequestMethod.GET)
   public String mypageGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      String userinfoIdVal = userinfo.getId();
      
      logger.info("아이디 값 = " + userinfoIdVal);

      List<Pack> heartList = packageHeartService.getUserHeartListById(userinfo.getId());
      List<Orders> ordersList = ordersService.getUserPaymentListById(userinfoIdVal);

      model.addAttribute("heartList", heartList);
      model.addAttribute("userinfo", userinfo);
      model.addAttribute("ordersList", ordersList);

      return "mypage/main";
   }

   // 회원정보 수정 페이지 이동
   @RequestMapping(value = "/modify", method = RequestMethod.GET)
   public String modifyGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      model.addAttribute("userinfo", userinfo);

      return "mypage/modify";
   }

   // 회원정보 수정 
   @RequestMapping(value = "/modify", method = RequestMethod.POST)
   public String modifyPOST(Userinfo userinfo) throws Exception {
      userinfoservice.modifyUserinfo(userinfo);

      return "redirect:/user/mypage";
   }

   // 회원정보 변경 확인 페이지 (비밀번호 확인) 이동
   @RequestMapping(value = "/pwCheck", method = RequestMethod.GET)
   public String pwCheckGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      model.addAttribute("userinfo", userinfo);

      return "mypage/pwCheck";
   }

   // 회원정보 변경 확인 (비밀번호 확인) 
   @RequestMapping(value = "/pwCheck", method = RequestMethod.POST)
   public String pwCheckPOST(@ModelAttribute Userinfo userinfo, RedirectAttributes rttr, Authentication authentication)
         throws LoginAuthFailException, UserinfoNotFoundException {

      CustomUserDetails userinfoVal = (CustomUserDetails) authentication.getPrincipal();
      Userinfo user = userinfoservice.getUserinfoById(userinfoVal.getId());

      if (user == null) {
         throw new LoginAuthFailException("유저 정보가 없습니다.");
      }

      String birth = user.getBirth().substring(0, 10);
      user.setBirth(birth);

      String rawPw = userinfo.getPw();// 사용자 입력 비밀번호
      String encodePw = user.getPw();// DB에서 가져온 사용자 비밀번호

      if (pwEncoder.matches(rawPw, encodePw)) {
         userinfoservice.updateUserLogindate(user.getId());
         return "redirect:/user/modify";
      } else {
         rttr.addFlashAttribute("result", 0);
         return "redirect:/user/pwCheck";
      }
   }

   // 비밀번호 변경 - GET
   @RequestMapping(value = "/modifypw", method = RequestMethod.GET)
   public String modifypwGET() {
      return "mypage/modify_pw";
   }

   // 비밀번호 변경 - POST
   @RequestMapping(value = "/modifypw", method = RequestMethod.POST)
   public String modiftpwPOST(Authentication authentication, Userinfo userinfo, @RequestParam String updatePw,
         Model model) throws LoginAuthFailException, UserinfoNotFoundException {

      CustomUserDetails userinfoVal = (CustomUserDetails) authentication.getPrincipal();

      Userinfo lto = userinfoservice.getUserinfoById(userinfoVal.getId());

      if (lto == null) {
         throw new UserinfoNotFoundException("회원 정보를 찾을 수 없습니다.");
      }

      String rawPw = userinfo.getPw();
      String encodePw = lto.getPw();

      if (!pwEncoder.matches(rawPw, encodePw)) {
         throw new LoginAuthFailException("비밀번호가 맞지 않습니다.");
      }

      lto.setPw(pwEncoder.encode(updatePw));
      userinfoservice.modifyPw(lto);
      return "redirect:/user/mypage";
   }

   // 회원 탈퇴 확인 페이지 (비밀번호 확인) - GET
   @RequestMapping(value = "/removePwCheck", method = RequestMethod.GET)
   public String removeUserinfoGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      model.addAttribute("userinfo", userinfo);

      return "mypage/remove";
   }

   // 회원 탈퇴 확인 페이지 (비밀번호 확인) - POST
   @RequestMapping(value = "/removePwCheck", method = RequestMethod.POST)
   public String removeUserinfoPOST(@ModelAttribute Userinfo userinfo, RedirectAttributes rttr,
         Authentication authentication) throws LoginAuthFailException, UserinfoNotFoundException {

      CustomUserDetails userinfoVal = (CustomUserDetails) authentication.getPrincipal();

      Userinfo lto = userinfoservice.getUserinfoById(userinfoVal.getId());

      if (lto != null) {
         String rawPw = userinfo.getPw();
         String encodePw = lto.getPw();

         if (pwEncoder.matches(rawPw, encodePw)) {
            userinfoservice.removeUserinfo(userinfoVal.getId());

            return "redirect:/";
         } else {
            rttr.addFlashAttribute("result", 0);
            return "redirect:/user/remove";
         }
      } else {
         throw new LoginAuthFailException("유저 정보가 없습니다.");
      }
   }

   // 회원 탈퇴 시 status 값 변경
   @RequestMapping(value = "/remove")
   public String remove(@RequestParam String id, Authentication authentication, HttpServletRequest request)
         throws UserinfoNotFoundException {
      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      Userinfo userinfoVal = userinfoservice.getUserinfoById(userinfo.getId());

      userinfoservice.removeUserinfo(id);

      // 스프링 시큐리티 로그아웃 수행
      new SecurityContextLogoutHandler().logout(request, null, null);

      return "redirect:/user/login";
   }

   // 고객센터 페이지 이동
   @GetMapping(value = "/center")
   public String centerGET() {
      return "userinfo/center";
   }

   /* 마이페이지 추가 */

   // 작성한 리뷰 페이지로 이동
   @RequestMapping(value = "/myReview", method = RequestMethod.GET)
   public String myReviewGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      List<Review> reviewList = reviewService.getUserReviewListById(userinfo.getId());

      model.addAttribute("userinfo", userinfo);
      model.addAttribute("reviewList", reviewList);

      return "mypage/my_review";
   }

   // 작성한 1:1 문의 페이지로 이동
   @RequestMapping(value = "/myQa", method = RequestMethod.GET)
   public String myQaGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      List<Qa> qaList = qaService.getUserQaListById(userinfo.getId());

      model.addAttribute("userinfo", userinfo);
      model.addAttribute("qaList", qaList);

      return "mypage/my_qa";
   }

   // 내가 작성한 DIY 페이지로 이동
   @RequestMapping(value = "/myDiy", method = RequestMethod.GET)
   public String myDiyGET(Authentication authentication, Model model) {

      CustomUserDetails userinfo = (CustomUserDetails) authentication.getPrincipal();

      List<Diy> diyList = diyService.getUserDiyListById(userinfo.getId());

      model.addAttribute("userinfo", userinfo);
      model.addAttribute("diyList", diyList);

      return "mypage/my_diy";
   }
}