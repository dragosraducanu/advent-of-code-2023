import java.io.File
import kotlin.math.max
import kotlin.math.min

//https://adventofcode.com/2023/day/3
class GearRatios {
    private val gearMap = mutableMapOf<Pair<Int, Int>, List<Int>>()
    private lateinit var input: List<String>

    fun solve() {
        input = File("inputs/day3_gear_ratios").readLines()

        var sum = 0
        for (i in input.indices) {
            var j = 0
            var num = ""
            while (j < input[i].length) {
                if (input[i][j].isDigit()) {
                    num += input[i][j]
                } else if (num.isNotBlank()) {
                    if (check(i, j, num)) {
                        sum += num.toInt()
                    }
                    num = ""
                }
                j++
            }
            if (num.isNotBlank()) {
                if (check(i, j, num)) {
                    sum += num.toInt()
                }
            }
        }

        // Part 2
        val sum2 = gearMap.values.filter {
            it.size == 2
        }.sumOf {
            it[0] * it[1]
        }

        println("Part 1: $sum") // 520019
        println("Part 2: $sum2") // 75519888
    }

    private fun check(i: Int, j: Int, num: String): Boolean {
        val startCheck = max(0, j - num.length - 1)

        var isPart = false

        if (startCheck >= 0) {
            isPart = isPart || input[i][startCheck].isSymbol()
            addToGears(i, startCheck, num.toInt())
        }

        if (j < input[i].length) {
            isPart = isPart || input[i][j].isSymbol()
            addToGears(i, j, num.toInt())
        }

        if (i > 0) {
            for(k in startCheck..<min(j + 1, input[i - 1].length) ) {
                isPart = isPart || input[i - 1][k].isSymbol()
                addToGears(i - 1, k, num.toInt())
            }
        }

        if (i < input.size - 1) {
            for (k in startCheck..<min(j + 1, input[i + 1].length) ) {
                isPart = isPart || input[i + 1][k].isSymbol()
                addToGears(i + 1, k, num.toInt())
            }
        }

        return isPart
    }

    private fun addToGears(x: Int, y: Int, n: Int) {
        if (input[x][y] == '*') {
            gearMap[Pair(x, y)] = listOf(n) + gearMap.getOrDefault(Pair(x, y), emptyList())
        }
    }

    private fun Char.isSymbol() = !this.isDigit() && this != '.'
}