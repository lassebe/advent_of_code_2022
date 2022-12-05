data class Instruction(val count: Int, val from: Int, val to: Int)

fun main() {
    fun getBoxes(input: List<String>): List<ArrayDeque<String>> {
        val stacks = mutableListOf<ArrayDeque<String>>()

        val cargoLines = input.filter { l -> l.contains("[") }.reversed()
        for (i in (0 until cargoLines.first().chunked(4).size))
            stacks.add(ArrayDeque())
        for (line in cargoLines) {
            line.chunked(4).withIndex().forEach { (i, box) ->
                if (box.contains("["))
                    stacks[i].addLast(box)
            }
        }
        return stacks
    }


    fun instructions(input: List<String>): List<Instruction> {
        return input.filter { it.startsWith("move ") }.map { line ->
            val ins = line.split(" ")
                .filter { w -> w.matches(Regex("\\d+")) }
                .map { d -> d.toInt() }
            Instruction(ins[0], ins[1], ins[2])
        }
    }

    fun solve(input: List<String>, move: (boxes: List<ArrayDeque<String>>, ins: List<Instruction>) -> Unit): String {
        val boxes = getBoxes(input)
        move(boxes, instructions(input))
        return boxes.joinToString("") {
            it.last()[1].toString()
        }
    }

    fun part1(input: List<String>) =
        solve(input) { boxes, ins ->
            for (instruction in ins) {
                for (i in (0 until instruction.count)) {
                    boxes[instruction.to - 1]
                        .addLast(boxes[instruction.from - 1].removeLast())
                }
            }
        }


    fun part2(input: List<String>) =
        solve(input) { boxes, ins ->
            for (instruction in ins) {
                val tmp = mutableListOf<String>()
                for (i in (0 until instruction.count)) {
                    tmp.add(boxes[instruction.from - 1].removeLast())
                }

                for (t in tmp.reversed()) {
                    boxes[instruction.to - 1].add(t)
                }
            }
        }


    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")

    println(part1(input))
    check(part2(testInput) == "MCD")
    println(part2(input))
}