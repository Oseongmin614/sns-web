# SNS_Web

![GitHub License](https://img.shields.io/badge/license-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.x-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-11-orange.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![AWS S3](https://img.shields.io/badge/AWS-S3-orange.svg)

## 📌 프로젝트 소개

이 프로젝트는 Spring기반의 소셜 네트워크 서비스(SNS) 백엔드 시스템입니다. 사용자 간 콘텐츠 공유 및 상호작용을 중심으로, 안정적인 백엔드 시스템 구축과 효율적인 데이터 처리에 중점을 두었습니다.

개인 프로젝트로 진행하며 백엔드 아키텍처 설계부터 RESTful API 구현, 데이터베이스 연동, 클라우드 스토리지 통합까지 웹 애플리케이션 개발의 전반적인 과정을 경험했습니다.
<img width="1150" height="802" alt="image" src="https://github.com/user-attachments/assets/bc65c33f-07df-4cbf-86bb-fd2c70b7cc4b" />

## 🚀 주요 기능

### 사용자 및 인증
-   **회원 가입 및 로그인**: 사용자 인증을 통한 서비스 접근.
-   **프로필 관리**: 사용자 프로필 조회 및 수정.
-   **팔로우/언팔로우**: 다른 사용자와의 관계 설정.

### 게시물 및 콘텐츠
-   **게시물 업로드**: 텍스트와 이미지 첨부를 통한 게시물 생성.
-   **게시물 조회**: 피드 (팔로우한 계정의 게시물), 전체 탐색 피드 제공.
-   **게시물 상호작용**: 좋아요, 댓글 작성 및 관리.
-   **게시물 수정/삭제**: 본인 게시물에 대한 CRUD 작업.

### 효율적인 데이터 처리
-   **무한 스크롤**: 피드 조회 시 효율적인 데이터 로딩을 위한 무한 스크롤 기능 구현.
-   **미디어 파일 관리**: AWS S3를 활용한 이미지 파일 저장 및 서빙.

## ⚙️ 기술 스택

### 백엔드
-   **언어**: Java 11
-   **프레임워크**: Spring Boot 2.x
-   **데이터베이스**: MySQL 8.0
-   **ORM/쿼리**: Spring Data JPA, Querydsl
-   **빌드 도구**: Gradle

### 클라우드/인프라
-   **객체 스토리지**: AWS S3

### 버전 관리
-   Git, GitHub

## 📝 개발 내용 및 학습 경험

-   **견고한 백엔드 아키텍처 설계**: MVC 패턴 기반으로 RESTful API를 설계하고 구현하여 유지보수성과 확장성을 고려한 시스템 구축.
-   **관계형 데이터베이스 연동 및 최적화**: MySQL 데이터베이스와 Spring Data JPA, Querydsl을 활용하여 엔티티 간의 복잡한 관계를 효율적으로 매핑하고 데이터 접근 로직을 최적화.
-   **클라우드 스토리지 통합**: 대용량 미디어 파일(게시물 이미지, 프로필 사진 등)의 안정적인 저장 및 관리를 위해 AWS S3를 백엔드에 통합하는 방법을 학습하고 적용.
-   **사용자 경험 개선**: 효율적인 데이터 로딩을 위한 `Pageable` 객체를 활용한 무한 스크롤 기능을 구현하여 사용자 피드 로딩 속도 및 경험 향상.
-   **주요 기술적 도전 및 해결**:
    -   **복잡한 엔티티 관계 모델링**: 팔로우/팔로잉, 게시물/댓글/좋아요 등 다수의 엔티티 간 복잡한 관계를 JDBC가 아닌 JPA 방식으로 효과적으로 매핑하고 관리하는 데 집중.
    -   **파일 업로드 처리**: 대용량 이미지 파일의 안정적인 저장 및 서빙을 위해 AWS S3와의 연동 과정에서 발생하는 권한 및 설정 문제를 해결하고, 실제 운영 환경을 고려한 파일 관리 방안 마련.

## 💡 프로젝트 실행 방법

1.  **프로젝트 클론**:
    ```bash
    git clone https://github.com/Oseongmin614/sns-web.git
    ```
2.  **의존성 설치**:
    -   Gradle을 사용하여 프로젝트 의존성을 자동으로 설치합니다. ( `./gradlew build` )
3.  **환경 설정**:
    -   `application.yml` 또는 `application.properties` 파일에 데이터베이스(`MySQL`) 연결 정보와 `AWS S3` 인증 정보를 설정합니다.
    -   데이터베이스 스키마 생성 (혹은 JPA `ddl-auto: update` 활용).
4.  **애플리케이션 실행**:
    -   Spring Boot 애플리케이션을 실행합니다. (IDE에서 실행 또는 `java -jar build/libs/*.jar` )
5.  **API 테스트**:
    -   Postman 또는 curl을 사용하여 API 엔드포인트를 테스트합니다. (예: `GET /api/posts`, `POST /api/users/signup` )
