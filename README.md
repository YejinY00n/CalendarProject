 ## 📢 1. 프로젝트 소개
> ✔️ 기본적인 **일정 관리 기능**을 제공하는 프로젝트

<br>

#### 📂 Github Repository
<a href="https://github.com/YejinY00n/ScheduleProject" target="_blank" style="text-decoration: none;">🔗 ScheduleProject - GitHub</a>

<br>

### 주요 기능
📅 **일정**

- **일정 생성**: 작성자와 비밀번호를 함께 입력하여 새로운 할 일을 저장
- **일정 조회**: 개별 일정 조회 및 기간/작성자 기준으로 모든 일정 목록을 조회
- **일정 업데이트**: 올바른 비밀번호 입력 시 할 일과 작성자 정보를 수정
- **일정 삭제**: 올바른 비밀번호 입력 시 해당 할 일을 삭제
<br><br><br>

## 📜 2. 기술 문서
### 기술 스택  

| 기술       | 스펙                          |
|-----------|-----------------------------|
| **언어**    | `Java 17`                   |
| **프레임워크** | `Spring Boot`                |
| **DB**     | `MySQL`                      |
| **DB 연동**  | `JDBC` (Java Database Connectivity) |
| **테스트 도구** | `Postman` (API 테스트)       |

<br><br>

### API 스펙
- API 문서는 Postman을 활용하여 문서화하였다.
- 자세한 API 명세는 아래 링크에서 확인할 수 있다.

<a href="https://documenter.getpostman.com/view/35016291/2sAYkKJy7J" target="_blank">🔗 📄 Schedule Project API 문서</a>
<br><br>

### ERD 다이어그램
![](https://velog.velcdn.com/images/yoon17710/post/be0c2e33-1407-4dfc-9827-86c7ae07e749/image.png)

#### 📅할 일 (Event)
- **ID**: 고유 식별자 (Primary Key)
- 추후 ver.2 업데이트에서 **사용자**와 **할 일** 엔티티를 분리할 계획이다.
<br><br>

## 🛠️ 3. 트러블 슈팅
<a href="https://velog.io/@yoon17710/TIL-%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC-%EC%BA%98%EB%A6%B0%EB%8D%94-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8" target="_blank">🔗 프로젝트 트러블 슈팅 velog</a>

<br><br>
