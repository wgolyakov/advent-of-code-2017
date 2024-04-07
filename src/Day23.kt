fun main() {
	open class Argument {
		open fun get(): Int = 0
		open fun set(value: Int) {}
	}

	class ArgReg(val index: Int, val registers: IntArray) : Argument() {
		override fun get(): Int {
			return registers[index]
		}
		override fun set(value: Int) {
			registers[index] = value
		}
	}

	class ArgNum(val value: Int) : Argument() {
		override fun get(): Int {
			return value
		}
	}

	open class Instruction(val name: String, val x: Argument, val y: Argument) {
		open fun run(): Int  = 1
	}

	class SetInstruction(x: Argument, y: Argument) : Instruction("set", x, y) {
		override fun run(): Int {
			x.set(y.get())
			return 1
		}
	}

	class SubInstruction(x: Argument, y: Argument) : Instruction("sub", x, y) {
		override fun run(): Int {
			x.set(x.get() - y.get())
			return 1
		}
	}

	class MulInstruction(x: Argument, y: Argument) : Instruction("mul", x, y) {
		override fun run(): Int {
			x.set(x.get() * y.get())
			return 1
		}
	}

	class JnzInstruction(x: Argument, y: Argument) : Instruction("jnz", x, y) {
		override fun run(): Int {
			return if (x.get() != 0) y.get() else 1
		}
	}

	fun parse(input: List<String>, registers: IntArray): List<Instruction> {
		return input.map { line ->
			val instr = line.substring(0, 3)
			val (x, y) = line.substring(4).split(' ')
			val xArg = if (x[0].isLetter()) ArgReg(x[0] - 'a', registers) else ArgNum(x.toInt())
			val yArg = if (y[0].isLetter()) ArgReg(y[0] - 'a', registers) else ArgNum(y.toInt())
			when (instr) {
				"set" -> SetInstruction(xArg, yArg)
				"sub" -> SubInstruction(xArg, yArg)
				"mul" -> MulInstruction(xArg, yArg)
				"jnz" -> JnzInstruction(xArg, yArg)
				else -> error("Wrong instruction: $line")
			}
		}
	}

	fun part1(input: List<String>): Int {
		val registers = IntArray(8)
		val instructions = parse(input, registers)
		var mulCount = 0
		var i = 0
		while (i < instructions.size) {
			val instruction = instructions[i]
			if (instruction.name == "mul") mulCount++
			i += instruction.run()
		}
		return mulCount
	}

	fun part2(input: List<String>): Int {
		val registers = IntArray(8)
		registers[0] = 1 // a = 1
		val instructions = parse(input, registers)
		var i = 0
		val programSize = instructions.size
		while (i < programSize) {
			i += instructions[i].run()
		}
		return registers[7] // h
	}

	fun part2(): Int {
		val a = 1
		var h = 0
		var b = 65
		var c = b
		if (a != 0) {
			b = b * 100 + 100000
			c = b + 17000
		}
		while (true) {
			var f = 1
			for (d in 2 .. b) {
				for (e in 2 .. b) {
					if (d * e > b) break // optimization
					if (d * e == b) f = 0
				}
			}
			if (f == 0) h++
			if (b == c) break
			b += 17
		}
		return h
	}

	val input = readInput("Day23")
	part1(input).println() // 3969
	//part2(input).println()
	part2().println() // 917
}
