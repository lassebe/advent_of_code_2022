import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val signalStrengths = mutableListOf(1)
        var insPtr = 0
        var cycle = 1
        while (cycle < 220) {
            val currentInstruction = input[insPtr]

            if (currentInstruction == "noop") {
                signalStrengths.add(signalStrengths.last())
                insPtr++
                cycle++
                continue
            }
            // addx begins executing
            signalStrengths.add(signalStrengths.last())
            cycle++
            // addx has executed
            val operand = currentInstruction.split(" ").last().toInt()
            signalStrengths.add(signalStrengths.last() + operand)
            cycle++
            insPtr++
        }

        return signalStrengths.mapIndexed { index, signal ->
            Pair(index + 1, signal)
        }.filter { listOf(20, 60, 100, 140, 180, 220).contains(it.first) }
            .sumOf { it.first * it.second }
    }


    fun nextCrtOutput(signal: Int, cycle: Int): String =
        if (abs(signal - cycle) < 2) {
            "#"
        } else {
            "."
        }


    fun part2(input: List<String>): String {
        val overlapping = mutableListOf<String>()
        val signalStrengths = mutableListOf(1)
        var insPtr = 0
        var cycle = 1
        while (cycle < 240 + 1) {
            val currentInstruction = input[insPtr]
            overlapping.add(nextCrtOutput(signalStrengths.last(), ((cycle - 1) % 40)))

            if (currentInstruction == "noop") {
                signalStrengths.add(signalStrengths.last())
                insPtr++
                cycle++
                continue
            }
            // addx begins executing
            signalStrengths.add(signalStrengths.last())
            cycle++

            overlapping.add(nextCrtOutput(signalStrengths.last(), ((cycle - 1) % 40)))

            // addx has executed
            val operand = currentInstruction.split(" ").last().toInt()
            signalStrengths.add(signalStrengths.last() + operand)
            cycle++
            insPtr++
        }

        println(
            overlapping.chunked(40).joinToString("\n")
            { it.joinToString("") }
        )
        println()
        return overlapping.joinToString("")
    }


    val testInput = readInput("Day10_test")
    expect(13140, part1(testInput))
    val input = readInput("Day10")
    println(part1(input))
    expect(17840, part1(input))

    expect(
        "##..##..##..##..##..##..##..##..##..##..###...###...###...###...###...###...###.####....####....####....####....####....#####.....#####.....#####.....#####.....######......######......######......###########.......#######.......#######.....",
        part2(testInput)
    )

    expect(
        "####..##..#.....##..#..#.#....###...##..#....#..#.#....#..#.#..#.#....#..#.#..#.###..#..#.#....#....#..#.#....#..#.#....#....####.#....#.##.#..#.#....###..#.##.#....#..#.#....#..#.#..#.#....#....#..#.####.#..#.####..###..##..####.#.....###.",
        part2(input)
    )
}