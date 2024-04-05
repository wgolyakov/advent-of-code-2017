fun main() {
	fun part1(input: List<String>): String {
		val letters = StringBuilder()
		var x = input[0].indexOf('|')
		var y = 0
		var dx = 0
		var dy = 1
		do {
			x += dx
			y += dy
			val c = input[y][x]
			if (c == '+') {
				val dir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
					.filter { (a, b) -> a != dx || b != dy }
					.filter { (a, b) -> a != -dx || b != -dy }
					.filter { (a, b) -> x + a >= 0 && y + b >= 0 }
					.filter { (a, b) -> y + b < input.size && x + a < input[y + b].length }
					.filter { (a, b) -> input[y + b][x + a] != ' ' }
					.single()
				dx = dir.first
				dy = dir.second
			} else if (c != '|' && c != '-' && c != ' ') {
				letters.append(c)
			}
		} while (c != ' ')
		return letters.toString()
	}

	fun part2(input: List<String>): Int {
		var steps = 0
		var x = input[0].indexOf('|')
		var y = 0
		var dx = 0
		var dy = 1
		do {
			x += dx
			y += dy
			val c = input[y][x]
			if (c == '+') {
				val dir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
					.filter { (a, b) -> a != dx || b != dy }
					.filter { (a, b) -> a != -dx || b != -dy }
					.filter { (a, b) -> x + a >= 0 && y + b >= 0 }
					.filter { (a, b) -> y + b < input.size && x + a < input[y + b].length }
					.filter { (a, b) -> input[y + b][x + a] != ' ' }
					.single()
				dx = dir.first
				dy = dir.second
			}
			steps++
		} while (c != ' ')
		return steps
	}

	val testInput = readInput("Day19_test")
	check(part1(testInput) == "ABCDEF")
	check(part2(testInput) == 38)

	val input = readInput("Day19")
	part1(input).println()
	part2(input).println()
}
