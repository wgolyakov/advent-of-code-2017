fun main() {
	fun flipH(grid: List<String>) = grid.reversed()

	fun flipV(grid: List<String>) = grid.map { it.reversed() }

	fun rotate(grid: List<String>): List<String> {
		val rGrid = grid.map { StringBuilder(it) }
		for (i in grid.indices) {
			for (j in grid.indices) {
				rGrid[i][j] = grid[grid.size - j - 1][i]
			}
		}
		return rGrid.map { it.toString() }
	}

	fun parse(input: List<String>): Map<List<String>, List<String>> {
		val rules = mutableMapOf<List<String>, List<String>>()
		for (line in input) {
			val (from, to) = line.split(" => ").map { it.split('/') }
			var grid = from
			for (i in 0 until 4) {
				rules[grid] = to
				rules[flipH(grid)] = to
				rules[flipV(grid)] = to
				grid = rotate(grid)
			}
		}
		return rules
	}

	fun oneIteration(grid: List<String>, rules: Map<List<String>, List<String>>): List<String> {
		val newGrid: List<StringBuilder>
		val n = grid.size
		if (n % 2 == 0) {
			val m = n + n / 2
			newGrid = List(m) { StringBuilder(".".repeat(m)) }
			for (y in 0 until n / 2) {
				for (x in 0 until n / 2) {
					val grid2x2 = grid.subList(y * 2, y * 2 + 2).map { it.substring(x * 2, x * 2 + 2) }
					val grid3x3 = rules[grid2x2]!!
					for (i in 0 until 3) {
						for (j in 0 until 3) {
							newGrid[y * 3 + i][x * 3 + j] = grid3x3[i][j]
						}
					}
				}
			}
		} else {
			val m = n + n / 3
			newGrid = List(m) { StringBuilder(".".repeat(m)) }
			for (y in 0 until n / 3) {
				for (x in 0 until n / 3) {
					val grid3x3 = grid.subList(y * 3, y * 3 + 3).map { it.substring(x * 3, x * 3 + 3) }
					val grid4x4 = rules[grid3x3]!!
					for (i in 0 until 4) {
						for (j in 0 until 4) {
							newGrid[y * 4 + i][x * 4 + j] = grid4x4[i][j]
						}
					}
				}
			}
		}
		return newGrid.map { it.toString() }
	}

	val startGrid = listOf(
		".#.",
		"..#",
		"###"
	)

	fun part1(input: List<String>, iterations: Int = 5): Int {
		val rules = parse(input)
		var grid = startGrid
		for (t in 0 until iterations) {
			grid = oneIteration(grid, rules)
		}
		return grid.sumOf { row -> row.count { it == '#' } }
	}

	fun part2(input: List<String>): Int {
		return part1(input, 18)
	}

	val testInput = readInput("Day21_test")
	check(part1(testInput, 2) == 12)

	val input = readInput("Day21")
	part1(input).println()
	part2(input).println()
}
