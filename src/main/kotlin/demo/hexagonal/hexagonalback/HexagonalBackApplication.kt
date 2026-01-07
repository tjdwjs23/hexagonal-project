package demo.hexagonal.hexagonalback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HexagonalBackApplication

fun main(args: Array<String>) {
    runApplication<HexagonalBackApplication>(*args)
}
