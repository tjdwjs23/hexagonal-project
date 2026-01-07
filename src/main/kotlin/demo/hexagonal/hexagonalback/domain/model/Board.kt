package demo.hexagonal.hexagonalback.domain.model

import java.time.LocalDateTime

// JPA 어노테이션 없음!
data class Board(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    // 도메인 로직 예시
    fun updateContent(newContent: String): Board {
        return this.copy(content = newContent)
    }
}