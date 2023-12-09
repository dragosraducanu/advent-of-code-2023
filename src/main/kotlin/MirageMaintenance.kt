import java.io.File

//https://adventofcode.com/2023/day/9
class MirageMaintenance {
    fun solve() {
        val input = File("inputs/day9_mirage_maintenance").readLines()

        val report = input.map {
            it.split(" ").map { it.toInt() }
        }

        var sum = 0
        var sum2 = 0

        report.forEach { measurements ->
            var mes = measurements
            var diffs: MutableList<Int>
            sum += measurements.last()

            var firsts = mutableListOf(measurements.first())

            while (mes.isNotEmpty() && mes.count { it == 0 } != mes.size) {
                diffs = mutableListOf()
                for (i in 0..<(mes.size - 1)) {
                    diffs += mes[i + 1] - mes[i]
                }

                sum += diffs.last()
                firsts += diffs.first()
                mes = diffs
            }

            firsts = firsts.reversed().toMutableList()

            sum2 += firsts.reduce { acc, i -> i - acc }
        }

        println("Part 1: $sum") // 1702218515
        println("Part 2: $sum2") // 925
    }
}