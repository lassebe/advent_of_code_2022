fun main() {
    data class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val divisor: Long,
        val test: (Long) -> Boolean,
        val testTrueId: Int,
        val testFalseId: Int
    )

    fun monkeys(input: List<String>): MutableList<Monkey> {
        val monkeys = mutableListOf<Monkey>()

        var i = 0
        var monkeyId = 0
        while (i < input.size) {
            i++
            val startingItems = input[i].split("Starting items: ").last().split(", ").map { it.toLong() }
            i++
            val op = input[i].split("Operation: new = ").last()
            i++
            val test = input[i].split("Test: divisible by ").last().toInt()
            i++
            val nextIfTrue = input[i].split("If true: throw to monkey ").last().toInt()
            i++
            val nextIfFalse = input[i].split("If false: throw to monkey ").last().toInt()
            i++
            i++

            val func = op.split(" ")

            val monkey = Monkey(
                monkeyId,
                startingItems.toMutableList(),
                {
                    val a = if (func[0] == "old") {
                        it
                    } else {
                        func[0].toLong()
                    }

                    val b = if (func[2] == "old") {
                        it
                    } else {
                        func[2].toLong()
                    }

                    when (func[1]) {
                        "*" -> a * b
                        "+" -> a + b
                        "-" -> a - b
                        "/" -> a / b
                        else -> throw IllegalStateException("invalid op ${func[1]}")
                    }
                },
                test.toLong(),
                {
                    (it % test == 0L)
                },
                nextIfTrue,
                nextIfFalse
            )
            monkeys.add(
                monkey
            )
            monkeyId++
        }
        return monkeys
    }

    fun part1(input: List<String>): Int {
        val monkeys = monkeys(input)
        val roundToMonkeys = mutableMapOf<Int, Int>()

        for (round in (1..20)) {
            for (monkey in monkeys) {
                roundToMonkeys[monkey.id] = roundToMonkeys.getOrDefault(monkey.id, 0) + monkey.items.size
                while (monkey.items.isNotEmpty()) {
                    val newWorry = monkey.operation(monkey.items.removeFirst())
                    val bored = (newWorry / 3)
                    val resultOfTest = monkey.test(bored)
                    monkeys[if (resultOfTest) monkey.testTrueId else monkey.testFalseId].items.add(bored)
                }
            }
        }
        val x = roundToMonkeys.map { it.value }.sorted()
        return x[x.size - 1] * x[x.size - 2]
    }

    fun part2(input: List<String>): Long {
        val monkeys = monkeys(input)
        val roundToMonkeys = mutableMapOf<Int, Long>()
        val product = monkeys.map { it.divisor }.reduce { acc, p -> acc * p }

        for (round in (1..10000)) {
            for (monkey in monkeys) {
                roundToMonkeys[monkey.id] = roundToMonkeys.getOrDefault(monkey.id, 0) + monkey.items.size

                while (monkey.items.isNotEmpty()) {
                    val newWorry = monkey.operation(monkey.items.removeFirst()) % product
                    val resultOfTest = monkey.test(newWorry)
                    monkeys[if (resultOfTest) monkey.testTrueId else monkey.testFalseId].items.add(newWorry)
                }
            }
        }

        val x = roundToMonkeys.map { it.value }.sorted()
        return x[x.size - 1] * x[x.size - 2]
    }


    val testInput = readInput("Day11_test")
    expect(10605, part1(testInput))
    val input = readInput("Day11")
    println(part1(input))
    expect(78960, part1(input))

    expect(
        2713310158,
        part2(testInput)
    )

    expect(
        14561971968,
        part2(input)
    )
}