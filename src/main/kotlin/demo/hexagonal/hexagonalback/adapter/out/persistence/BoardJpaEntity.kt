// adapter/out/persistence/BoardJpaEntity.kt
package demo.hexagonal.hexagonalback.adapter.out.persistence

import jakarta.persistence.*

// 실제 DB 테이블과 매핑되는 놈은 여기 있습니다.
@Entity
@Table(name = "board")
class BoardJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String
)