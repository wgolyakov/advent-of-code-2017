fun main() {
    fun part1(input: String): Int {
        return (input + input[0]).windowed(2).filter { it[0] == it[1] }.sumOf { it[0].digitToInt() }
    }

    fun part2(input: String): Int {
        val l = input.length
        val f = l / 2
        return input.filterIndexed { i, c -> c == input[(i + f) % l] }.sumOf { it.digitToInt() }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput[0]) == 3)
    check(part1(testInput[1]) == 4)
    check(part1(testInput[2]) == 0)
    check(part1(testInput[3]) == 9)
    check(part2(testInput[4]) == 6)
    check(part2(testInput[5]) == 0)
    check(part2(testInput[6]) == 4)
    check(part2(testInput[7]) == 12)
    check(part2(testInput[8]) == 4)

    val input = readInput("Day01")
    part1(input[0]).println()
    part2(input[0]).println()
}
