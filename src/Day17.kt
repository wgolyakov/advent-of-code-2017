fun main() {
	fun part1(input: List<String>): Int {
		val step = input[0].toInt()
		val buffer = mutableListOf(0)
		var pos = 0
		for (i in 1 .. 2017) {
			pos = (pos + step) % buffer.size + 1
			buffer.add(pos, i)
		}
		return buffer[pos + 1]
	}

	fun part2(input: List<String>): Int {
		val step = input[0].toInt()
		var bufferSize = 1
		var pos = 0
		var afterZero = -1
		for (i in 1 .. 50000000) {
			pos = (pos + step) % bufferSize + 1
			if (pos == 1) afterZero = i
			bufferSize++
		}
		return afterZero
	}

	val testInput = readInput("Day17_test")
	check(part1(testInput) == 638)

	val input = readInput("Day17")
	part1(input).println()
	part2(input).println()
}
