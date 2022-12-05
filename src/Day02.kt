fun main() {

    val scores = mapOf(
        "Rock" to 1,
        "Paper" to 2,
        "Scissor" to 3,
        "Win" to 6,
        "Draw" to 3,
        "Loss" to 0
    )

    fun part1(input: List<String>): Int = input.sumOf {
        val plays = it.split(" ").map {
            mapOf(
                "A" to "Rock",
                "X" to "Rock",
                "B" to "Paper",
                "Y" to "Paper",
                "C" to "Scissor",
                "Z" to "Scissor",
            )[it]
        }
        val theyPlay = plays.first()
        val youPlay = plays.last()
        val resultScore = scores[if (theyPlay == "Rock" && youPlay == "Paper"
            || theyPlay == "Paper" && youPlay == "Scissor"
            || theyPlay == "Scissor" && youPlay == "Rock"
        ) {
            "Win"
        } else if (theyPlay == youPlay) {
            "Draw"
        } else {
            "Loss"
        }]!!
        val playScore = scores[plays.last()!!]!!
        resultScore + playScore
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val plays = it.split(" ").map { decode ->
                mapOf(
                    "A" to "Rock",
                    "X" to "Loss",
                    "B" to "Paper",
                    "Y" to "Draw",
                    "C" to "Scissor",
                    "Z" to "Win",
                )[decode]!!
            }
            val theyPlay = plays.first()
            val expectedResult = plays.last()

            val youPlay = when (expectedResult) {
                "Win" -> {
                    mapOf(
                        "Scissor" to "Rock",
                        "Rock" to "Paper",
                        "Paper" to "Scissor",
                    )[theyPlay]!!
                }

                "Loss" -> {
                    mapOf(
                        "Rock" to "Scissor",
                        "Paper" to "Rock",
                        "Scissor" to "Paper",
                    )[theyPlay]!!
                }

                else -> {
                    theyPlay
                }
            }

            val resultScore = scores[expectedResult]!!
            val playScore = scores[youPlay]!!
            resultScore + playScore
        }.sum()
    }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
    check(part2(testInput) == 12)
    println(part2(input))
}