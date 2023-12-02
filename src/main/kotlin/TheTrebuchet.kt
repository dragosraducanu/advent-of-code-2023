import java.io.File

// https://adventofcode.com/2023/day/1
class TheTrebuchet {

    private val digits = IntRange(0, 9).map { it.toString() }
    private val digits2 = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    private val allDigits = digits + digits2

    fun solve() {
        val input = File("inputs/day1_the_trebuchet").readLines()
        var sum1 = 0
        var sum2 = 0

        input.forEach {
            sum1 += (10 * (it.findAnyOf(digits)?.second)!!.toDigit() + it.findLastAnyOf(digits)?.second!!.toDigit())
            sum2 += (10 * (it.findAnyOf(allDigits)?.second)!!.toDigit() + it.findLastAnyOf(allDigits)?.second!!.toDigit())
        }

        println("${this.javaClass.name} Part 1: $sum1")
        println("${this.javaClass.name} Part 2: $sum2")
    }

    private fun String.toDigit() = try {
        this.toInt()
    } catch (e: NumberFormatException) {
        digits2.indexOf(this)
    }
}