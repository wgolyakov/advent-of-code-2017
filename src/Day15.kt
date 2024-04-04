fun main() {
	fun part1(input: List<String>): Int {
		var a = input[0].substringAfterLast(' ').toLong()
		var b = input[1].substringAfterLast(' ').toLong()
		var matchCount = 0
		for (i in 0 until 40_000_000) {
			a = (a * 16807) % 2147483647
			b = (b * 48271) % 2147483647
			val aBits = a.toString(2).padStart(16, '0').takeLast(16)
			val bBits = b.toString(2).padStart(16, '0').takeLast(16)
			if (aBits == bBits) matchCount++
		}
		return matchCount
	}

	fun part2(input: List<String>): Int {
		var a = input[0].substringAfterLast(' ').toLong()
		var b = input[1].substringAfterLast(' ').toLong()
		var matchCount = 0
		for (i in 0 until 5_000_000) {
			do { a = (a * 16807) % 2147483647 } while (a % 4 != 0L)
			do { b = (b * 48271) % 2147483647 } while (b % 8 != 0L)
			val aBits = a.toString(2).padStart(16, '0').takeLast(16)
			val bBits = b.toString(2).padStart(16, '0').takeLast(16)
			if (aBits == bBits) matchCount++
		}
		return matchCount
	}

	val testInput = readInput("Day15_test")
	check(part1(testInput) == 588)
	check(part2(testInput) == 309)

	val input = readInput("Day15")
	part1(input).println()
	part2(input).println()
}
