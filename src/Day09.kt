fun main() {
	fun score(group: String): Int {
		var score = 0
		var level = 0
		var garbage = false
		var canceled = false
		for (c in group) {
			when (c) {
				'{' -> if (!garbage) level++
				'}' -> if (!garbage) { score += level; level-- }
				'<' -> garbage = true
				'>' -> if (!canceled) garbage = false
				'!' -> canceled = !canceled
			}
			if (canceled && c != '!') canceled = false
		}
		return score
	}

	fun garbageCount(group: String): Int {
		var count = 0
		var garbage = false
		var canceled = false
		for (c in group) {
			when (c) {
				'<' -> if (garbage) { if (!canceled) count++ } else garbage = true
				'>' -> if (!canceled) garbage = false
				'!' -> canceled = !canceled
				else -> if (garbage && !canceled) count++
			}
			if (canceled && c != '!') canceled = false
		}
		return count
	}

	fun part1(input: List<String>): Int {
		return input.sumOf { score(it) }
	}

	fun part2(input: List<String>): Int {
		return input.sumOf { garbageCount(it) }
	}

	val testInput = readInput("Day09_test")
	check(part1(testInput) == 1 + 6 + 5 + 16 + 1 + 9 + 9 + 3)
	val testInput2 = readInput("Day09_test2")
	check(part2(testInput2) == 0 + 17 + 3 + 2 + 0 + 0 + 10)

	val input = readInput("Day09")
	part1(input).println()
	part2(input).println()
}
