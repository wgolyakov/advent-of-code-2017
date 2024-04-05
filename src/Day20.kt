import kotlin.math.abs

fun main() {
	data class Coord(var x: Int, var y: Int, var z: Int) {
		fun add(c: Coord) {
			x += c.x
			y += c.y
			z += c.z
		}
	}

	class Particle(val p: Coord, val v: Coord, val a: Coord) {
		fun tick() {
			v.add(a)
			p.add(v)
		}

		fun distance() = abs(p.x) + abs(p.y) + abs(p.z)
	}

	fun parse(input: List<String>): List<Particle> {
		return input.map { line ->
			val (p, v, a) = line.split(", ").map { s -> s.substring(3, s.length - 1).split(',')
				.map { it.toInt() }.let { (x, y, z) -> Coord(x, y, z) } }
			Particle(p, v, a)
		}
	}

	fun part1(input: List<String>): Int {
		val particles = parse(input)
		for (t in 0 until 500) {
			for (particle in particles) {
				particle.tick()
			}
		}
		return particles.withIndex().minBy { it.value.distance() }.index
	}

	fun part2(input: List<String>): Int {
		val particles = parse(input).toMutableList()
		for (t in 0 until 500) {
			for (particle in particles) {
				particle.tick()
			}
			val collisions = mutableSetOf<Int>()
			for ((i1, p1) in particles.withIndex()) {
				for (i2 in i1 + 1 until particles.size) {
					val p2 = particles[i2]
					if (p1.p == p2.p) {
						collisions.add(i1)
						collisions.add(i2)
					}
				}
			}
			if (collisions.isNotEmpty()) {
				for (i in collisions.sorted().reversed()) {
					particles.removeAt(i)
				}
			}
		}
		return particles.size
	}

	val testInput = readInput("Day20_test")
	check(part1(testInput) == 0)
	val testInput2 = readInput("Day20_test2")
	check(part2(testInput2) == 1)

	val input = readInput("Day20")
	part1(input).println()
	part2(input).println()
}
