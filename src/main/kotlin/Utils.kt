fun String.trimSplit(delim: String): List<String> = this.trim().split(delim).filterNot { it.isEmpty() }

fun lcm(ns: List<Long>): Long = ns.reduce { a, b ->
    a * (b / a.toBigInteger().gcd(b.toBigInteger()).toLong())
}