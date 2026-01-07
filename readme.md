# ⬢ Hexagonal Architecture Board API

> **Strict Hexagonal Architecture (Ports and Adapters)** implementation using **Kotlin** & **Spring Boot 4**.

이 프로젝트는 **순수 도메인 로직의 격리**와 **유연한 어댑터 구조**를 목표로 하는 헥사고날 아키텍처 기반의 게시판 API입니다. 외부 시스템(Web, Database)이 변경되어도 핵심 비즈니스 로직(Domain)은 영향받지 않도록 설계되었습니다.

## 🛠 Tech Stack

* **Language**: Kotlin (JDK 21)
* **Framework**: Spring Boot 4.0.1
* **Persistence**: Spring Data JPA (Hibernate)
* **Build Tool**: Gradle
* **Architecture**: Hexagonal Architecture (Ports and Adapters)

## 📂 Project Structure

패키지 구조는 기술적인 계층이 아닌 **아키텍처의 의도**를 명확히 드러내도록 구성되었습니다.

```text
demo.hexagonal.hexagonalback
├── 📂 adapter                 # [Infra] 외부 세계와 소통하는 어댑터
│   ├── 📂 in                  # Driving Adapter (요청을 받아들이는 곳)
│   │   └── 📂 web             # Web Controller, Web DTO
│   └── 📂 out                 # Driven Adapter (요청을 내보내는 곳)
│       └── 📂 persistence     # JPA Entity, Repository Impl, Mapper
│
├── 📂 application             # [App] 도메인과 어댑터를 연결하는 오케스트레이션
│   ├── 📂 port                # 인터페이스 (Port) 정의
│   │   ├── 📂 in              # UseCase Interface (Input Port)
│   │   └── 📂 out             # Repository Interface (Output Port)
│   └── 📂 service             # UseCase 구현체 (트랜잭션 관리, 흐름 제어)
│
└── 📂 domain                  # [Core] 외부 의존성이 전혀 없는 순수 비즈니스 로직
    ├── 📂 exception           # 도메인 비즈니스 예외 (BoardException)
    └── 📂 model               # 핵심 도메인 모델 (Pure Kotlin Class)
```

## 📐 Architecture Principles

이 프로젝트는 헥사고날 아키텍처의 핵심 원칙을 **절대적으로 준수**합니다.

### 1. 의존성 규칙 (Dependency Rule)
모든 의존성은 **바깥쪽(Adapter)에서 안쪽(Domain)** 으로만 향합니다.
* `Domain`은 `Application`, `Adapter`에 대해 전혀 알지 못합니다.
* `Application`은 `Adapter`에 대해 알지 못합니다 (Port 인터페이스를 통해서만 소통).
* **JPA Entity(@Entity)** 와 **Domain Model(Pure Class)** 은 철저히 분리되어 있으며, `Mapper`를 통해 변환됩니다.

### 2. 도메인 중심 설계 (Rich Domain Model)
* **Service**는 단순히 로직의 흐름(Orchestration)만 제어합니다.
* 실제 상태 변경과 비즈니스 규칙 검증은 **Domain Model** 내부의 메서드가 책임집니다.

### 3. 포트와 어댑터 (Ports and Adapters)
* **In-Port (UseCase)**: 클라이언트가 애플리케이션에 무엇을 요청할 수 있는지 정의합니다.
* **Out-Port (Repository Port)**: 애플리케이션이 데이터를 저장/조회하기 위해 무엇이 필요한지 정의합니다.

## 📝 API Specification

### 게시글 (Board)

| Method | URI | Description |
| :--- | :--- | :--- |
| `POST` | `/api/boards` | 게시글 생성 |
| `GET` | `/api/boards` | 전체 게시글 조회 |
| `GET` | `/api/boards/{id}` | 특정 게시글 단건 조회 |
| `PUT` | `/api/boards/{id}` | 게시글 수정 |
| `DELETE` | `/api/boards/{id}` | 게시글 삭제 |

## 🚀 Getting Started

### Prerequisites
* JDK 21+
* Gradle

### Run

```bash
# Clone Repository
git clone <repository-url>

# Build
./gradlew clean build

# Run
./gradlew bootRun
```

---

### 💡 Key Code Features

#### 1. Self-Validating Commands
Controller에서 넘어온 데이터는 UseCase로 진입하기 전, Command 객체 생성 시점에 **생성자 내부에서 유효성이 검증**됩니다. 이를 통해 애플리케이션 계층은 항상 유효한 데이터만 다룹니다.

#### 2. Isolation of Persistence
DB 테이블 구조(JPA Entity)가 변경되어도 비즈니스 로직(Domain Model)은 영향을 받지 않습니다. `BoardPersistenceAdapter`가 중간에서 `Mapper`를 이용해 두 객체 간의 변환을 담당합니다.