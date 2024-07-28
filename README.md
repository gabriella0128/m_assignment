[구현 범위]
 - 브랜드 CRUD 구현
 - 상품 CRUD 구현
 - 카테고리별 최저가, 단일 브랜드 코디 최저가, 카테고리 검색(최저가, 최고가) 조회 API 구현
 - 백엔드 프로젝트 구동 시 resources 폴더 내 sql 문이 자동 수행되어 기존 데이터 삭제 후 과제에서 제공한 기본 데이터가 새롭게 다시 셋팅되도록 구현
   
[프로젝트 실행 방법]

Git repository 에서 프로젝트 clone (git clone https://github.com/gabriella0128/m_assignment.git)

    1. Backend Project 실행
        1. m_assignment 프로젝트 내에서 m_backend 로 이동 (cd m_backend)
        2. ./gradlew build 실행 (Permission Denied 시 chmod +x gradlew 살행)
        3. ./gradlew bootRun 실행
        4. test 구동 시 ./gradlew test 실행 후 m_assignment/m_backend/build/reports/tests/test/index.html 에서 test 결과 확인 가능
    
    2. Frontend Project 실행
        1. m_assignment 프로젝트 내에서 m_frontend 로 이동 (cd m_frontend)
        2. npm install 실행 (node 가 기본 설치되어 있어야 함)
        3. npm start 실행
        4. http://localhost:3000 에서 프론트엔드 페이지 확인
       
[기타 추가 정보]
- 백엔드(m_backend) 환경설정 
  - Spring boot version 3.3.2
  - Java version 18
  - JPA 기반 ORM
- 프론트엔드(m_frontend) 환경설정 
  - react version 18.3.1

[특이사항]
1. 구현 1에 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 의 경우, 카테고리 별로 최저가격 상품이 두 개 이상일 떄 복수 개의 브랜드가 결과값에 해당될 수 있음. 이 경우 최신 등록된 브랜드의 최저가 상품을 반환하도록 설정함.
