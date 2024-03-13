<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="utf-8">
  
<head><script src="https://code.jquery.com/jquery-3.6.0.min.js"></script></head>
<body id="body" class="up-scroll">

<div class="main-wrapper packages-grid">

<div class="pack_detail_title">
   <p>${pack.packTitle} </p>
</div>

<!-- ====================================
———   PACKAGES SECTION
===================================== -->
<section class="py-10 pd">
  <div class="container">
    <div class="row">
      <div class="col-md-5 col-lg-4 order-2">
          <div class="card border">
            <h2 class="card-header text-uppercase text-center bg-smoke border-bottom">
              옵션선택
            </h2>
         
         
            <div class="card-body px-3 py-4">
              <div class="border-bottom mb-5">
                <div class="form-group mb-5">
                  <div class="row">
                    <label for="inputTime" class="col-xl-5 col-form-label text-center text-xl-right px-2">여행 일정</label>
               
               <!-- 데이트피커 부분  -->
               <!--  <div class="col-xl-7">
                      <div class="form-group form-group-icon form-group-icon-category-2 mb-0">
                        <i class="far fa-calendar-alt" aria-hidden="true"></i>
                        <input type="text" class="form-control daterange-picker-category-2" autocomplete="off" name="dateRange" value=""
                          placeholder="MM/DD/YYYY">
                      </div>
                    </div>  -->
                    
                         <div class="pack_date">
                            <p>${pack.packStartDate}</p>
                            <p>${pack.packEndDate}</p>
                          </div>
   
                     
                  </div>
                </div>
                

                <!-- <div class="form-group mb-5">
                  <div class="row">
                    <label for="inputTime" class="col-xl-5 col-form-label text-center text-xl-right px-2">Select a time
                      slot:</label>
                    <div class="col-xl-7">
                      <div class="form-group mb-0">
                        <div class="select-default select-category-2 timer">
                          <select class="select-option">
                            <option>9:00 AM</option>
                            <option>10:00 AM</option>
                            <option>11:00 AM</option>
                            <option>12:00 AM</option>
                            <option>13:00 AM</option>
                            <option>14:00 AM</option>
                            <option>15:00 AM</option>
                            <option>16:00 AM</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div> -->
<form action="<c:url value='/cart/insert'/>" method="post">
               <c:set var="packIdx" value="${pack.packIdx}" />
<div class="form-group mb-5">
    <div class="row align-items-center">
        <label class="control-label col-xl-5 text-center text-xl-right">성인</label>
        <div class="col-xl-5">
            <div class="count-input mx-auto">
                <a class="incr-btn adult" data-action="decrease" href="javascript:void(0)">–</a>
                <input class="quantity adult" type="number" value="0" name="packAdultcount">
                <a class="incr-btn adult" data-action="increase" href="javascript:void(0)">+</a>
            </div>
        </div>
        <div class="col-xl-2">
            <p class="text-center mt-3 mt-xl-0 mb-0">${pack.packAdultPrice}원</p>
        </div>
    </div>
</div>

<div class="form-group mb-5">
    <div class="row align-items-center">
        <label class="control-label col-xl-5 text-center text-xl-right">소인</label>
        <div class="col-xl-5">
            <div class="count-input mx-auto">
                <a class="incr-btn child" data-action="decrease" href="javascript:void(0)">–</a>
                <input class="quantity child" type="number" value="0" name="packChildcount">
                <a class="incr-btn child" data-action="increase" href="javascript:void(0)">+</a>
            </div>
        </div>
        <div class="col-xl-2">
            <p class="text-center mt-3 mt-xl-0 mb-0">${pack.packChildPrice}원</p>
        </div>
    </div>
</div>
              </div>

              <div class="d-flex justify-content-between border-bottom mb-5 pb-5">
                <span class="text-uppercase h4 mb-0">Total cost</span>
                 <span class="font-weight-bold text-primary h3 mb-0" id="total-cost">0원</span>
              
             
              </div>

              <div class="text-center px-4">
                <button type="button" onclick="location.href='booking-step-1.html';"
                  class="btn btn-hover btn-lg btn-block btn-outline-secondary text-uppercase bsize">
                  예약하기 <span class="ms-4"><i class="fa fa-angle-right" aria-hidden="true"></i></span>
                </button>
           		  <input type="hidden" name="packIdx" value="${packIdx}" />

				<button type="submit" class="btn btn-hover btn-lg btn-block btn-outline-secondary text-uppercase bsize" value="장바구니"onclick="showAlert()">
					장바구니<span class="ms-4"><i class="fa fa-angle-right" aria-hidden="true"></i></span>
				</button>
								
				<%--<button type="button" onclick="location.href='booking-step-1.html';"
                  class="btn btn-hover btn-lg btn-block btn-outline-secondary text-uppercase bsize">
                  하트수정<span class="ms-4"><i class="fa fa-angle-right" aria-hidden="true"></i></span>
                </button> --%>
				

				
				<!-- 찜 기능 -->
				<button type="button" class="btn btn-hover btn-lg btn-block btn-outline-secondary text-uppercase bsize" id="packageHeartBtn">
				    <img id="heartImage" src="${pageContext.request.contextPath}/assets/img/package_heart.png" alt="하트 이미지" style="width: 50px; height: 50px;">
				    <span class="ms-4"></span>
				</button>
				<input type="hidden" name="userinfoId" value="${userinfo.id}">
				<input type="hidden" name="packHeartIdx" value="${packHeartList.packHeartIdx}" id="packHeartIdx">
				
             	
              </div>
            </div>
          </div>
        </form>
      </div>
    
 
      
     <!-- 이미지 출력 -->
      <div class="col-md-7 col-lg-8">
        <div id="package-slider" class="owl-carousel owl-theme package-slider">
          <div class="item">
            <img class="lazyestload" data-src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg1}" src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg1}" alt="image">
          </div>

          <div class="item">
            <img class="lazyestload" data-src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg2}" src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg2}" alt="image">
          </div>

          <div class="item">
            <img class="lazyestload" data-src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg3}" src="${pageContext.request.contextPath}/assets/img/upload/${pack.packSlideImg3}" alt="image">
          </div>
        </div>

      

      <div class="package_con">
         <div class="pack_img1">

         
         <img src="<c:url value='/assets/img/upload/${pack.packCalendarImg}'/>" alt="">   
         
         </div>
          <img src="<c:url value='/assets/img/upload/${pack.packContentImg1}'/>" alt=""> 
      </div>
      
      
      
	<!-- -----------------------------------후기----------------------- -->
        <div class="mb-7">

          <div class="d-flex align-items-center mb-6">
            <h3 class="text-uppercase mb-0">
              <span class="me-2"  style="font-weight:600; font-size:25px;">Review</span>

              <span class="text-warning">
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fas fa-star-half-alt" aria-hidden="true"></i>
              </span>
            </h3>
          </div>

	<c:forEach var="review" items="${latestReviews}">
          <div class="media mb-6">
            <a class="me-6 pack_review" href=""> <!-- 사진 -->
              <img class="rounded" src="<c:url value='/assets/img/upload/${review.reviewImg}'/>" alt="">
            </a>

            <div class="media-body"> <!-- 제목, 내용, 이름날짜 -->
              <h5 style="font-weight:600;">${review.reviewTitle}</h5>
              <p class="mb-0" style="color:#000;">${review.reviewContent}</p>
              <p class="mb-0">${review.userinfoId} / ${review.reviewRegdate}</p>
            </div>
          </div>
	</c:forEach>



<!-- 
          <div class="media mb-6">
            <a class="me-6" href="">
              <img class="rounded lazyestload" data-src="${pageContext.request.contextPath}/assets/img/blog/comments-01.jpg" src="${pageContext.request.contextPath}/assets/img/blog/comments-01.jpg" alt="Generic placeholder image">
            </a>

            <div class="media-body">
              <h5>Amanda Smith</h5>
              <span class="star add-rating-default pb-1"></span>
              <p class="mb-0">Nunc ultricies dui sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                incididunt ut labore et dolore magna
                aliqua.</p>
            </div>
          </div>
 -->
 
 <!-- 
          <div class="media">
            <a class="me-6" href="">
              <img class="rounded lazyestload" data-src="${pageContext.request.contextPath}/assets/img/blog/comments-03.jpg" src="${pageContext.request.contextPath}/assets/img/blog/comments-03.jpg" alt="Generic placeholder image">
            </a>
            <div class="media-body">
              <h5>Rodney Artichoke</h5>
              <span class="star add-rating-default pb-1"></span>
              <p class="mb-0">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
                laudan totam rem ape riam</p>
            </div>
          </div> 
-->
        </div>

<!-- ------------------------------//후기끝----------------------- -->



      <!-- 
      
          <form class="mb-9 mb-md-0">
          <h3 class="text-uppercase mb-6">Leave your review</h3>

          <p class="rating-view d-flex align-items-center">
            <span class="content-view">Want to Rate it?</span>
            <span class="star add-rating-default ms-2"></span>
          </p>

          <div class="form-group mb-6">
            <textarea class="form-control border-0 bg-smoke" placeholder="Comment" rows="6"></textarea>
          </div>

          <div class="">
            <button type="button" onclick="location.href='javascript:void(0)';"
              class="btn btn-hover btn-outline-secondary text-uppercase">
              Submit
            </button>
          </div>
        </form>
      
       -->
       <button type="button" id="removeBtn">삭제</button>
       
      </div>
    </div>
  </div>
</section>

         


<!-- ====================================
———   PACKAGES LIKE SECTION
===================================== -->
<!-- <section class="pb-10">
  <div class="container">
    <div class="text-uppercase mb-4">
      <h2 class="mb-0">you may also like</h2>
    </div>
    <div class="row">
      <div class="col-md-6 col-lg-4 mb-5 mb-lg-0">
        <div class="card card-bg">
          <a href="single-package-right-sidebar.html" class="position-relative">
            <img class="card-img-top lazyestload" data-src="assets/img/home/deal/deal-01.jpg" src="assets/img/home/deal/deal-01.jpg" alt="Card image cap">
            <div class="card-img-overlay card-hover-overlay rounded-top d-flex flex-column">
              <div class="badge bg-primary badge-rounded-circle">
                <span class="d-block">
                  45%<br>off
                </span>
              </div>

              <ul class="list-unstyled d-flex mt-auto text-warning mb-0">
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star" aria-hidden="true"></i>
                </li>
              </ul>

              <ul class="list-unstyled d-flex text-white font-weight-bold mb-0">
                <li class="border-right border-white pe-2">7 days</li>
                <li class="border-right border-white px-2">15 hrs</li>
                <li class="ps-2">15 min</li>
              </ul>
            </div>
          </a>

          <div class="card-body">
            <h5>
              <a href="single-package-right-sidebar.html" class="card-title text-uppercase">France / Paris</a>
            </h5>

            <p class="mb-7">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt
              labore dolore.</p>

            <div class="d-flex justify-content-between align-items-center">
              <div>
                <p class="mb-0 text-capitalize">Start from</p>
                <h3 class="text-primary">$299</h3>
              </div>

              <div>
                <a href="single-package-fullwidth.html" class="btn btn-xs btn-outline-secondary text-uppercase">Details</a>
                <a href="" class="btn btn-xs btn-outline-secondary px-4">
                  <i class="fas fa-map-marker-alt" aria-hidden="true"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-md-6 col-lg-4 mb-5 mb-lg-0">
        <div class="card card-bg">
          <a href="single-package-right-sidebar.html" class="position-relative">
            <img class="card-img-top lazyestload" data-src="assets/img/home/deal/deal-02.jpg" src="assets/img/home/deal/deal-02.jpg" alt="Card image cap">
            <div class="card-img-overlay card-hover-overlay rounded-top d-flex flex-column">
              <div class="badge bg-primary badge-rounded-circle">
                <span class="d-block">
                  50%<br>off
                </span>
              </div>

              <ul class="list-unstyled d-flex mt-auto text-warning mb-0">
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star" aria-hidden="true"></i>
                </li>
              </ul>

              <ul class="list-unstyled d-flex text-white font-weight-bold mb-0">
                <li class="border-right border-white pe-2">7 days</li>
                <li class="border-right border-white px-2">15 hrs</li>
                <li class="ps-2">15 min</li>
              </ul>
            </div>
          </a>

          <div class="card-body">
            <h5>
              <a href="single-package-right-sidebar.html" class="card-title text-uppercase">Australia / Canberra</a>
            </h5>

            <p class="mb-7">Integer purus ex, dictum nec elementum eu, tristique vel lectus. Donec rutrum lectus et
              pharetra
              egestas.</p>

            <div class="d-flex justify-content-between align-items-center">
              <div>
                <p class="mb-0 text-capitalize">Start from</p>
                <h3 class="text-primary">$299</h3>
              </div>

              <div>
                <a href="single-package-fullwidth.html" class="btn btn-xs btn-outline-secondary text-uppercase">Details</a>
                <a href="" class="btn btn-xs btn-outline-secondary px-4">
                  <i class="fas fa-map-marker-alt" aria-hidden="true"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-md-6 col-lg-4">
        <div class="card card-bg">
          <a href="single-package-right-sidebar.html" class="position-relative">
            <img class="card-img-top lazyestload" data-src="assets/img/home/deal/deal-03.jpg" src="assets/img/home/deal/deal-03.jpg" alt="Card image cap">
            <div class="card-img-overlay card-hover-overlay rounded-top d-flex flex-column">
              <div class="badge bg-primary badge-rounded-circle">
                <span class="d-block">
                  40%<br>off
                </span>
              </div>

              <ul class="list-unstyled d-flex mt-auto text-warning mb-0">
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star me-1" aria-hidden="true"></i>
                </li>
                <li>
                  <i class="fa fa-star" aria-hidden="true"></i>
                </li>
              </ul>

              <ul class="list-unstyled d-flex text-white font-weight-bold mb-0">
                <li class="border-right border-white pe-2">7 days</li>
                <li class="border-right border-white px-2">15 hrs</li>
                <li class="ps-2">15 min</li>
              </ul>
            </div>
          </a>

          <div class="card-body">
            <h5>
              <a href="single-package-right-sidebar.html" class="card-title text-uppercase">Jarmani / Berlin</a>
            </h5>

            <p class="mb-7">Donec lacus felis, dapibus males uada massa non, ferm entum tincidunt quam. Orci varius
              natoque.</p>

            <div class="d-flex justify-content-between align-items-center">
              <div>
                <p class="mb-0 text-capitalize">Start from</p>
                <h3 class="text-primary">$299</h3>
              </div>

              <div>
                <a href="single-package-fullwidth.html" class="btn btn-xs btn-outline-secondary text-uppercase">Details</a>
                <a href="" class="btn btn-xs btn-outline-secondary px-4">
                  <i class="fas fa-map-marker-alt" aria-hidden="true"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
 -->


  </div><!-- element wrapper ends -->
   

      <!-- 가격 계산 -->
      <script>
      // 성인 가격 소인 가격 설정 
       const adultPrice = ${pack.packAdultPrice};
       const childPrice = ${pack.packChildPrice};
   
       // 요소 선택
       const adultInput = document.querySelector('.quantity.adult');
       const childInput = document.querySelector('.quantity.child');
       const totalCostSpan = document.getElementById('total-cost');
    
    
       $(document).ready(function () {
           $(".incr-btn.adult").click(function () {
             const adultTotalCost = adultInput.value * adultPrice;
             const childTotalCost = childInput.value * childPrice;
             const totalCost = adultTotalCost + childTotalCost;
             totalCostSpan.textContent = totalCost +"원" ;
           });

           $(".incr-btn.child").click(function () {
             const adultTotalCost = adultInput.value * adultPrice;
             const childTotalCost = childInput.value * childPrice;
             const totalCost = adultTotalCost + childTotalCost;
             totalCostSpan.textContent = totalCost +"원";
           });
         });
     function showAlert() {
       var successMessage = "${successMessage}";
       var errorMessage = "${errorMessage}";

       if (successMessage) {
           alert(successMessage);
       } else if (errorMessage) {
           alert(errorMessage);
       }
    }
     
     
   /*패키지삭제*/
   $("#removeBtn").click(function() {
		if(confirm("게시글을 삭제 하시겠습니까?")) {
			$("#linkForm").attr("action", "<c:url value="/admin/removePackage"/>").submit();
		}
	});
   
   
    /* 찜 기능 구현 */
	
	// 클릭한 하트의 상태를 나타내는 변수
	var isHeartAdded = ${isHeartAdded}; 	
	
	// 하트 이미지 클릭 시
	document.getElementById('packageHeartBtn').addEventListener('click',function (){
	    isHeartAdded = !isHeartAdded; // 상태 토글

	    // 이미지 변경
	    var heartImage = document.getElementById('heartImage');
	    if (isHeartAdded) {
	        heartImage.src = "${pageContext.request.contextPath}/assets/img/package_heart_red.png";
	        addToWishlist(); // 찜 목록에 추가
	    } else {
	        heartImage.src = "${pageContext.request.contextPath}/assets/img/package_heart.png";
	        removeFromWishlist(); // 찜 목록에서 삭제
	    }
	});

	// 찜 목록에 추가하는 함수
	function addToWishlist() {
	    var packIdx = ${pack.packIdx};
	    var userinfoId = "${userinfo.id}";

	    $.ajax({
	        url: "${pageContext.request.contextPath}/package/addToPackageHeartList",
	        method: "POST",
	        data: {
	            packIdx: packIdx,
	            userinfoId: userinfoId
	        },
	        success: function (response) {
	        	isHeartAdded = true; // 찜이 추가되었으므로 상태를 true로 변경
	            console.log("찜 목록에 추가되었습니다.");
	        },
	        error: function () {
	            console.error("찜 목록 추가에 실패했습니다.");
	        }
	    });
	}

	// 찜 목록에서 삭제하는 함수
	function removeFromWishlist() {
	    //var packHeartIdx = ${packHeartList.packHeartIdx};
	    var packHeartIdx = document.getElementById("packHeartIdx").value
	    var userinfoId = "${userinfo.id}";

	    $.ajax({
	        url: "${pageContext.request.contextPath}/package/removeFromPackageHeartList", 
	        method: "POST",
	        data: {
	        	packHeartIdx: packHeartIdx,
	            userinfoId: userinfoId
	        },
	        success: function (response) {
	            console.log("찜 목록에서 삭제되었습니다.");
	            isHeartAdded = false; // 찜이 삭제되었으므로 상태를 false로 변경
	            updateHeartImage(); // 하트 이미지 업데이트
	        },
	        error: function () {
	            console.error("찜 삭제에 실패했습니다.");
	        }
	    });
	}
	
	// 하트 이미지 업데이트 함수
	function updateHeartImage() {
	    var heartImage = document.getElementById('heartImage');
	    if (isHeartAdded) {
	        heartImage.src = "${pageContext.request.contextPath}/assets/img/package_heart_red.png";
	    } else {
	        heartImage.src = "${pageContext.request.contextPath}/assets/img/package_heart.png";
	    }
	}

	// 페이지 로드 시 초기 하트 이미지 설정
	$(document).ready(function () {
	    updateHeartImage();
	});
   
   </script>
   
  </body>
</html>