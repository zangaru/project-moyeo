package com.moyeo.dto;


import org.springframework.format.annotation.DateTimeFormat;


// import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Diy {
        private int diyIdx;
        private String userinfoId;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private String diyStartdate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private String diyEnddate;
        private int diyPeople;
        private String diyLoc;
        private int diyPrice;
        private String diyMap;
        private String diyRegdate;
        private String diyModifydate;
        private long loveCount; //- love는 long
        private String diyTitle;
        private String diyIntroduction;
        private String diyThumbnail;
        private String diyContent1Img;
        private String diyContent1;
        private String diyContent2Img;
        private String diyContent2;
        private String diyContent3Img;
        private String diyContent3;
        private String diyContent4Img;
        private String diyContent4;
        private String diyContent5Img;
        private String diyContent5;
        private String diyContent6Img;
        private String diyContent6;
        private String diyContent7Img;
        private String diyContent7;
        
       //  private LocalDateTime time;
        
}