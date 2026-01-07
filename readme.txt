src/main/kotlin/demo/hexagonal/hexagonalback
├── domain                         # [가장 안쪽] 순수한 비즈니스 로직 (Spring, JPA 의존성 X)
│   ├── model                      # 도메인 객체 (Entity, VO)
│   └── exception                  # 도메인 관련 예외
│
├── application                    # [중간] 도메인과 어댑터를 연결하는 오케스트레이션
│   ├── port
│   │   ├── in                     # (Input Port) 외부에서 들어오는 요청 정의 (UseCase)
│   │   └── out                    # (Output Port) 외부로 나가는 요청 정의 (Interface)
│   └── service                    # (UseCase 구현체) 비즈니스 흐름 제어
│
└── adapter                        # [가장 바깥] 실제 기술 구현체
    ├── in                         # (Driving Adapter) 입력을 받는 곳
    │   └── web                    # RestController, Request/Response DTO
    └── out                        # (Driven Adapter) 출력을 내보내는 곳
        └── persistence            # JPA Entity, Repository, Mapper