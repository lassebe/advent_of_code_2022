fun main() {
    fun solve(input: List<String>, predicate: (a: Int, b: Int, c: Int, d: Int) -> Boolean): Int {
        return input.sumOf { schedule ->
            val assignments = schedule.split(",")
            val elf1 = assignments.first().split("-").map { it.toInt() }
            val elf2 = assignments.last().split("-").map { it.toInt() }
            if (predicate(elf1[0], elf1[1], elf2[0], elf2[1]))
                return@sumOf 1.toInt() // Compiler silliness

            return@sumOf 0
        }
    }

    fun part1(input: List<String>) =
        solve(input) { a, b, c, d -> a >= c && b <= d || c >= a && d <= b }


    fun part2(input: List<String>) =
        solve(input) { a, b, c, d -> !(a < c && b < c || a > d) }


    val testInput = readInput("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 2)

    val input = readInput("Day04")

    println(part1(input))
    check(part2(testInput) == 4)
    println(part2(input))
}