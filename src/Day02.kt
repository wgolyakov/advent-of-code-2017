fun main() {
	fun part1(input: List<String>): Int {
		return input.map { it.split("\\s+".toRegex()).map { s -> s.toInt() } }.sumOf { it.max() - it.min() }
	}

	fun part2(input: List<String>): Int {
		return input.map { it.split("\\s+".toRegex()).map { s -> s.toInt() } }.sumOf {
			it.map { a -> it.map { b -> a to b } }.flatten()
				.first { (a, b) -> a != b && a % b == 0 }
				.let { (a, b) -> a / b }
		}
	}

	val testInput = readInput("Day02_test")
	check(part1(testInput) == 18)
	val testInput2 = readInput("Day02_test2")
	check(part2(testInput2) == 9)

	val input = readInput("Day02")
	part1(input).println()
	part2(input).println()
}
