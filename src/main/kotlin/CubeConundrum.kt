import java.io.File

// https://adventofcode.com/2023/day/2
class CubeConundrum {

    fun solve() {
        val red = 12
        val green = 13
        val blue = 14

        var sum = 0
        var sum2 = 0

        var gameId = 1
        File("inputs/day2_cube_conundrum").forEachLine { game ->
            val cubeSets = game.split(":")[1].trim().split(";").map { set ->
                set.trim().split(",").associate { cube ->
                    cube.trim().split(" ").let {
                        Pair(it[1], it[0].toInt())
                    }
                }
            }

            val minRed = cubeSets.maxOf { it.getOrDefault("red", 0) }
            val minGreen = cubeSets.maxOf { it.getOrDefault("green", 0) }
            val minBlue = cubeSets.maxOf { it.getOrDefault("blue", 0) }

            // part 1
            if (minRed <= red && minGreen <= green && minBlue <= blue) {
                sum += gameId
            }

            // part 2
            sum2 += minRed * minGreen * minBlue

            gameId++
        }

        println("Part 1: $sum")
        println("Part 2: $sum2")
    }
}
