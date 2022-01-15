Green-Challenge!
=============
친환경 알고리즘 및 챌린지 프로젝트 입니다.<br>

## 프로젝트의 전체적인 구조
![Green-Challenge 서버 구조도](대충 이미지 경로)
- github action을 이용하여 CI/CD를 진행합니다.
- 서버는 AWS EC2를 사용하고 있습니다.<br>
- 빌드된 파일을 S3에 업로드합니다.
- 업로드된 .jar파일을 codeDeploy가 ec2 서버에 자동 업로드합니다.
- 구동중인 서버 : Nginx, MariaDB Server
- public IP는 Main Server인 ec2에만 할당되어 있습니다.


## 프로젝트의 주요 관심사
- 지속적인 성능 개선
- 스파게티 코드나 중복된 코드에 대한 리팩토링
  <br><br>
### 코드 컨벤션
- Google code Style을 준수
- CheckStyle-IDEA 플러그인을 적용하여 코드 컨벤션을 유지
- 링크 https://google.github.io/styleguide/javaguide.html
  <br><br>

### 브랜치 관리 전략
Git Flow를 사용하여 브랜치를 관리합니다.<br>
모든 브랜치는 Pull Request에 리뷰를 진행한 후 merge를 진행합니다.<br>
메인 브렌치인 Develop에는 아직 많은 내용이 merge되지 않았습니다. 현재 개발 진행사항을 확인하고 싶다면 PR를 확인해주세요.<br><br>
- Main : 배포시 사용합니다. 아직 배포단계에 이르지 않아 Master 브랜치에 내용이 없습니다.
- Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
- Feature : 기능 개발을 진행할 때 사용합니다.
- Release : 배포를 준비할 때 사용합니다.
- Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.
  <br><br>
  <b>브랜치 관리 전략 참고 문헌</b><br>
- 우아한 형제들 기술 블로그(http://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)
- Bitbucket Gitflow Workflow(https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)

### 테스트
- JUnit를 활용하여 고립된 테스트 코드를 작성
- Github action을 이용하여 테스트 자동화
- 협업하는 동료의 소스코드에 서로 테스트코드를 작성하여 서로의 소스코드를 알 수 있도록 하고 있습니다.
  <br><br>

### 성능 테스트

## 사용 기술 및 환경
Spring boot, Maven, JPA, AWS, MariaDB, Github action, S3, CodeDeploy, Java11, Nginx
<br>

## CI
Github action을 사용합니다.<br>
develop 브랜치에 push 및 PR시 마다 자동 Build 및 Test를 적용합니다<br>

## CD
AWS S3, CodeDeploy를 이용하여 ec2 서버에 자동 배포합니다.<br>

##Database
- MariaDB<br><br>

## 화면 설계


## 프로젝트 DB ERD
