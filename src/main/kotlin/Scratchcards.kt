import java.io.File

class Scratchcards {
    fun solve() {
        val cardCounts = mutableMapOf<Int, Int>()

        var sum = 0
        File("inputs/day4_scratchcards").readLines().forEachIndexed { index, line ->
            val numbers = line.toNumbers(false)
            val winning = line.toNumbers(true)

            // part 1
            var s = 0
            var c = 0
            cardCounts[index] = cardCounts.getOrDefault(index, 0) + 1
            numbers.forEach {
                if (winning.contains(it)) {
                    s = 2 * s + if (s == 0) {
                        1
                    } else {
                        0
                    }
                    c++
                }
            }
            sum += s

            // part 2
            for (i in 1..c) {
                cardCounts[index + i] = cardCounts.getOrDefault(index + i, 0) + cardCounts.getOrDefault(index, 0)
            }
        }

        println("Part 1: $sum") //26346
        println("Part 2: ${cardCounts.values.sum()}") //8467762
    }


    private fun String.toNumbers(winningNumbers: Boolean) =
        trimSplit(":")[1]
            .trimSplit("|")[if (winningNumbers) 1 else 0]
            .trimSplit(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim().toInt() }


    private fun String.trimSplit(delim: String): List<String> = this.trim().split(delim)

}