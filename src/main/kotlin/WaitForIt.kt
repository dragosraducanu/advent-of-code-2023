import java.io.File

//https://adventofcode.com/2023/day/6
class WaitForIt {
    fun solve() {
        val races = parseInput()

        val part1 = findTotalWays(races)
        println("Part 1: $part1") //1084752

        val part2 = findTotalWays(
            listOf(
                races.reduce { (at, ad), (pt, pd) ->
                    Pair("${at}${pt}".toLong(), "${ad}${pd}".toLong())
                }
            )
        )

        println("Part 2: $part2") //28228952
    }

    private fun findTotalWays(races: List<Pair<Long, Long>>): Long {
        var total = 1L
        races.forEach { (t, d) ->
            total *= (0..t).count {
                it * (t - it) > d
            }
        }
        return total
    }

    private fun parseInput(): List<Pair<Long, Long>> {
        val (times, distances) = File("inputs/day6_wait_for_it").readLines()
            .map { it.trimSplit(":")[1].trimSplit(" ").map { it.toLong() } }

        return times.mapIndexed { index, t ->
            Pair(t, distances[index])
        }
    }
}