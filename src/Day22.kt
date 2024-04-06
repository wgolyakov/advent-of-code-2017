enum class Direction(val dx: Int, val dy: Int) {
	Up(0, -1),
	Down(0, 1),
	Left(-1, 0),
	Right(1, 0);

	fun left(): Direction {
		return when (this) {
			Up -> Left
			Down -> Right
			Left -> Down
			Right -> Up
		}
	}

	fun right(): Direction {
		return when (this) {
			Up -> Right
			Down -> Left
			Left -> Up
			Right -> Down
		}
	}

	fun reverse(): Direction {
		return when (this) {
			Up -> Down
			Down -> Up
			Left -> Right
			Right -> Left
		}
	}
}

enum class InfectionType {Clean, Weakened, Infected, Flagged}

fun main() {
	fun part1(input: List<String>): Int {
		val infection = mutableSetOf<Pair<Int, Int>>()
		for ((y, row) in input.withIndex()) {
			for ((x, c) in row.withIndex()) {
				if (c == '#') infection.add(x to y)
			}
		}
		var infectionCount = 0
		var y = input.size / 2
		var x = input[y].length / 2
		var direction = Direction.Up
		for (burst in 0 until 10000) {
			if (x to y in infection) {
				direction = direction.right()
				infection.remove(x to y)
			} else {
				direction = direction.left()
				infection.add(x to y)
				infectionCount++
			}
			x += direction.dx
			y += direction.dy
		}
		return infectionCount
	}

	fun part2(input: List<String>): Int {
		val infection = mutableMapOf<Pair<Int, Int>, InfectionType>()
		for ((y, row) in input.withIndex()) {
			for ((x, c) in row.withIndex()) {
				if (c == '#') infection[x to y] = InfectionType.Infected
			}
		}
		var infectionCount = 0
		var y = input.size / 2
		var x = input[y].length / 2
		var direction = Direction.Up
		for (burst in 0 until 10000000) {
			val type = infection[x to y] ?: InfectionType.Clean
			when (type) {
				InfectionType.Clean -> {
					direction = direction.left()
					infection[x to y] = InfectionType.Weakened
				}
				InfectionType.Weakened -> {
					infection[x to y] = InfectionType.Infected
					infectionCount++
				}
				InfectionType.Infected -> {
					direction = direction.right()
					infection[x to y] = InfectionType.Flagged
				}
				InfectionType.Flagged -> {
					direction = direction.reverse()
					infection.remove(x to y)
				}
			}
			x += direction.dx
			y += direction.dy
		}
		return infectionCount
	}

	val testInput = readInput("Day22_test")
	check(part1(testInput) == 5587)
	check(part2(testInput) == 2511944)

	val input = readInput("Day22")
	part1(input).println()
	part2(input).println()
}
