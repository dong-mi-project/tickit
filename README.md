# tickit


## 개발 환경 관리

- nodeJS: `24.16.0`
- pnpm: `11.5.2`
- jdk: `25` Azul(Zulu)


## 개발 환경 설정


### Windows


- [Node Version Manager](https://github.com/coreybutler/nvm-windows/releases/latest/download/nvm-setup.exe) 설치

- 작업표시줄 Winodws 로고에서 우클릭 -> 터미널(관리자) 실행 후 아래 명령어 입력
    ```Powershell
    Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
    ```
- Node js 설치
    ```Powershell
    nvm install 24.16.0
    ```
    ```Powershell
    nvm use 24.16.0
    ```
- pnpm 설치
    ```Powershell
    choco install pnpm
    ```
- JDK 설치
    ```Powershell
    choco install zulu25
    ```

### MacOS

- Node Version Manager 설치
    ```zsh
    curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash
    ```
    ```zsh
    source ~/.zshrc
    ```
- Node js 설치
    ```Powershell
    nvm install 24.16.0
    ```
    ```Powershell
    nvm use 24.16.0
    ```
- JDK 설치
    ```shell
    brew install --cask zulu@25
    ```
    - Homebrew없는 경우
        ```shell
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
        ```


## 프로젝트 열기

- VSCode로 [tickit.code-workspace](tickit.code-workspace) 작업 영역 열기
- 권장 vscode확장 프로그램 설치
    - 프로젝트 파일 열면 우측 아래 알림에 표시되는 권장 vscode확장 설치
    - 또는 좌측 확장 프로그램 메뉴 `Ctrl + Shift + x`에서 `@recommended` 검색 후 모두 설치
    - 현재 [Debugger for Java](vscode:extension/vscjava.vscode-java-debug)확장에 오류가 있어 디버깅이 정상적으로 작동하지 않음
        - 해당 확장 우클릭 -> `특정 버전 설치` -> `0.58.5`선택

## 디버깅

- VSCode 우측 디버그 메뉴를 통하여 디버깅 및 실행
- 디버깅 옵션
    - Next.js Debug(client)
    - Spring boot Debug(server)
    - Full Stack Debug(Compound, 위 두 디버그를 동시 실행)


### 디버그 실행 정보
- [client/.vscode/launch.json](client/.vscode/launch.json)
- [server/.vscode/launch.json](server/.vscode/launch.json)


## [데이터베이스 구성](schema/README.md)
> 해당 문서 참고


## Server Project 초기 설정
- `.env`파일 생성 및 아래 항목 값 입력
    - `SPRING_DATASOURCE_USERNAME`
    - `SPRING_DATASOURCE_PASSWORD`
- JDK 버전 오류로 프로젝트가 열리지 않을 경우
    - `Ctrl(Cmd) + Shift + p`로 vscode명령 실행창 띄우기
    - `Java: Configure Java Runtime`에서 jdk 경로 지정


## [Server 프로젝트 구성](server/README.md)
> 해당 문서 참고
