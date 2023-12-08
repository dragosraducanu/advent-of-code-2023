import java.io.File

//https://adventofcode.com/2023/day/8
class HauntedWasteland {
    fun solve() {
        val (directions, map) = parseInput()

        solve1(directions.toList(), map)
        solve2(directions.toList(), map)
    }

    private fun solve2(directions: List<Char>, map: Map<String, Pair<String, String>>) {
        val counts = map.keys
            .filter { it.last() == 'A' }
            .map { curr ->
                count(curr, { it.last() != 'Z' }, directions, map)
            }

        println("Part 2: ${lcm(counts)}") //7309459565207
    }

    private fun solve1(directions: List<Char>, map: Map<String, Pair<String, String>>) {
        println("Part 1: ${count("AAA", { it != "ZZZ" }, directions, map)}") //13301
    }

    private fun count(start: String, endCondition: (String) -> Boolean, directions: List<Char>, map: Map<String, Pair<String, String>>): Long {
        var count = 0L
        var c = start
        var dir = directions
        while (endCondition.invoke(c)) {
            c = if (dir[0] == 'L') {
                map[c]!!.first
            } else {
                map[c]!!.second
            }

            dir = dir.subList(1, dir.size) + dir[0]
            count++
        }
        return count
    }

    private fun parseInput(): Pair<List<Char>, Map<String, Pair<String, String>>> {
        val input = File("inputs/day8_haunted_wasteland").readLines()
        val directions = input[0].toList()

        val map = input.drop(2).associate { line ->
            val (from, to) = line.split(" = ")
            val (left, right) = to
                .drop(1)
                .dropLast(1)
                .split(", ")

            from to (left to right)
        }
        return directions to map
    }
}