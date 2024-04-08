fun main() {
	class Action(val writeValue: Int, val move: Int, val continueState: String)
	class State(val name: String, val actions: Array<Action>)

	fun part1(input: List<String>): Int {
		var i = 0
		val beginState = input[i++].substringAfterLast(' ').dropLast(1)
		val steps = input[i++].substringBeforeLast(" steps.").substringAfterLast(' ').toInt()
		val states = mutableMapOf<String, State>()
		i++
		while (i < input.size) {
			val stateName = input[i++].substringAfterLast(' ').dropLast(1)
			i++
			var writeValue = input[i++].substringAfterLast(' ').dropLast(1).toInt()
			var move = input[i++].substringAfterLast(' ').dropLast(1).let { if (it == "right") 1 else -1 }
			var continueState = input[i++].substringAfterLast(' ').dropLast(1)
			val action0 = Action(writeValue, move, continueState)
			i++
			writeValue = input[i++].substringAfterLast(' ').dropLast(1).toInt()
			move = input[i++].substringAfterLast(' ').dropLast(1).let { if (it == "right") 1 else -1 }
			continueState = input[i++].substringAfterLast(' ').dropLast(1)
			val action1 = Action(writeValue, move, continueState)
			states[stateName] = State(stateName, arrayOf(action0, action1))
			i++
		}
		val tape = mutableListOf(0)
		var cursor = 0
		var state = states[beginState]!!
		for (step in 0 until steps) {
			val currValue = tape[cursor]
			val action = state.actions[currValue]
			tape[cursor] = action.writeValue
			cursor += action.move
			if (cursor < 0) {
				tape.add(0, 0)
				cursor = 0
			} else if (cursor == tape.size) {
				tape.add(0)
			}
			state = states[action.continueState]!!
		}
		return tape.sum()
	}

	val testInput = readInput("Day25_test")
	check(part1(testInput) == 3)

	val input = readInput("Day25")
	part1(input).println()
}
