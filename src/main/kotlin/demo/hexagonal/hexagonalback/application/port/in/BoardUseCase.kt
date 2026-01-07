package demo.hexagonal.hexagonalback.application.port.`in`

import demo.hexagonal.hexagonalback.domain.model.Board

// 1. 게시글 생성 유즈케이스
interface CreateBoardUseCase {
    fun createBoard(command: CreateBoardCommand): Board
}

data class CreateBoardCommand(
    val title: String,
    val content: String
)

// 2. 게시글 조회 유즈케이스
interface GetBoardUseCase {
    fun getBoard(id: Long): Board
    fun getAllBoards(): List<Board>
}

// 3. 게시글 수정 유즈케이스
interface UpdateBoardUseCase {
    fun updateBoard(command: UpdateBoardCommand): Board
}

data class UpdateBoardCommand(
    val id: Long,
    val title: String,
    val content: String
)

// 4. 게시글 삭제 유즈케이스
interface DeleteBoardUseCase {
    fun deleteBoard(id: Long)
}