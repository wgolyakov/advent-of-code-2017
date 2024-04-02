import kotlin.math.abs
import kotlin.math.max

fun main() {
	fun distance(x: Int, y: Int): Int {
		val a = abs(x)
		val b = abs(y)
		return if (a >= b) a else a + (b - a) / 2
	}

	fun part1(input: List<String>): Int {
		val path = input[0].split(',')
		var x = 0
		var y = 0
		for (dir in path) {
			when (dir) {
				"n" -> y += 2
				"s" -> y -= 2
				"ne" -> { x++; y++ }
				"se" -> { x++; y-- }
				"sw" -> { x--; y-- }
				"nw" -> { x--; y++ }
				else -> error("Wrong direction: $dir")
			}
		}
		return distance(x, y)
	}

	fun part2(input: List<String>): Int {
		val path = input[0].split(',')
		var maxDistance = 0
		var x = 0
		var y = 0
		for (dir in path) {
			when (dir) {
				"n" -> y += 2
				"s" -> y -= 2
				"ne" -> { x++; y++ }
				"se" -> { x++; y-- }
				"sw" -> { x--; y-- }
				"nw" -> { x--; y++ }
				else -> error("Wrong direction: $dir")
			}
			maxDistance = max(distance(x, y), maxDistance)
		}
		return maxDistance
	}

	val testInput = readInput("Day11_test")
	check(part1(testInput) == 3)
	check(part2(testInput) == 3)

	val input = readInput("Day11")
	part1(input).println()
	part2(input).println()
}
