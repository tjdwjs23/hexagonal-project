package demo.hexagonal.hexagonalback.domain.model

import java.time.LocalDateTime

// JPA 어노테이션 없음!
data class Board(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    // 도메인 내부에서 비즈니스 규칙(유효성 검증 등)을 처리하고 변경된 객체를 반환
    fun update(title: String, content: String): Board {
        if (title.isBlank()) throw IllegalArgumentException("제목은 비어있을 수 없습니다.") // 도메인 규칙
        return this.copy(title = title, content = content)
    }
}