package demo.hexagonal.hexagonalback.adapter.out.persistence

import demo.hexagonal.hexagonalback.domain.model.Board
import org.springframework.stereotype.Component

@Component
class BoardMapper {

    // 도메인 -> 엔티티 (저장할 때)
    fun toEntity(domain: Board): BoardJpaEntity {
        return BoardJpaEntity(
            id = domain.id,
            title = domain.title,
            content = domain.content
        )
    }

    // 엔티티 -> 도메인 (조회할 때)
    fun toDomain(entity: BoardJpaEntity): Board {
        return Board(
            id = entity.id,
            title = entity.title,
            content = entity.content
        )
    }
}