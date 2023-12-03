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

        val toCheck = mutableListOf<Pair<Int, Int>>()

        if (startCheck >= 0) {
            toCheck.add(Pair(i, startCheck))
        }

        if (j < input[i].length) {
            toCheck.add(Pair(i, j))
        }

        if (i > 0) {
            toCheck.addAll(
                IntRange(startCheck, min(j + 1, input[i - 1].length) - 1).map {
                    Pair(i - 1, it)
                }
            )
        }

        if (i < input.size - 1) {
            toCheck.addAll(
                IntRange(startCheck, min(j + 1, input[i + 1].length) - 1).map {
                    Pair(i + 1, it)
                }
            )
        }

        toCheck.filter {
            input[it.first][it.second] == '*'
        }.forEach {
            gearMap[it] = listOf(num.toInt()) + gearMap.getOrDefault(it, emptyList())
        }

        return toCheck.any { input[it.first][it.second].isSymbol() }
    }

    private fun Char.isSymbol() = !this.isDigit() && this != '.'
}