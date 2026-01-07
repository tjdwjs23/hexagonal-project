package demo.hexagonal.hexagonalback.adapter.`in`.web

import demo.hexagonal.hexagonalback.domain.model.Board
import java.time.LocalDateTime

// 요청 DTO: 게시글 생성
data class CreateBoardRequest(
    val title: String,
    val content: String
)

// 요청 DTO: 게시글 수정
data class UpdateBoardRequest(
    val title: String,
    val content: String
)

// 응답 DTO: 사용자에게 보여줄 데이터
data class BoardResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
) {
    // 편의 메서드: Domain -> Web Response 변환
    // (별도 Mapper 클래스를 만들어도 되지만, 간단한 경우 동반 객체나 생성자로 처리해도 됩니다)
    companion object {
        fun from(board: Board): BoardResponse {
            return BoardResponse(
                id = board.id!!, // 저장된 후라면 id는 반드시 있음
                title = board.title,
                content = board.content,
                createdAt = board.createdAt
            )
        }
    }
}