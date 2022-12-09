fun main() {
    fun transformInput(input: List<String>) = input.map { line ->
        val split = line.split("")
        split.subList(1, split.size - 1).map {
            it.toInt()
        }
    }

    fun part1(input: List<String>): Int {
        val heights = transformInput(input)
        return heights.indices.sumOf<Int> { i ->
            heights[i].indices.sumOf<Int> { j ->
                if (i == 0 || j == 0 || i == heights.size - 1 || j == heights.size - 1) {
                    return@sumOf 1 as Int // compiler strikes back
                }
                val currentTree = heights[i][j]
                val visibleDirections = listOf(
                    (0 until i).all { heights[it][j] < currentTree },
                    (i + 1 until heights.size).all { heights[it][j] < currentTree },
                    (0 until j).all { heights[i][it] < currentTree },
                    (j + 1 until heights[i].size).all { heights[i][it] < currentTree })
                return@sumOf if (visibleDirections.any { it })
                    1
                else
                    0
            }
        }
    }

    fun visibleTrees(
        heights: List<List<Int>>,
        currentTree: Int,
        fixed: Int,
        range: IntProgression,
        row: Boolean
    ): Int {
        var count = 0
        for (k in range) {
            count += 1
            if (row) {
                if (heights[fixed][k] >= currentTree) {
                    return count
                }
            } else {
                if (heights[k][fixed] >= currentTree) {
                    return count
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val heights = transformInput(input)
        val scenicScores = mutableListOf<Int>()
        heights.indices.forEach { i ->
            heights[i].indices.forEach { j ->
                val currentTree = heights[i][j]
                var topCount = visibleTrees(heights, currentTree, j, (i - 1 downTo 0), false)
                var bottomCount = visibleTrees(heights, currentTree, j, (i + 1 until heights.size), false)
                var leftCount = visibleTrees(heights, currentTree, i, (j - 1 downTo 0), true)
                var rightCount = visibleTrees(heights, currentTree, i, (j + 1 until heights[i].size), true)
                scenicScores.add(topCount * bottomCount * leftCount * rightCount)
            }
        }
        return scenicScores.maxOf { it }
    }


    val testInput = readInput("Day08_test")
    expect(21, part1(testInput))
    val input = readInput("Day08")
    expect(1719, part1(input))
    println(part1(input))
    expect(8, part2(testInput))
    println(part2(input))
    expect(590824, part2(input))
}