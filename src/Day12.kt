fun main() {

    fun buildGraph(input: List<String>): Triple<MutableMap<Pair<Int, Int>, List<Pair<Int, Int>>>, MutableMap<Pair<Int, Int>, Int>, Pair<Pair<Int, Int>,Pair<Int,Int>>> {
        val edges = mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()
        val cost = mutableMapOf<Pair<Int, Int>, Int>()
        val chunked = input.map { it.chunked(1) }
        var start = Pair(0, 0)
        var end = Pair(0, 0)
        chunked.mapIndexed { i, line ->
            List(line.size) { j ->
                val current = Pair(i, j)
                if (line[j] == "S") {
                    start = current
                    cost[start] = 0
                } else if (line[j] == "E") {
                    end = current
                    cost[current] = 25
                } else {
                    cost[current] = (line[j].toCharArray().first()).code - 97
                }

                if (i > 0) {
                    edges[current] = edges.getOrDefault(current, emptyList()) + Pair(i - 1, j)
                }
                if (j > 0) {
                    edges[current] = edges.getOrDefault(current, emptyList()) + Pair(i, j - 1)
                }
                if (i != chunked.size - 1) {
                    edges[current] = edges.getOrDefault(current, emptyList()) + Pair(i + 1, j)
                }
                if (j != line.size - 1) {
                    edges[current] = edges.getOrDefault(current, emptyList()) + Pair(i, j + 1)
                }
            }
        }
        return  Triple(edges, cost, Pair(start,end))
    }


    fun bfs(
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        edges: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
        cost: Map<Pair<Int, Int>, Int>
    ): Int {
        val parent = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            if (visited.contains(next))
                continue
            visited.add(next)

            if (next == end) {
                break
            }

            val actualNeighbours = edges.getOrDefault(next, emptyList()).filter {
                cost[it]!! <= cost[next]!! + 1
            }
            if (actualNeighbours.isEmpty()) throw IllegalStateException("no neighbours!")
            actualNeighbours.forEach {
                if (!queue.contains(it)) {
                    if (parent[it] == null)
                        parent[it] = next
                    queue.add(it)
                }
            }
        }
        val path = mutableListOf(end)
        while (path.last() != start) {
            val parentNode = parent[path.last()]
            path.add(parentNode!!)
        }
        return path.size - 1
    }

    fun bfsPredicate(
        start: Pair<Int, Int>,
        end: (Pair<Int, Int>) -> Boolean,
        edges: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
        cost: Map<Pair<Int, Int>, Int>
    ): Int {
        val parent = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()
        var endNode = Pair(-1, -1)
        queue.add(start)
        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            if (visited.contains(next))
                continue
            visited.add(next)

            if (end(next)) {
                endNode = next
                break
            }

            val possibleNeighbours = edges.getOrDefault(next, emptyList())
            val actualNeighbours = possibleNeighbours.filter {
                cost[next]!! - 1 <= cost[it]!! && it != start
            }
            if (actualNeighbours.isEmpty()) throw IllegalStateException("no neighbours!")
            actualNeighbours.forEach {
                if (!queue.contains(it)) {
                    if (parent[it] == null)
                        parent[it] = next
                    queue.add(it)
                }
            }
        }
        val path = mutableListOf(endNode)
        while (path.last() != start) {
            val parentNode = parent[path.last()]
            path.add(parentNode!!)
        }
        return path.size - 1
    }

    fun part1(input: List<String>): Int {
        val (edges, cost, startEnd) = buildGraph(input)
        val (start,end) = startEnd
        return bfs(start, end, edges, cost)
    }

    fun part2(input: List<String>): Int {
        val (edges, cost, startEnd) = buildGraph(input)
        val (_,end) = startEnd
        return bfsPredicate(end, { node -> cost[node] == 0 }, edges, cost)
    }


    val testInput = readInput("Day12_test")
    expect(31, part1(testInput))
    val input = readInput("Day12")
    println(part1(input))
    expect(408, part1(input))

    expect(
        29,
        part2(testInput)
    )

    expect(
        399,
        part2(input)
    )
    println(part2(input))
}