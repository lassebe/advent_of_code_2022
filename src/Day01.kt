fun main() {
    fun solve(input: List<String>): List<Int> {
        val elves = input.joinToString(":")
            .split("::")
            .map { it.split(":") }
            .map { it.map { it.toInt() } }
            .map { it.sum() }

        return elves.sorted()
    }

    fun part1(input: List<String>): Int {
        return solve(input).last()
    }

    fun part2(input: List<String>): Int {
        val elves = solve(input)
        return elves.last() + elves.get(elves.size - 2) + elves.get(elves.size - 3)
    }

    val testInput = readInput("Day01_test")
    println(part1(testInput))
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}