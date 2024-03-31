fun main() {
	fun part1(input: List<String>): Int {
		val jumps = input.map { it.toInt() }.toMutableList()
		var steps = 0
		var i = 0
		while (i >= 0 && i < jumps.size) {
			val offset = jumps[i]
			jumps[i] = offset + 1
			i += offset
			steps++
		}
		return steps
	}

	fun part2(input: List<String>): Int {
		val jumps = input.map { it.toInt() }.toMutableList()
		var steps = 0
		var i = 0
		while (i >= 0 && i < jumps.size) {
			val offset = jumps[i]
			jumps[i] = if (offset >= 3) offset - 1 else offset + 1
			i += offset
			steps++
		}
		return steps
	}

	val testInput = readInput("Day05_test")
	check(part1(testInput) == 5)
	check(part2(testInput) == 10)

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
