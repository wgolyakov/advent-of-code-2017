fun main() {
	class Program(val name: String, var weight: Int = 0, var totalWeight: Int = 0, var parent: Program? = null) {
		val children = mutableSetOf<Program>()
		override fun toString() = "$totalWeight"
	}

	fun parse(input: List<String>): Map<String, Program> {
		val programs = mutableMapOf<String, Program>()
		for (line in input) {
			val (name, weight) = line.substringBefore(" -> ").split(' ')
			val w = weight.substring(1, weight.length - 1).toInt()
			val program = programs.getOrPut(name) { Program(name, w) }
			program.weight = w
			val children = line.substringAfter(" -> ", "").split(", ")
			for (childName in children) {
				if (childName.isEmpty()) continue
				val child = programs.getOrPut(childName) { Program(childName) }
				program.children.add(child)
				child.parent = program
			}
		}
		return programs
	}

	fun calcTotalWeight(program: Program) {
		for (child in program.children) {
			calcTotalWeight(child)
		}
		program.totalWeight = program.weight + program.children.sumOf { it.totalWeight }
	}

	fun findWrongWeight(program: Program): Int {
		for (child in program.children) {
			val res = findWrongWeight(child)
			if (res > 0) return res
		}
		if (program.children.size > 1) {
			val sortedChildren = program.children.sortedBy { it.totalWeight }
			if (sortedChildren.first().totalWeight != sortedChildren.last().totalWeight) {
				val delta =	sortedChildren.last().totalWeight - sortedChildren.first().totalWeight
				return if (sortedChildren[0].totalWeight == sortedChildren[1].totalWeight) {
					sortedChildren.last().weight - delta
				} else {
					sortedChildren.first().weight + delta
				}
			}
		}
		return 0
	}

	fun part1(input: List<String>): String {
		val programs = parse(input)
		val root = programs.values.first { it.parent == null }
		return root.name
	}

	fun part2(input: List<String>): Int {
		val programs = parse(input)
		val root = programs.values.first { it.parent == null }
		calcTotalWeight(root)
		return findWrongWeight(root)
	}

	val testInput = readInput("Day07_test")
	check(part1(testInput) == "tknk")
	check(part2(testInput) == 60)

	val input = readInput("Day07")
	part1(input).println()
	part2(input).println()
}
