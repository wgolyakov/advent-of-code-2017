fun main() {
	fun part1(input: List<String>): Int {
		return input.count { it.split(' ').let { l -> l.size == l.distinct().size } }
	}

	fun lettersCount(s: String): Map<Char, Int> {
		val result = mutableMapOf<Char, Int>()
		for (c in s) {
			result[c] = (result[c] ?: 0) + 1
		}
		return result
	}

	fun anagrams(s1: String, s2: String) = s1.length == s2.length && lettersCount(s1) == lettersCount(s2)

	fun valid2(list: List<String>): Boolean {
		for ((i1, s1) in list.withIndex()) {
			for (i2 in i1 + 1 until list.size) {
				val s2 = list[i2]
				if (anagrams(s1, s2)) return false
			}
		}
		return true
	}

	fun part2(input: List<String>): Int {
		return input.count { valid2(it.split(' ')) }
	}

	val testInput = readInput("Day04_test")
	check(part1(testInput) == 2)
	val testInput2 = readInput("Day04_test2")
	check(part2(testInput2) == 3)

	val input = readInput("Day04")
	part1(input).println()
	part2(input).println()
}
