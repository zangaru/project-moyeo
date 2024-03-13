<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>    
<!DOCTYPE html>
<html lang="utf-8">
<head><script src="https://code.jquery.com/jquery-3.6.0.min.js"></script></head>
   <style>
/* + 버튼과 - 버튼의 스타일 설정 */
.plus-btn,
.minus-btn {
    font-size: 18px; /* 원하는 폰트 크기로 조절하세요 */
    padding: 0px 10px; /* 원하는 여백 값으로 조절하세요 */
}

</style>       
  
<body id="body" class="up-scroll">
  <div class="main-wrapper packages-grid">

   
<!-- ====================================
———   PAGE TITLE
===================================== -->
<section class="page-title">
  <div class="page-title-img bg-img bg-overlay-darken" style="background-image: url(${pageContext.request.contextPath}/assets/img/pages/page-title-bg6.jpg);">
    <div class="container">
      <div class="row align-items-center justify-content-center" style="height: 200px;">
        <div class="col-lg-6">
          <div class="page-title-content">
            <div class="title-border">
              <h2 class="text-uppercase text-white font-weight-bold">장바구니</h2>
            </div>
            <p class="text-white mb-0"></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>


<!-- ====================================
———   cart SECTION
===================================== -->


   <section class="cartList">
       <div class="container">
           <div class="py-10">
               <div class="cart_list">
                   
                   <div class="diy_form_title">
                       장바구니
                   </div>
                   
                   <div class="cart_f">
                       <form action="<c:url value='/cart/delete'/>" method="POST" id="deleteForm">
                           <table>
                               <colgroup>
                                   <col width="10%">
                                   <col width="15%">
                                   <col width="45%">
                                   <col width="10%">
                                   <col width="10%">
                                   <col width="10%">
                               </colgroup>
       
                               <tr class="th">
                                   <td class="t1"><label><input type="checkbox" id="selectAllCheckbox"> </label></td>
                                   <td class="t1"></td>
                                   <td class="t1">상품명</td>
                                   <td class="t1">성인</td>
                                   <td class="t1">소인</td>
                                   <td class="t1">합계금액</td>
                               </tr>
       
                               <c:forEach var="cartItem" items="${map.list}" varStatus="i">
                         <tr>
                             <td class="t2">
                                 <input type="checkbox" class="cartCheckbox" name="cartIdx" value="${cartItem.cartIdx}">
                             </td>
                             <td class="t2 t_img"> <img class="card-img-top rounded lazyestload" width="250" height="300" data-src="${pageContext.request.contextPath}/assets/img/upload/${cartItem.packPreviewImg}" alt="Card image cap"></td>
                             <td class="t2">${cartItem.packTitle}</td>
                             <td class="t2">
                                 <div class="input-group">
                                     <button type="button" class="btn btn-outline-secondary minus-btn" data-product-id="${cartItem.cartIdx}">-</button>
                                     <input type="number" class="form-control adult-count-input" value="${cartItem.packAdultcount}" min="0">
                                     <button type="button" class="btn btn-outline-secondary plus-btn" data-product-id="${cartItem.cartIdx}">+</button>
                                 </div>
                             </td>
                             <td class="t2">
                                 <div class="input-group">
                                     <button type="button" class="btn btn-outline-secondary minus-btn" data-product-id="${cartItem.cartIdx}">-</button>
                                     <input type="number" class="form-control child-count-input" value="${cartItem.packChildcount}" min="0">
                                     <button type="button" class="btn btn-outline-secondary plus-btn" data-product-id="${cartItem.cartIdx}">+</button>
                                 </div>
                             </td>
                             <td class="t2"><fmt:formatNumber value="${cartItem.packTotalprice}" pattern="###,###"/>원</td>
                         </tr>
                     </c:forEach>
                           </table>
       
                           <div class="cart_totbar">
                               <ul>
                                   <li class="tot1">총 ${map.count} 개의 상품</li>
                                   <li class="tot2">장바구니 총금액 </li>
                                   <li class="tot3">﻿<fmt:formatNumber value="${map.sumTotal}" pattern="###,###"/></li>
                               </ul>
                           </div>
                           <div class="cart_btn">
                               <button type="submit" class="b1" id="deleteButton">선택상품 삭제</button>
                               <button type="button" class="b1" id="updateButton">수정</button>    
                               <%-- <button type="button" class="b1" id="orderButton">선택상품 주문</button>--%>
                               <button type="button" class="b1" id="allorderButton">상품 주문</button>         
                           </div>   
                           <sec:csrfInput/>
                           </form>                
                           </div>
                   </div>
               </div><!-- cart_list -->
           </div><!-- py-10 -->
       </div><!-- container -->
</section>
<script>
     var csrfHeaderName="${_csrf.headerName}";
     var csrfTokenValue="${_csrf.token}";
        
     //CSRF 토큰 전달
     $(document).ajaxSend(function(e, xhr) {
        xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
     }); 
     
     
       document.getElementById('selectAllCheckbox').addEventListener('change', function () {
           var cartCheckboxes = document.getElementsByClassName('cartCheckbox');
           for (var i = 0; i < cartCheckboxes.length; i++) {
               cartCheckboxes[i].checked = this.checked;
           }
       });
   
   
       document.querySelectorAll('.plus-btn').forEach(function (button) {
           button.addEventListener('click', function () {
               var inputField = this.parentElement.querySelector('.form-control');
               inputField.value = parseInt(inputField.value) + 1;
           });
       });
   
       document.querySelectorAll('.minus-btn').forEach(function (button) {
           button.addEventListener('click', function () {
               var inputField = this.parentElement.querySelector('.form-control');
               if (parseInt(inputField.value) > 0) {
                   inputField.value = parseInt(inputField.value) - 1;
               }
           });
       });
       
    // 선택상품수정버튼 클릭 이벤트 핸들러
       document.getElementById('updateButton').addEventListener('click', function () {
           console.log("Update button clicked");

           var selectedCartIdx = [];
           var selectedAdultCounts = [];
           var selectedChildCounts = [];

           var cartCheckboxes = document.getElementsByClassName('cartCheckbox');
           var adultInputFields = document.querySelectorAll('.adult-count-input');
           var childInputFields = document.querySelectorAll('.child-count-input');

           for (var i = 0; i < cartCheckboxes.length; i++) {
               cartCheckboxes[i].checked = true; // 모든 체크박스 선택
               selectedCartIdx.push(cartCheckboxes[i].value);
               selectedAdultCounts.push(adultInputFields[i].value);
               selectedChildCounts.push(childInputFields[i].value);
           }

           // FormData 객체를 생성하여 데이터 추가
           var formData = new FormData();
           for (var i = 0; i < selectedCartIdx.length; i++) {
               formData.append("cartIdx", selectedCartIdx[i]);
               formData.append("packAdultcount", selectedAdultCounts[i]);
               formData.append("packChildcount", selectedChildCounts[i]);
           }

           // AJAX 요청을 보냅니다.
           fetch('/moyeo/cart/update', {
               method: 'POST',
               body: formData
           })
           .then(function (response) {
               if (response.ok) {
                   console.log('Update successful');
                   alert('수정되었습니다.');
                   location.reload(); // 서버로부터 업데이트가 완료되면 페이지 새로고침
               } else {
                   console.error('Update failed');
               }
           })
           .catch(function (error) {
               console.error('Fetch error:', error);
           });
       });
       
       // 버튼 클릭 이벤트 핸들러 함수
       document.getElementById("allorderButton").addEventListener("click", function() {
           window.location.href = "${pageContext.request.contextPath}/payment/pay";
       });
       
       document.getElementById("allorderButton").addEventListener("click", function () {
           var selectedCartIdx = [];
           var selectedAdultCounts = [];
           var selectedChildCounts = [];

           var cartCheckboxes = document.getElementsByClassName("cartCheckbox");
           var adultInputFields = document.querySelectorAll(".adult-count-input");
           var childInputFields = document.querySelectorAll(".child-count-input");

           for (var i = 0; i < cartCheckboxes.length; i++) {
               if (cartCheckboxes[i].checked) {
                   selectedCartIdx.push(cartCheckboxes[i].value);
                   selectedAdultCounts.push(adultInputFields[i].value);
                   selectedChildCounts.push(childInputFields[i].value);
               }
           }

           // 선택한 상품 정보를 URL 매개변수로 결제 페이지로 전달
           var queryParams = [];
           for (var j = 0; j < selectedCartIdx.length; j++) {
               queryParams.push(
                   "cartIdx=" + selectedCartIdx[j] +
                   "&packAdultcount=" + selectedAdultCounts[j] +
                   "&packChildcount=" + selectedChildCounts[j]
               );
           }

           if (queryParams.length > 0) {
               var queryString = queryParams.join("&");
               var paymentPageURL = "${pageContext.request.contextPath}/payment/pay?" + queryString;

               // 결제 페이지로 이동
               window.location.href = paymentPageURL;
           } else {
               // 선택한 상품이 없을 때 처리
               alert("선택한 상품이 없습니다.");
           }
       });
   </script>
  </body>
</html>