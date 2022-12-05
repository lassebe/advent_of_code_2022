import kotlin.streams.toList

fun main() {
    fun itemPriority(c: Int): Int =
        if (c >= 97) (c - 97 + 1)
        else (c - 65 + 27)


    fun part1Alt(input: List<String>) =
        input.sumOf { bag ->
            val wholeBag = bag.chars().toList()
            val (firstBagItems, secondBagItems) = wholeBag.chunked(wholeBag.size / 2).map { it.toSet() }

            for (item in firstBagItems) {
                if (secondBagItems.contains(item)) {
                    return@sumOf itemPriority(item)
                }
            }
            return@sumOf 0
        }


    fun String.toCharSet() = this.chars().toList().toSet()

    fun part2(input: List<String>) =
        input.chunked(3).sumOf { bagGroup ->
            val firstBag = bagGroup[0].toCharSet()
            val secondBag = bagGroup[1].toCharSet()
            val thirdBag = bagGroup[2].toCharSet()

            for (item in firstBag) {
                if (secondBag.contains(item) && thirdBag.contains(item)) {
                    return@sumOf itemPriority(item)
                }
            }
            return@sumOf 0

        }

    val testInput = readInput("Day03_test")
    println(part1Alt(testInput))
    check(part1Alt(testInput) == 157)

    val input = readInput("Day03")
    check(part2(testInput) == 70)

    println(part1Alt(input))
    println(part2(input))
}