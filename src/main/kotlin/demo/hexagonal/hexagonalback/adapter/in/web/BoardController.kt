package demo.hexagonal.hexagonalback.adapter.`in`.web

import demo.hexagonal.hexagonalback.application.port.`in`.CreateBoardCommand
import demo.hexagonal.hexagonalback.application.port.`in`.CreateBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.DeleteBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.GetBoardUseCase
import demo.hexagonal.hexagonalback.application.port.`in`.UpdateBoardCommand
import demo.hexagonal.hexagonalback.application.port.`in`.UpdateBoardUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/boards")
class BoardController(
    // Service 구현체가 아닌, UseCase 인터페이스를 주입받습니다.
    private val createBoardUseCase: CreateBoardUseCase,
    private val getBoardUseCase: GetBoardUseCase,
    private val updateBoardUseCase: UpdateBoardUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase
) {

    @PostMapping
    fun createBoard(@RequestBody request: CreateBoardRequest): ResponseEntity<BoardResponse> {
        // 1. Request DTO -> UseCase Command 변환
        val command = CreateBoardCommand(
            title = request.title,
            content = request.content
        )

        // 2. UseCase 실행
        val createdBoard = createBoardUseCase.createBoard(command)

        // 3. Domain -> Response DTO 변환 및 반환
        val response = BoardResponse.from(createdBoard)
        return ResponseEntity.created(URI.create("/api/boards/${response.id}")).body(response)
    }

    @GetMapping("/{id}")
    fun getBoard(@PathVariable id: Long): ResponseEntity<BoardResponse> {
        val board = getBoardUseCase.getBoard(id)
        return ResponseEntity.ok(BoardResponse.from(board))
    }

    @GetMapping
    fun getAllBoards(): ResponseEntity<List<BoardResponse>> {
        val boards = getBoardUseCase.getAllBoards()
        // 리스트 내부의 각 요소를 변환
        val responses = boards.map { BoardResponse.from(it) }
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{id}")
    fun updateBoard(
        @PathVariable id: Long,
        @RequestBody request: UpdateBoardRequest
    ): ResponseEntity<BoardResponse> {
        val command = UpdateBoardCommand(
            id = id,
            title = request.title,
            content = request.content
        )

        val updatedBoard = updateBoardUseCase.updateBoard(command)
        return ResponseEntity.ok(BoardResponse.from(updatedBoard))
    }

    @DeleteMapping("/{id}")
    fun deleteBoard(@PathVariable id: Long): ResponseEntity<Void> {
        deleteBoardUseCase.deleteBoard(id)
        return ResponseEntity.noContent().build()
    }
}