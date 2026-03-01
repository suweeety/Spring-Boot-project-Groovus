# [Spring Boot Project] 
![groovus_main](https://github.com/suweeety/Spring-Boot-project-Groovus/assets/150898767/65d54b22-ff21-4ef4-ad5d-2850eb73560a)
## 🖥️ 프로젝트 소개
* GroovUs는 팀원 간의 원활한 소통과 효율적인 업무 관리를 지원하는 종합 프로젝트 협업 툴입니다. 공지사항, 업무 및 일정 관리부터 실시간 채팅, 화상회의, 공유 드라이브까지 협업에 필요한 핵심 기능들을 통합하여 제공합니다.<br>
주요기능[ 공지사항 / 프로젝트 관리 / 댓글관리 / 업무관리 / 쪽지함 / 캘린더 / 공유 드라이브 / 화상회의 및 채팅 ]
<br><br>


## 🕰️ 개발 기간
* 24.04.22일 - 24.05.13일 (약 3주)
<br><br>


### ⚙️ 개발 환경 및 주요 기술 스택
- **BE** : Java17, Gradle, JPA, Querydsl
- **FE** : JavaScript, jQuery, Thymeleaf
- **Framework** : Spring Boot(3.x), Spring Security, OAuth2 Client (소셜 로그인)
- **DB** : PostgreSQL
- **Features**: Spring WebSocket, SockJS, STOMP (실시간 통신), MapStruct/ModelMapper (데이터 매핑)
- **IDE** : IntelliJ
- **배포환경** : AWS EC2, Docker
- **VCS** : git&github
<br><br>

<img width="1731" height="861" alt="diagram-export-2026 -3 -1 -오후-10_48_12" src="https://github.com/user-attachments/assets/11f1cb2b-f978-4684-8588-fffed8484308" />
<br><br>


## 📌 주요 기능
* 프로젝트 및 업무 관리: 프로젝트 셋업(Admin 권한 제어), 업무/이슈 등록 및 상태 기반 진행 상황 공유
* 실시간 화상회의 및 채팅: 1:1 화상회의(오디오/화면공유 지원), 실시간 채팅(방 생성/삭제, 입장/퇴장 알림, 유저 리스트)
* 소통 및 피드백: 쪽지함(멤버 간 다이렉트 메시지), 업무 하단 댓글 및 대댓글 시스템
* 리소스 공유: 팀원 간 일정 공유가 가능한 공유 캘린더, 파일 관리를 위한 공유 드라이브
* 공지사항: 사내/팀 내 중요 소식 전달 기능
<br><br>


## 🧑‍🤝‍🧑 맴버구성(담당 역할)
* 정은 - 프로젝트, 댓글관리, 업무관리 , 쪽지함, 프로젝트 관리
* 수영 - 공지사항, 화상회의 및 채팅
* 근휘 - 캘린더(일정관리)
* 영주 - 공유 드라이브
<br><br>


## GroovUs 웹사이트 <a href="https://mbc-webcloud.iptime.org:8007/" target=_blank >클릭!</a> 시 홈페이지로 이동합니다.

### Test 계정 로그인
ID : user / PW : 1111
<br><br>


## 🏛 아키텍처 패턴 (Architecture Pattern)
이 프로젝트는 전통적인 **Spring MVC** 패턴과 관심사 분리를 위한 **계층형 아키텍처 (Layered Architecture)** 를 매우 충실하게 따르고 있습니다.
* **Presentation Layer (`controller`)**: HTTP 요청 처리 및 View/REST API 응답 반환
* **Business Layer (`service`)**: 핵심 비즈니스 로직 및 트랜잭션(`@Transactional`) 관리
* **Data Access Layer (`repository`)**: Spring Data JPA 인터페이스와 QueryDSL을 이용한 데이터베이스 통신
* **Domain/Model Layer (`entity`, `dto`)**: DB 매핑 객체(Entity)와 계층 간 데이터 교환 객체(DTO) 분리
* **Mapping Layer (`mapper`)**: Entity ↔ DTO 간의 데이터 변환 (MapStruct/ModelMapper 활용)
* **Infrastructure/Configuration (`config`, `security`, `exception`)**: 웹소켓, 보안, 전역 예외 처리 등 환경 설정
<br><br>


## 💡 백엔드 핵심 복잡도 및 문제 해결 역량
#### 1. 웹소켓 및 STOMP 기반 실시간 양방향 통신 제어
* **구현:** HTTP의 무상태(Stateless)를 벗어나 STOMP 프로토콜을 활용해 채팅방 브로드캐스트 구현.
* **기술적 난이도:** 인메모리 기반(`ChatRoomMap`) 상태 관리 및 동시성 문제 제어. 이벤트 리스너(`SessionDisconnectEvent`)를 활용한 세션 라이프사이클 관리.
#### 2. 다중 엔티티 연관관계 및 상태 이력(History) 정합성 관리
* **구현:** 업무(Task) 상태 변경 시 변경 이력(`StatusHistory`) 데이터를 함께 저장하여 이력 추적.
* **기술적 난이도:** 다단계 계층형 데이터(댓글, 대댓글 등) 조작 및 트랜잭션 관리를 통한 데이터 정합성 보장. N+1 문제를 고려한 연관관계 설계.
#### 3. QueryDSL을 활용한 동적 다중 조건 검색 및 페이징 처리
* **구현:** 사용자의 다양한 검색 조건(제목, 내용 등)에 맞춰 동적으로 WHERE 및 JOIN 절을 조합.
* **기술적 난이도:** 순수 JPA만으로 해결하기 힘든 복잡한 동적 쿼리를 `BooleanBuilder`를 통해 Type-Safe하게 구현. Entity 리스트를 최적화하여 DTO로 변환하는 스트림 데이터 핸들링.
<br><br>


## 🗄 데이터베이스 설계 특징
1. **공통 속성 추상화 (JPA Auditing):** `@MappedSuperclass`를 활용하여 생성일(`regDate`), 수정일(`modDate`)을 BaseEntity로 추상화하여 로깅 자동화.
2. **지연 로딩(Lazy Loading) 중심 설계:** 모든 연관관계 매핑(`@ManyToOne`, `@ManyToMany` 등)에 `FetchType.LAZY`를 적용하여 불필요한 즉시 로딩(Eager)으로 인한 N+1 성능 병목을 사전 차단.
3. **효율적인 동적 SQL 처리:** QueryDSL을 활용해 컴파일 타임에 오류를 잡을 수 있는 안전한 동적 페이징 쿼리 생성.
4. **논리적 삭제 (Soft Delete):** 데이터 삭제 시 물리적 `DELETE` 대신 `del = true` 업데이트 방식을 채택하고, 검색 시 `isFalse()` 조건을 필수화하여 데이터 이력 보존.
