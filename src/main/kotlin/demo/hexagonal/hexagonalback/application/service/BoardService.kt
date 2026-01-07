package demo.hexagonal.hexagonalback.application.service

import demo.hexagonal.hexagonalback.application.port.`in`.CreateBoardCommand
import demo.hexagonal.hexagonalback.application.port.`in`.CreateBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.DeleteBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.GetBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.UpdateBoardCommand
import demo.hexagonal.hexagonalback.application.port.`in`.UpdateBoardUseCase
import demo.hexagonal.hexagonalback.application.port.out.BoardRepositoryPort
import demo.hexagonal.hexagonalback.domain.exception.BoardNotFoundException
import demo.hexagonal.hexagonalback.domain.model.Board
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional // 트랜잭션 관리는 서비스 계층에서 합니다.
class BoardService(
    private val boardRepositoryPort: BoardRepositoryPort
) : CreateBoardUseCase, GetBoardUseCase, UpdateBoardUseCase, DeleteBoardUseCase {

    override fun createBoard(command: CreateBoardCommand): Board {
        // 도메인 객체 생성
        val newBoard = Board(
            title = command.title,
            content = command.content
        )
        // 포트를 통해 저장 (ID가 부여된 객체가 반환됨)
        return boardRepositoryPort.save(newBoard)
    }

    @Transactional(readOnly = true)
    override fun getBoard(id: Long): Board {
        return boardRepositoryPort.findById(id)
            ?: throw BoardNotFoundException(id)
    }

    @Transactional(readOnly = true)
    override fun getAllBoards(): List<Board> {
        return boardRepositoryPort.findAll()
    }

    override fun updateBoard(command: UpdateBoardCommand): Board {
        // 1. 기존 게시글 조회
        val existingBoard = boardRepositoryPort.findById(command.id)
            ?: throw BoardNotFoundException(command.id)

        // 2. 도메인 로직 실행 (내용 수정)
        // Board가 data class(immutable)라면 copy로 새 객체를 만듭니다.
        // 만약 도메인 안에 `update()` 메서드를 만들어뒀다면 그걸 쓰세요.
        val updatedBoard = existingBoard.copy(
            title = command.title,
            content = command.content
        )

        // 3. 변경된 객체 저장
        return boardRepositoryPort.save(updatedBoard)
    }

    override fun deleteBoard(id: Long) {
        boardRepositoryPort.deleteById(id)
    }
}