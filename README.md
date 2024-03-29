# 구해줘 홈즈

## 프로젝트 요약

### 지도API와 아파트 실거래 공공데이터를 활용한 아파트 매물 조회 및 중개 서비스

![메인](https://user-images.githubusercontent.com/37106496/203910156-af354d89-8011-49c5-a0ab-813a83b01de5.PNG)

![](../header.png)

## 개발 기간 및 팀원

**개발 기간** : 7일

**팀원** : 김진호 , 권혁근

## 개발 환경

### Backend

- SpringBoot
- MySql
- Lombok
- MyBatis

### Frontend

- Vue2.0
- Vuex
- Axios
- vue-router
- Vuetify
- Vue-Toastification

## 디렉토리 구조

### Backend

├─main  
│ ├─java  
│ │ └─com  
│ │ └─mycom  
│ │ └─myapp  
│ │ ├─board  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ ├─bookmark  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ ├─common  
│ │ ├─config  
│ │ ├─house  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ ├─info  
│ │ │ └─dto  
│ │ ├─marketproxy  
│ │ │ └─controller  
│ │ ├─proposal  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ ├─region  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ ├─user  
│ │ │ ├─controller  
│ │ │ ├─dao  
│ │ │ ├─dto  
│ │ │ └─service  
│ │ └─util  
│ │ └─market  
│ └─resources  
│ ├─config  
│ └─mapper  
└─test  
 └─java  
 └─com  
 └─mycom  
 └─myapp

### Frontend

├─api  
├─assets  
│ ├─img  
│ │ └─apart  
│ ├─profileImage  
│ └─video  
├─bus  
├─common  
├─components  
│ ├─board  
│ ├─common  
│ └─error  
├─pages  
├─plugins  
├─routes  
├─store  
│ └─modules  
├─styles  
│ └─generic  
└─util

## 구현 기능

1. 회원관리
   - 로그인
   - 회원가입
   - 로그아웃
   - 회원탈퇴
   - 회원수정
2. 거래정보
   - 지도 API 사용
   - 거래 정보 리스트
   - 지도 정보 연동
   - 매물 상세조회
3. 부동산 뉴스 API
   - 뉴스 리스트
4. 공지사항
   - 공지사항 리스트
   - 공지사항 상세조회
   - 공지사항 수정
   - 공지사항 삭제
5. 문의하기
   - 매물 문의신청
   - 매물 조회
6. 관심 매물
   - 관심매물 등록
   - 관심매물 관리

## 실행 방법

### DB

```
MySql Dump파일 import
```

### Backend

```Java
SpringbootRestAPIProject 서버 실행
```

### Frondend

```sh
npm i
npm run serve -- --port 5500
```

## 추가 정보

프로젝트에 대해 더 알고 싶으시면 Notion링크를 방문해 주세요 !   
[https://nebulous-angle-7cd.notion.site/71e66dd219814385b1e53fb9136dacec]
