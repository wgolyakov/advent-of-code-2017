fun main() {
	class Scanner(val range: Int) {
		var position = if (range > 0) 0 else -1
		var moveUp = false

		fun move() {
			if (range < 2) return
			if (moveUp) {
				position--
				if (position == 0) moveUp = false
			} else {
				position++
				if (position == range - 1) moveUp = true
			}
		}

		fun setTime(time: Int) {
			if (range < 2) return
			val period = (range - 1) * 2
			val t = time % period
			if (t < range - 1) {
				position = t
				moveUp = false
			} else {
				position = period - t
				moveUp = true
			}
		}
	}

	fun parse(input: List<String>): List<Scanner> {
		val scanners = mutableListOf<Scanner>()
		for (line in input) {
			val (depth, range) = line.split(": "). map { it.toInt() }
			while (scanners.size < depth) scanners.add(Scanner(0))
			scanners.add(Scanner(range))
		}
		return scanners
	}

	fun part1(input: List<String>): Int {
		val scanners = parse(input)
		var severity = 0
		for ((t, scanner) in scanners.withIndex()) {
			if (scanner.position == 0) severity += t * scanner.range
			for (s in scanners) s.move()
		}
		return severity
	}

	fun part2(input: List<String>): Int {
		val scanners = parse(input)
		for (delay in 0 until Int.MAX_VALUE) {
			for (s in scanners) s.setTime(delay)
			var caught = false
			for (scanner in scanners) {
				if (scanner.position == 0) {
					caught = true
					break
				}
				for (s in scanners) s.move()
			}
			if (!caught) return delay
		}
		return -1
	}

	val testInput = readInput("Day13_test")
	check(part1(testInput) == 24)
	check(part2(testInput) == 10)

	val input = readInput("Day13")
	part1(input).println()
	part2(input).println()
}
