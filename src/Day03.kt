import kotlin.math.abs

fun main() {
	fun part1(input: String): Int {
		val square = input.toInt()
		var spSize = 1
		var x = 0
		var y = 0
		var n = 1
		loop@ while (n < square) {
			// right
			x++
			spSize += 2
			n++
			if (n == square) break@loop
			// up
			for (i in 0..spSize - 3) {
				y--
				n++
				if (n == square) break@loop
			}
			// left
			for (i in 0..spSize - 2) {
				x--
				n++
				if (n == square) break@loop
			}
			// down
			for (i in 0..spSize - 2) {
				y++
				n++
				if (n == square) break@loop
			}
			// right
			for (i in 0..spSize - 2) {
				x++
				n++
				if (n == square) break@loop
			}
		}
		return abs(x) + abs(y)
	}

	fun adjacentSum(grid: MutableMap<Pair<Int, Int>, Int>, x: Int, y: Int): Int {
		var res = 0
		for (a in -1 .. 1) {
			for (b in -1 .. 1) {
				res += grid[x + a to y + b] ?: 0
			}
		}
		grid[x to y] = res
		return res
	}

	fun part2(input: String): Int {
		val square = input.toInt()
		val grid = mutableMapOf<Pair<Int, Int>, Int>()
		var spSize = 1
		var x = 0
		var y = 0
		var n = 1
		grid[0 to 0] = 1
		loop@ while (n <= square) {
			// right
			x++
			spSize += 2
			n = adjacentSum(grid, x, y)
			if (n > square) break@loop
			// up
			for (i in 0..spSize - 3) {
				y--
				n = adjacentSum(grid, x, y)
				if (n > square) break@loop
			}
			// left
			for (i in 0..spSize - 2) {
				x--
				n = adjacentSum(grid, x, y)
				if (n > square) break@loop
			}
			// down
			for (i in 0..spSize - 2) {
				y++
				n = adjacentSum(grid, x, y)
				if (n > square) break@loop
			}
			// right
			for (i in 0..spSize - 2) {
				x++
				n = adjacentSum(grid, x, y)
				if (n > square) break@loop
			}
		}
		return n
	}

	val testInput = readInput("Day03_test")
	check(part1(testInput[0]) == 0)
	check(part1(testInput[1]) == 3)
	check(part1(testInput[2]) == 2)
	check(part1(testInput[3]) == 31)

	val input = readInput("Day03")
	part1(input[0]).println()
	part2(input[0]).println()
}
