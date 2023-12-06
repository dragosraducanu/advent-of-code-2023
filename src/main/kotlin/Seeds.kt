import java.io.File
import java.util.LinkedList
import java.util.Queue
import kotlin.math.max
import kotlin.math.min
//https://adventofcode.com/2023/day/5
class Seeds {

    fun solve() {
        val (seeds, maps) = parseInput()
        part1(seeds, maps)
        part2(seeds, maps)
    }


    private fun part1(seeds: List<Long>, maps: List<List<Triple<Long, Long, Long>>>) {
        val vQ: Queue<Long> = LinkedList<Long>().apply {
            addAll(seeds)
        }
        maps.forEach { ranges ->
            val newQ = LinkedList<Long>()

            while (vQ.isNotEmpty()) {
                val v = vQ.remove()

                var matchFound = false
                ranges.forEach loop@{ range ->
                    if (range.second <= v && v < range.second + range.third) {
                        newQ.add(v - range.second + range.first)
                        matchFound = true
                        return@loop
                    }
                }
                if (!matchFound) {
                    newQ.add(v)
                }
            }
            vQ.clear()
            vQ.addAll(newQ)
        }

        println("Part 1: ${vQ.min()}") //662197086
    }

    private fun part2(seeds: List<Long>, maps: List<List<Triple<Long, Long, Long>>>) {
        val seedRanges = seeds.chunked(2).map { Pair(it[0], it[0] + it[1]) }

        val vQ2: Queue<Pair<Long, Long>> = LinkedList()
        vQ2.addAll(seedRanges)

        maps.forEach { ranges ->
            val newQ = LinkedList<Pair<Long, Long>>()
            while (vQ2.isNotEmpty()) {
                val (start, end) = vQ2.remove()
                var matchFound = false
                ranges.forEach loop@{
                    val intersectStart = max(start, it.second)
                    val intersectEnd = min(end, it.second + it.third)
                    if (intersectStart < intersectEnd) {
                        newQ.add(
                            Pair(
                                intersectStart - it.second + it.first,
                                intersectEnd - it.second + it.first
                            )
                        )
                        if (intersectStart > start) {
                            vQ2.add(Pair(start, intersectStart))
                        }
                        if (end > intersectEnd) {
                            vQ2.add(Pair(intersectEnd, end))
                        }
                        matchFound = true
                        return@loop
                    }
                }
                if (!matchFound) {
                    newQ.add(Pair(start, end))
                }
            }
            vQ2.clear()
            vQ2.addAll(newQ)
        }

        println("Part 2: ${vQ2.minOf { it.first }}") //52510809
    }

    private fun parseInput(): Pair<List<Long>, List<List<Triple<Long, Long, Long>>>> {
        val input = File("inputs/day5_seeds").readLines()

        val seeds = input[0].trimSplit(":")[1].trimSplit(" ").map { it.toLong() }

        val maps = mutableListOf<List<Triple<Long, Long, Long>>>()
        var current = mutableListOf<Triple<Long, Long, Long>>()

        input.filterNot { it.isEmpty() || it.contains("seeds:") }
            .forEach { s ->
                if (s.contains("map")) {
                    if (current.isNotEmpty()) {
                        maps.add(current)
                    }
                    current = mutableListOf()
                } else {
                    val (a, b, c) = s.trimSplit(" ").map { it.toLong() }
                    current.add(Triple(a, b, c))
                }
            }
        maps.add(current)
        return Pair(seeds, maps)
    }
}