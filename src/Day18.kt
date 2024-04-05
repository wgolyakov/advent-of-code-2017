fun main() {
	class Program(num: Int, val instructions: List<String>,
				  val inQueue: MutableList<Long>, val outQueue: MutableList<Long>) {
		val registers = mutableMapOf('p' to num.toLong())
		var index = 0
		var sendCount = 0

		fun isEnd() = index < 0 || index >= instructions.size

		fun isLocked() = instructions[index].take(3) == "rcv" && inQueue.isEmpty()

		fun isFinished() = isEnd() || isLocked()

		fun run() {
			while (index >= 0 && index < instructions.size) {
				val instruction = instructions[index]
				val instr = instruction.take(3)
				val args = instruction.substring(4).split(' ')
				val xReg = args[0][0]
				val x = if (xReg.isLetter()) registers[xReg] ?: 0 else args[0].toLong()
				val y = if (args.size > 1) {
					val yReg = args[1][0]
					if (yReg.isLetter()) registers[yReg] ?: 0 else args[1].toLong()
				} else 0
				when (instr) {
					"snd" -> { outQueue.add(x); sendCount++ }
					"set" -> registers[xReg] = y
					"add" -> registers[xReg] = x + y
					"mul" -> registers[xReg] = x * y
					"mod" -> registers[xReg] = x % y
					"rcv" -> if (inQueue.isNotEmpty()) registers[xReg] = inQueue.removeFirst() else break
					"jgz" -> if (x > 0) index += y.toInt() - 1
					else -> error("Wrong instruction: $instruction")
				}
				index++
			}
		}
	}

	fun part1(input: List<String>): Long {
		val registers = mutableMapOf<Char, Long>()
		var lastSound = 0L
		var i = 0
		while (i >= 0 && i < input.size) {
			val instruction = input[i]
			val instr = instruction.substring(0, 3)
			val args = instruction.substring(4).split(' ')
			val xReg = args[0][0]
			val x = if (xReg.isLetter()) registers[xReg] ?: 0 else args[0].toLong()
			val y = if (args.size > 1) {
				val yReg = args[1][0]
				if (yReg.isLetter()) registers[yReg] ?: 0 else args[1].toLong()
			} else 0
			when (instr) {
				"snd" -> lastSound = x
				"set" -> registers[xReg] = y
				"add" -> registers[xReg] = x + y
				"mul" -> registers[xReg] = x * y
				"mod" -> registers[xReg] = x % y
				"rcv" -> if (x != 0L) break
				"jgz" -> if (x > 0) i += y.toInt() - 1
				else -> error("Wrong instruction: $instruction")
			}
			i++
		}
		return lastSound
	}

	fun part2(input: List<String>): Int {
		val inQueue1 = mutableListOf<Long>()
		val inQueue2 = mutableListOf<Long>()
		val prog1 = Program(0, input, inQueue1, inQueue2)
		val prog2 = Program(1, input, inQueue2, inQueue1)
		while (!prog1.isFinished() || !prog2.isFinished()) {
			prog1.run()
			prog2.run()
		}
		return prog2.sendCount
	}

	val testInput = readInput("Day18_test")
	check(part1(testInput) == 4L)

	val input = readInput("Day18")
	part1(input).println()
	part2(input).println()
}
