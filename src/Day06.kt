fun main() {
	fun part1(input: List<String>): Int {
		var banks = input[0].split('\t').map { it.toInt() }.toMutableList()
		val states = mutableSetOf<List<Int>>()
		while (banks !in states) {
			states.add(banks)
			val (maxIndex, maxBlocks) = banks.withIndex().sortedWith(
				compareByDescending<IndexedValue<Int>> { it.value }.thenBy { it.index }).first()
			banks = banks.toMutableList()
			banks[maxIndex] = 0
			for (i in 1 .. maxBlocks) {
				banks[(maxIndex + i) % banks.size]++
			}
		}
		return states.size
	}

	fun part2(input: List<String>): Int {
		var banks = input[0].split('\t').map { it.toInt() }.toMutableList()
		val states = mutableSetOf<List<Int>>()
		while (banks !in states) {
			states.add(banks)
			val (maxIndex, maxBlocks) = banks.withIndex().sortedWith(
				compareByDescending<IndexedValue<Int>> { it.value }.thenBy { it.index }).first()
			banks = banks.toMutableList()
			banks[maxIndex] = 0
			for (i in 1 .. maxBlocks) {
				banks[(maxIndex + i) % banks.size]++
			}
		}
		return states.size - states.indexOf(banks)
	}

	val testInput = readInput("Day06_test")
	check(part1(testInput) == 5)
	check(part2(testInput) == 4)

	val input = readInput("Day06")
	part1(input).println()
	part2(input).println()
}
