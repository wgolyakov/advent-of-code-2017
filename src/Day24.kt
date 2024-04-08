fun main() {
	class Component(val port1: Int, val port2: Int) {
		var freePort = port1

		fun strength() = port1 + port2

		fun match(bridge: List<Component>): Boolean {
			if (bridge.isEmpty()) return port1 == 0 || port2 == 0
			val c = bridge.last()
			return c.freePort == port1 || c.freePort == port2
		}

		fun addToBridge(bridge: List<Component>): List<Component> {
			if (bridge.isEmpty()) {
				freePort = if (port1 == 0) port2 else port1
			} else {
				val c = bridge.last()
				freePort = if (port1 == c.freePort) port2 else port1
			}
			return (bridge + this)
		}
	}

	fun parse(input: List<String>): List<Component> {
		return input.map { line ->
			line.split('/').map { it.toInt() }.let { (p1, p2) -> Component(p1, p2) }
		}
	}

	fun strongestRecurs(bridge: List<Component>, components: List<Component>): Int {
		val matchComponents = components.filter { it.match(bridge) }
		if (matchComponents.isEmpty()) return bridge.sumOf { it.strength() }
		var maxStrength = 0
		for (component in matchComponents) {
			val st = strongestRecurs(component.addToBridge(bridge), components - component)
			if (maxStrength < st) maxStrength = st
		}
		return maxStrength
	}

	fun longestRecurs(bridge: List<Component>, components: List<Component>): Pair<Int, Int> {
		val matchComponents = components.filter { it.match(bridge) }
		if (matchComponents.isEmpty()) return bridge.size to bridge.sumOf { it.strength() }
		var maxLength = 0
		var maxStrength = 0
		for (component in matchComponents) {
			val curr = longestRecurs(component.addToBridge(bridge), components - component)
			if ((maxLength < curr.first) || (maxLength == curr.first && maxStrength < curr.second)) {
				maxLength = curr.first
				maxStrength = curr.second
			}
		}
		return maxLength to maxStrength
	}

	fun part1(input: List<String>): Int {
		val components = parse(input)
		return strongestRecurs(emptyList(), components)
	}

	fun part2(input: List<String>): Int {
		val components = parse(input)
		return longestRecurs(emptyList(), components).second
	}

	val testInput = readInput("Day24_test")
	check(part1(testInput) == 31)
	check(part2(testInput) == 19)

	val input = readInput("Day24")
	part1(input).println()
	part2(input).println()
}
