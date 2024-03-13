<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="utf-8">
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style> 
    .my{
        max-width: 1200px;
        padding: 1%;
        margin:0 auto;
    }
    
    .my h1{
        font-size: 3.5em;
        font-weight: 100;
        margin-top: 10%;
    }
    
    .my h1:nth-child(1){
       text-align: center;
       margin-top: 5%;
    }
    
    .my h4{
        font-size: 1.5em;
        font-weight: 400;
    }
    
    .quick_menu{
        width: 100%;
        height: 190px;
        margin-top: 5%;
    }
    
    .quick_menu ul{
        margin: 0;
        padding: 0;
        list-style: none;
        margin-top: 3%;
    }
    
    .quick_menu ul li input{
        width: 130px;
        height: 60px;
        background: #fff;
        float: left;
        margin-right: 1.2%;
        border: 1.2px solid #a1a1a1;
        font-size: 1.2em;
        border-radius: 5px;
    }

    .my #mypage{
        width: 100%;
        height: 500px;
        position: relative;
    }
    
    .my #mypage p{
        position: absolute;
        right: 0;
        top: 0;
        font-size: 1.2em;
        font-weight: 100;
    }
    
    .my #mypage a{
       color: #000;
    }
    
    .my #mypage div{
        width: 100%;
        height: 350px;
        border: 1.2px solid #a1a1a1;
        border-radius: 10px;
        margin-top: 2%;
    }
    
    .heart {
       display: flex;
       padding-left: 10px;
    }
    
    .heart li {
       width: 250px;
       margin-right: 35px;
    }
    
    .heart li img {
       width: 250px;
       height: 280px;
       border-radius: 10px;
       margin: 15px;
    }
    
    .heart li span {
       display: block;
       font-size: 20px;
       margin: -5px 20px;
    }
    
    .my-main {
         text-decoration: none; /* 밑줄 제거 */
       color: black; /* 검정색으로 변경 */
    }
</style>
</head>     

<body id="body" class="up-scroll">  


<div class="my">
   <h1><a href="${pageContext.request.contextPath}/user/mypage" class="my-main">마이페이지</a></h1>

   <h1><u>${userinfo.name}</u>님, 안녕하세요!</h1>

    <section class="quick_menu">
        <h4>서비스 바로가기</h4>
        <ul>
            <li>
                <input type="button" value="회원정보변경"
                    onclick="location.href='${pageContext.request.contextPath}/user/pwCheck';">
            </li>
            <li>
                <input type="button" value="비밀번호변경"
                    onclick="location.href='${pageContext.request.contextPath}/user/modifypw';">
            </li>
            <li>
                <input type="button" value="후기"
                    onclick="location.href='${pageContext.request.contextPath}/user/myReview';">
            </li>

           
            <li>
                <input type="button" value="1:1 문의 내역"
                     onclick="location.href='${pageContext.request.contextPath}/user/myQa';">
            </li>
            
            <li>
                <input type="button" value="DIY"
                     onclick="location.href='${pageContext.request.contextPath}/user/myDiy';">
            </li>
            
            <li>
                <input type="button" value="장바구니"
                     onclick="location.href='${pageContext.request.contextPath}/cart/list';">
            </li>

            <li>
                <input type="button" value="탈퇴하기"
                    onclick="location.href='${pageContext.request.contextPath}/user/removePwCheck';">
            </li>
        </ul>
    </section>

    <section id="mypage">
        <h4>예약 내역</h4>
        <div>
        <ul class="heart">
           <c:forEach var="pack" items="${ordersList}" begin="0" end="3">
               <li>
                   <a href="${pageContext.request.contextPath}/package/detail/${pack.packIdx}">
                       <img src="<c:url value='/assets/img/upload/${pack.packPreviewImg}'/>" alt="">
                       <span>${pack.packTitle}</span>
                   </a>
                   <button onclick="writeReview(${pack.packIdx}, '${pack.packTitle}')">리뷰 작성</button>
                   <button id="cancelButton">결제 취소</button>
               </li>
           </c:forEach>
       </ul>
        </div>
    </section>
    
    <section id="mypage">
       <h4>찜 내역</h4>
       <div>
       <ul class="heart">
           <c:forEach var="pack" items="${heartList}" begin="0" end="3">
               <li>
                   <a href="${pageContext.request.contextPath}/package/detail/${pack.packIdx}">
                       <img src="<c:url value='/assets/img/upload/${pack.packPreviewImg}'/>" alt="">
                       <span>${pack.packTitle}</span>
                   </a>
               </li>
           </c:forEach>
       </ul>
      </div>
   </section>
</div>

<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
  
<script>
/*
    function cancelOrder(packIdx) {
        window.location.href = '${pageContext.request.contextPath}/cancel-url/' + packIdx;
    }
*/
    function writeReview(packIdx) {
        window.location.href = '${pageContext.request.contextPath}/review/write/';
    }
    
	function writeReview(packIdx, packTitle) {
	    location.href = '/moyeo/review/write?packTitle=' + encodeURIComponent(packTitle);
	}
</script>
<script>
function cancelPay() {
  jQuery.ajax({
    // 예: http://www.myservice.com/payments/cancel
    "url": "<c:url value='/payment/cancel'/>", 
    "type": "POST",
    "contentType": "application/json",
    "data": JSON.stringify({
      "merchant_uid": "merchant_1697779523736", // 예: ORD20180131-0000011
      "cancel_request_amount": 1, // 환불금액
      "reason": "테스트 결제 환불" // 환불사유
      /*
      // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
      "refund_holder": "홍길동", 
      // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(예: KG이니시스의 경우 신한은행은 88번)
      "refund_bank": "88" 
      // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
      "refund_account": "56211105948400" 
      */
    }),
    "dataType": "json"
  });
}

$(document).ready(function() {
  // 버튼 클릭 시 `cancelPay` 함수 호출
  $("#cancelButton").click(function() {
    cancelPay();
  });
});
</script>


</body>
</html>