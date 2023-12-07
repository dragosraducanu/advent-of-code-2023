import java.io.File

//https://adventofcode.com/2023/day/7
class CamelCards {

    fun solve() {
        val input = File("inputs/day7_camel_cards").readLines()

        val hands = input.map { line ->
            line.trimSplit(" ").let {
                Pair(it[0], it[1].toLong())
            }
        }

        println("Part 1: ${sum(hands)}") //248559379
        println("Part 2: ${sum(hands, true)}") //249631254
    }

    private fun sum(h: List<Pair<String, Long>>, joker: Boolean = false): Long {
        val comparator = object : Comparator<Pair<String, Long>> {
            override fun compare(p1: Pair<String, Long>, p2: Pair<String, Long>): Int {
                val h1 = p1.first
                val h2 = p2.first

                val comp = h2.type(joker).compareTo(h1.type(joker))

                if (comp == 0) {
                    val map = mapOf(
                        'A' to 'E',
                        'K' to 'D',
                        'Q' to 'C',
                        'J' to if(joker) '1' else 'B',
                        'T' to 'A'
                        )

                    return map.entries.fold(h2) { acc, (k, v) -> acc.replace(k, v) }.compareTo(
                        map.entries.fold(h1) { acc, (k, v) -> acc.replace(k, v) }
                    )
                }
                return comp
            }
        }

        val hands = h.sortedWith(comparator)

        var sum = 0L
        hands.reversed().forEachIndexed { index, (_, bid) ->
            sum += bid * (index + 1)
        }

        return sum
    }

    private fun String.type(joker: Boolean = false): Int {
        val counts = mutableMapOf<Char, Int>()

        forEach {
            counts[it] = counts.getOrDefault(it, 0) + 1
        }

        if (joker) {
            val m = counts.filterKeys { it != 'J' }.maxByOrNull { (_, v) -> v }?.key ?: return 6

            counts[m] = counts.getOrDefault(m, 0) + counts.getOrDefault('J', 0)
            counts['J'] = 0
        }

        return when {
            counts.values.any { it == 5 } -> 6 // five of a kind
            counts.values.any { it == 4 } -> 5 // four of a kind
            counts.values.any { it == 3 } && counts.values.any { it == 2 } -> 4 // full house
            counts.values.any { it == 3 } && !counts.values.any { it == 2 } -> 3 // three of a kind
            counts.values.count { it == 2 } == 2 -> 2 // two pairs
            counts.values.count { it == 2 } == 1 -> 1 // single pair
            else -> 0 // high card
        }
    }
}
