package demo.hexagonal.hexagonalback.domain.exception

class BoardNotFoundException(id: Long) : RuntimeException("Board not found with id: $id")