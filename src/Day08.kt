fun main() {
	fun part1(input: List<String>): Int {
		val registers = mutableMapOf<String, Int>()
		for (line in input) {
			val (instruction, condition) = line.split(" if ")
			val (condRegName, cond, condValue) = condition.split(' ')
			val cReg = registers[condRegName] ?: 0
			val cVal = condValue.toInt()
			val condState = when (cond) {
				">" -> cReg > cVal
				"<" -> cReg < cVal
				">=" -> cReg >= cVal
				"<=" -> cReg <= cVal
				"==" -> cReg == cVal
				"!=" -> cReg != cVal
				else -> error("Wrong condition: $cond")
			}
			if (!condState) continue
			val (regName, instr, value) = instruction.split(' ')
			val reg = registers[regName] ?: 0
			val iVal = value.toInt()
			registers[regName] = when (instr) {
				"inc" -> reg + iVal
				"dec" -> reg - iVal
				else -> error("Wrong instruction: $instr")
			}
		}
		return registers.values.max()
	}

	fun part2(input: List<String>): Int {
		var maxValue = 0
		val registers = mutableMapOf<String, Int>()
		for (line in input) {
			val (instruction, condition) = line.split(" if ")
			val (condRegName, cond, condValue) = condition.split(' ')
			val cReg = registers[condRegName] ?: 0
			val cVal = condValue.toInt()
			val condState = when (cond) {
				">" -> cReg > cVal
				"<" -> cReg < cVal
				">=" -> cReg >= cVal
				"<=" -> cReg <= cVal
				"==" -> cReg == cVal
				"!=" -> cReg != cVal
				else -> error("Wrong condition: $cond")
			}
			if (!condState) continue
			val (regName, instr, value) = instruction.split(' ')
			val reg = registers[regName] ?: 0
			val iVal = value.toInt()
			val v = when (instr) {
				"inc" -> reg + iVal
				"dec" -> reg - iVal
				else -> error("Wrong instruction: $instr")
			}
			registers[regName] = v
			if (maxValue < v) maxValue = v
		}
		return maxValue
	}

	val testInput = readInput("Day08_test")
	check(part1(testInput) == 1)
	check(part2(testInput) == 10)

	val input = readInput("Day08")
	part1(input).println()
	part2(input).println()
}
