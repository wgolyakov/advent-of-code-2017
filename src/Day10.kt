fun main() {
	fun part1(input: List<String>, listSize: Int = 256): Int {
		val lengths = input[0].split(',').map { it.toInt() }
		val list = MutableList(listSize) { it }
		var pos = 0
		for ((skipSize, len) in lengths.withIndex()) {
			val subList = if (pos + len <= listSize) {
				list.subList(pos, pos + len).reversed()
			} else {
				(list.subList(pos, listSize) + list.subList(0, pos + len - listSize)).reversed()
			}
			for (i in 0 until len) list[(pos + i) % listSize] = subList[i]
			pos = (pos + len + skipSize) % listSize
		}
		return list[0] * list[1]
	}

	fun part2(input: String): String {
		val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
		val listSize = 256
		val list = MutableList(listSize) { it }
		var pos = 0
		var skipSize = 0
		for (round in 0 until 64) {
			for (len in lengths) {
				val subList = if (pos + len <= listSize) {
					list.subList(pos, pos + len).reversed()
				} else {
					(list.subList(pos, listSize) + list.subList(0, pos + len - listSize)).reversed()
				}
				for (i in 0 until len) list[(pos + i) % listSize] = subList[i]
				pos = (pos + len + skipSize) % listSize
				skipSize++
			}
		}
		val denseHash = list.windowed(16, 16).map { it.reduce { a, b -> a xor b } }
		return denseHash.joinToString("") { it.toString(16).padStart(2, '0') }
	}

	val testInput = readInput("Day10_test")
	check(part1(testInput, 5) == 12)
	val testInput2 = readInput("Day10_test2")
	check(part2(testInput2[0]) == "a2582a3a0e66e6e86e3812dcb672a272")
	check(part2(testInput2[1]) == "33efeb34ea91902bb2f59c9920caa6cd")
	check(part2(testInput2[2]) == "3efbe78a8d82f29979031a4aa0b16a9d")
	check(part2(testInput2[3]) == "63960835bcdc130f0b66d7ff4f6a5a8e")

	val input = readInput("Day10")
	part1(input).println()
	part2(input[0]).println()
}
