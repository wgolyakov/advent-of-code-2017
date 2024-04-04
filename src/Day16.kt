fun main() {
	fun dance(s: StringBuilder, moves: List<String>) {
		val size = s.length
		for (move in moves) {
			when (move[0]) {
				's' -> {
					val x = move.drop(1).toInt()
					val a = s.substring(0, size - x)
					val b = s.substring(size - x)
					s.replace(0, x, b)
					s.replace(x, size, a)
				}
				'x' -> {
					val (a, b) = move.drop(1).split('/').map { it.toInt() }
					val tmp = s[a]
					s[a] = s[b]
					s[b] = tmp
				}
				'p' ->  {
					val (a, b) = move.drop(1).split('/').map { it[0] }
					val ai = s.indexOf(a)
					val bi = s.indexOf(b)
					s[ai] = b
					s[bi] = a
				}
				else -> error("Wrong move: $move")
			}
		}
	}

	fun part1(input: List<String>, size: Int = 16): String {
		val s = StringBuilder(CharArray(size) { 'a' + it }.joinToString(""))
		val moves = input[0].split(',')
		dance(s, moves)
		return s.toString()
	}

	fun part2(input: List<String>, size: Int = 16): String {
		val s = StringBuilder(CharArray(size) { 'a' + it }.joinToString(""))
		val moves = input[0].split(',')
		val states = mutableSetOf<String>()
		do {
			states.add(s.toString())
			dance(s, moves)
		} while (s.toString() !in states)
		val period = states.size
		for (i in 0 until 1000000000 % period) {
			dance(s, moves)
		}
		return s.toString()
	}

	val testInput = readInput("Day16_test")
	check(part1(testInput, 5) == "baedc")

	val input = readInput("Day16")
	part1(input).println()
	part2(input).println()
}
