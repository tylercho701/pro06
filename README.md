
## 프로젝트명 
***
    세차용품 프로젝트(CHEMICALGUYS)
####
    CHEMICALGUYS는 미국 시장에서 활동하는 세차 용품 브랜드로, 전 세계 차량 애호가들에게 
    사랑 받고 있습니다. 국내 시장 입점을 위한 쇼핑몰을 오픈합니다.

#
## 팀원소개
***
    조교행(PM)
#
## 프로젝트 기간
***
    2023.08. 17. ~ 2023.08. 23 (7 days)
#
## 프로젝트 목적
***
    해당 브랜드의 세차 용품을 구매할 수 있습니다.
#
## 프로젝트 설명
***
    미국, 그 외 해외 국가에서는 해당 브랜드가 해당 국가에서 입점했기 때문에 쉽게 구매가 가능하나,
    국내 차량 애호가들은 직구 혹은 극소수의 온오프라인 매장을 통해 구매가 가능했습니다.

    케미컬 가이즈의 세차 용품을 보다 쉽게 구매할 수 있도록 전용 쇼핑몰을 오픈하여
    세차 용품의 빠르고 저렴한 가격에 구매 가능합니다.

####

    1) 로그인한 회원만 모든 게시글에 열람 및 글쓰기 가능. 비회원은 열람만 가능.

    2) <세차용품>은 전체 리스트로 제공되며, 검색 기능을 통해 특정 상품을 찾아볼 수 있도록 구현.

    3) <세차용품>은 관리자 권한으로 상품 등록 가능.

    4) <QNA> 게시판을 통해 운영자 뿐만 아니라 각 회원 간에 정보 공유 가능.

    5) <공지사항>을 통해 운영 간 고객에게 필요한 정보들을 전달 가능.

#
## 일정계획서(WBS)
***
![WBS](/prep/WBS_01.png)<br>

#
## DB 설계
***
![DBMS_01](/prep/table_structure_01.png)<br>
![DBMS_02](/prep/table_structure_02.png)<br>
![DBMS_03](/prep/table_structure_03.png)<br>
![DBMS_04](/prep/table_structure_04.png)<br>
![DBMS_05](/prep/table_structure_05.png)<br>

#
## ERD
***
![ERD](/prep/ERD_01.png)<br>

#
## 기술스택
***
**Frontend**

    HTML5, CSS3, JavaScript, JQuery(3.5.1), Popper(1.16.1), bootstrap(4.5.3)

**IDE**

    IntelliJ IDEA UE(2023.1.3)

**Backend-Language**

    Java SDK 17

**Backend-Library**

    Spring Boot (3.1.2), Spring Security, Spring Data JPA, Lombok, Thymeleaf, Mysql-Connector(8.0.33), Jakarta(5.0.0)

**Backend-Dependency Manager**

    Maven

**DBMS**

    MySQL

**WAS**

    Embeded Spring Boot

#
## 주요기능
***
#
### 공통
    각 게시글(상품 목록, 상품 상세, 공지사항, QnA 등)은 비회원도 열람이 가능하나,
    관리자 또는 작성자가 로그인 한 조건에서만 수정/삭제 기능 사용하도록 구현되었습니다.
    구매 및 장바구니는 회원이 로그인 한 조건에서만 사용 가능하며, 비회원은 사용 불가합니다.
    각 상품과 공지 게시글에서는 MultipartFile 인터페이스를 사용하여 DB에 경로를, 
    로컬에서는 설정된 경로에 파일이 저장되어 페이지에서 로드되도록 구현하였습니다.
    페이징 처리는 Pageable 클래스를 이용하여 구현하였습니다.
    유효성 검증 기능은 Jakarta의 Valildation 클래스를 사용하여 구현하였습니다.
#
### 로그인
    Spring Security 라이브러리로 로그인 유저 정보를 세션에 저장합니다.

    일반유저(ROLE_USER)
    Header의 우측 <로그인>, <회원가입>은 로그인 후 <장바구니>, <로그아웃> 으로 변경됩니다.
    
    관리자(ROLE_ADMIN)
    관리자(Admin) 로그인 시 좌측 상단에서 <관리 페이지>가 활성화되며, 상품 등록 및 관리 기능을 사용할 수 있습니다.

#
### 상품 리스트
    상품 전체 리스트를 home(index)에서 확인 가능하며, header 중앙에서 검색 기능을 통해 특정 상품 검색이 가능합니다.
    상품 상세 페이지에서 썸네일 외 디테일 사진 확인 가능하며, 장바구니 담기 및 구매 가능합니다.

    장바구니에 상품을 담거나 삭제할 수 있으며, 장바구니에서 상품 구매 가능합니다.
    장바구니에서는 구매를 원하는 상품 수량을 조정할 수 있습니다.
    구매를 원하는 상품을 목록 좌측에서 체크 박스 체크 후 구매하기 진행 가능합니다.

    구매하기를 통해 상품을 구매할 수 있으며, 구매 시 등록된 재고 수량에서 차감됩니다.

#
### 공지사항
    상품 구매 관련한 운영자의 공지 사항을 등록할 수 있으며, 회원/비회원 모두 열람 가능합니다.
    
    공지사항 등록 및 수정, 삭제는 운영자의 권한으로만 접근/수행 가능합니다.

    공지사항에는 최대 5개의 이미지 파일을 등록할 수 있습니다.

#
### QnA    
    질문(Question) 등록이 가능하며, 질문 후 운영자나 타 고객에게 답변(Answer)을 받을 수 있습니다.
    본인의 질문에 본인이 답변을 등록할 수 있으며, 답변에는 댓글(Comment)과 추천(vote)을 받을 수 있습니다.

    각 글은 작성자 본인과 운영자의 권한으로 수정/삭제 가능합니다.

    추천 시 추천인 만큼의 수가 추천 버튼 옆에 게시됩니다.
    답변의 수는 QnA 게시글 목록에서 게시글의 제목 옆에 표기됩니다.

#
## 최종 산출물
***
### 최종발표 PPT
[ChemicalguysMallProject.pptx](/prep/ChemicalguysProject.pptx)<br>
![슬라이드1](/prep/ChemicalguysProject/slide_01.png)<br>
![슬라이드2](/prep/ChemicalguysProject/slide_02.png)<br>
![슬라이드3](/prep/ChemicalguysProject/slide_03.png)<br>
![슬라이드4](/prep/ChemicalguysProject/slide_04.png)<br>
![슬라이드5](/prep/ChemicalguysProject/slide_05.png)<br>
![슬라이드6](/prep/ChemicalguysProject/slide_06.png)<br>


