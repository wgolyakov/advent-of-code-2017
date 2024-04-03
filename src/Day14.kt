fun main() {
	fun knotHash(input: String): String {
		val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
		val listSize = 256
		val sparseHash = MutableList(listSize) { it }
		var pos = 0
		var skipSize = 0
		for (round in 0 until 64) {
			for (len in lengths) {
				val subList = if (pos + len <= listSize) {
					sparseHash.subList(pos, pos + len).reversed()
				} else {
					(sparseHash.subList(pos, listSize) + sparseHash.subList(0, pos + len - listSize)).reversed()
				}
				for (i in 0 until len) sparseHash[(pos + i) % listSize] = subList[i]
				pos = (pos + len + skipSize) % listSize
				skipSize++
			}
		}
		val denseHash = sparseHash.windowed(16, 16).map { it.reduce { a, b -> a xor b } }
		return denseHash.joinToString("") { it.toString(16).padStart(2, '0') }
	}

	fun makeGrid(keyString:String): List<String> {
		val grid = mutableListOf<String>()
		for (row in 0 until 128) {
			val hash = knotHash("$keyString-$row")
			val bits = hash.map { it.digitToInt(16).toString(2).padStart(4, '0') }
				.joinToString("")
			grid.add(bits)
		}
		return grid
	}

	fun part1(input: List<String>): Int {
		val grid = makeGrid(input[0])
		return grid.sumOf { row -> row.count { it == '1' } }
	}

	fun part2(input: List<String>): Int {
		val grid = makeGrid(input[0])
		var groupIndex = 0
		var delGroupCount = 0
		val groups = grid.map { IntArray(it.length) }
		for ((y, row) in grid.withIndex()) {
			for ((x, c) in row.withIndex()) {
				if (c == '0') continue
				val adjacent = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
				val adjacentGroups = adjacent.map { (dx, dy) -> x + dx to y + dy }
					.filter { (xa, ya) -> xa >= 0 && ya >= 0 && xa < row.length && ya < grid.size }
					.map { (xa, ya) -> groups[ya][xa] }.filter { it != 0 }
				when (adjacentGroups.size) {
					0 -> groups[y][x] = ++groupIndex
					1 -> groups[y][x] = adjacentGroups.first()
					else -> {
						val group1 = adjacentGroups.first()
						for (gr in adjacentGroups) {
							if (gr == group1) continue
							groups.forEach { line ->
								line.withIndex().filter { it.value == gr }.forEach { line[it.index] = group1 }
							}
							delGroupCount++
						}
						groups[y][x] = group1
					}
				}
			}
		}
		return groupIndex - delGroupCount
	}

	val testInput = readInput("Day14_test")
	check(part1(testInput) == 8108)
	check(part2(testInput) == 1242)

	val input = readInput("Day14")
	part1(input).println()
	part2(input).println()
}
