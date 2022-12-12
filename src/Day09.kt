import kotlin.math.abs
import kotlin.math.min

fun main() {
    val map = (0..40).map { i ->
        (0..40).map { j -> j }
    }

    fun printMap(head: Pair<Int, Int>, tail: Pair<Int, Int>, visited: Map<Pair<Int, Int>, Boolean>) {
//        return
        map.indices.forEach { i ->
            var symbol = " . "
            map[0].indices.forEach { j ->
                if (tail.first == i && tail.second == j)
                    symbol = "T"
                if (head.first == i && head.second == j)
                    symbol = "H"
                if (visited.getOrDefault(Pair(i, j), false))
                    symbol = "#"
                print(symbol)
                symbol = "."
            }

            println()
        }
        println()
    }

    fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val xDistance = abs(head.first - tail.first)
        val yDistance = abs(head.second - tail.second)
        if (xDistance + yDistance < 2 || xDistance == 1 && yDistance == 1)
            return tail
        var xPos = tail.first
        var yPos = tail.second
        if (xDistance >= 1) {
            if (head.first > tail.first) {
                xPos += 1
            } else {
                xPos -= 1
            }
        }
        if (yDistance >= 1) {
            if (head.second > tail.second) {
                yPos += 1
            } else {
                yPos -= 1
            }
        }

        return Pair(xPos, yPos)
    }


    fun part1(input: List<String>): Int {
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()

        input.forEach { line ->
            val (dir, l) = line.split(" ")
            val length = l.toInt()
            when (dir) {
                "R" -> {
                    repeat((0 until length).count()) {
                        head = Pair(head.first, head.second + 1)
                        tail = moveTail(head, tail)
                        visited[tail] = true
                    }
                }

                "U" -> {
                    repeat((0 until length).count()) {
                        head = Pair(head.first + 1, head.second)
                        tail = moveTail(head, tail)
                        visited[tail] = true
                    }
                }

                "L" -> {
                    repeat((0 until length).count()) {
                        head = Pair(head.first, head.second - 1)
                        tail = moveTail(head, tail)
                        visited[tail] = true
                    }
                }

                "D" -> {
                    repeat((0 until length).count()) {
                        head = Pair(head.first - 1, head.second)
                        tail = moveTail(head, tail)
                        visited[tail] = true
                    }
                }
            }
        }
        return visited.count { (_, v) -> v }
    }

    fun moveRopeSegment(rope: MutableList<Pair<Int, Int>>) {
        (1 until rope.size).forEach { i ->
            rope[i] = moveTail(rope[i - 1], rope[i])
        }
    }

    fun part2(input: List<String>): Int {
        var xOffset = 0
        var yOffset = 0

        var rope = mutableListOf<Pair<Int,Int>>()
        repeat(10) {
            rope.add(Pair(0,0))
        }
        val visited = mutableMapOf<Pair<Int, Int>, Boolean>()

        input.forEach { line ->
            val (dir, l) = line.split(" ")
            val length = l.toInt()
            when (dir) {
                "R" -> {
                    repeat((0 until length).count()) {
                        rope[0] = Pair(rope[0].first, rope[0].second + 1)
                        moveRopeSegment(rope)
                        visited[rope.last()] = true
                    }
                }

                "U" -> {
                    repeat((0 until length).count()) {
                        rope[0] = Pair(rope[0].first + 1, rope[0].second)
                        moveRopeSegment(rope)
                        visited[rope.last()] = true
                    }
                }

                "L" -> {
                    repeat((0 until length).count()) {
                        rope[0] = Pair(rope[0].first, rope[0].second - 1)
                        moveRopeSegment(rope)
                        visited[rope.last()] = true
                    }
                }

                "D" -> {
                    repeat((0 until length).count()) {
                        rope[0] = Pair(rope[0].first - 1, rope[0].second)
                        moveRopeSegment(rope)
                        visited[rope.last()] = true
                    }
                }
            }
            xOffset = min(xOffset, rope[0].first)
            yOffset = min(yOffset, rope[0].second)
        }
        return visited.count { (_, v) -> v }
    }


    val testInput = readInput("Day09_test")
    expect(13, part1(testInput))
    val input = readInput("Day09")
    println(part1(input))
    expect(6311, part1(input))
    expect(1, part2(testInput))
    expect(36, part2(readInput("Day09_test2")))

    println(part2(input))
    expect(2482, part2(input))
}