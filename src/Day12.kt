fun main() {
	class Program(val id: Int) {
		val pipes = mutableSetOf<Program>()
		override fun toString() = "$id"
	}

	fun parse(input: List<String>): Map<Int, Program> {
		val programs = mutableMapOf<Int, Program>()
		for (line in input) {
			val (idStr, pipesStr) = line.split(" <-> ")
			val id = idStr.toInt()
			val program = programs.getOrPut(id) { Program(id) }
			val pipes = pipesStr.split(", ").map { it.toInt() }.map { programs.getOrPut(it) { Program(it) } }
			program.pipes.addAll(pipes)
		}
		return programs
	}

	fun findGroups(programs: Map<Int, Program>): List<Set<Program>> {
		val groups = mutableListOf<MutableSet<Program>>()
		for (program in programs.values) {
			val linkGroups = groups.filter { gr -> program.pipes.any { it in gr } }
			when (linkGroups.size) {
				0 -> {
					val group = mutableSetOf(program)
					groups.add(group)
				}
				1 -> {
					val group = linkGroups.first()
					group.add(program)
				}
				else -> {
					val group1 = linkGroups.first()
					for (gr in linkGroups) {
						if (gr === group1) continue
						group1.addAll(gr)
						groups.remove(gr)
					}
					group1.add(program)
				}
			}
		}
		return groups
	}

	fun part1(input: List<String>): Int {
		val programs = parse(input)
		val groups = findGroups(programs)
		val program0 = programs[0]!!
		return groups.first { program0 in it }.size
	}

	fun part2(input: List<String>): Int {
		val programs = parse(input)
		val groups = findGroups(programs)
		return groups.size
	}

	val testInput = readInput("Day12_test")
	check(part1(testInput) == 6)
	check(part2(testInput) == 2)

	val input = readInput("Day12")
	part1(input).println()
	part2(input).println()
}
