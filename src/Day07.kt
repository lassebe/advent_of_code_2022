fun main() {

    fun sizeOf(
        dirName: String,
        fileStructure: Map<String, List<String>>,
        dirSize: Map<String, Long>,
    ): Long {
        val subDirs = fileStructure.getOrDefault(dirName, emptyList())
        return dirSize.getOrDefault(dirName, 0) + subDirs.sumOf {
            sizeOf(
                it,
                fileStructure,
                dirSize,
            )
        }
    }


    fun buildFileStructure(input: List<String>): Pair<Map<String, List<String>>, Map<String, Long>> {
        val fileStructure = mutableMapOf<String, List<String>>()
        val dirSize = mutableMapOf<String, Long>()
        var currentDir = "/"
        for (i in input.indices) {
            val line = input[i]
            if (line.startsWith("\$ cd")) {
                val nextDir = line.split(" cd ").last()
                currentDir = when (nextDir) {
                    "" -> "/"
                    "/" -> "/"
                    ".." -> {
                        if (currentDir == "/")
                            "/"
                        else
                            currentDir.substring(0, currentDir.lastIndexOf("/"))
                    }

                    else -> if (currentDir.length > 1) "$currentDir/$nextDir" else "/$nextDir"
                }
            }
            if (line.startsWith("\$ ls")) {
                var j = i + 1
                var size = 0L
                fileStructure[currentDir] = fileStructure.getOrDefault(currentDir, emptyList())
                while (j < input.size && !input[j].startsWith("\$")) {
                    val subLine = input[j]
                    val fileInfo = subLine.split(" ")

                    if (fileInfo.first() != "dir") {
                        size += fileInfo.first().toLong()
                    } else {
                        val subDirName = fileInfo.last()
                        fileStructure[currentDir] =
                            fileStructure[currentDir]!! + if (currentDir.length > 1) "$currentDir/$subDirName" else "/$subDirName"
                    }

                    j++
                }
                dirSize[currentDir] = size
            }
        }
        return Pair(fileStructure, dirSize)
    }

    fun part1(input: List<String>): Long {
        val (fileStructure, dirSize) = buildFileStructure(input)
        return fileStructure.keys.map { dirName ->
            Pair(dirName, sizeOf(dirName, fileStructure, dirSize))
        }.filter { it.second < 100000 }.sumOf { it.second }
    }

    fun part2(input: List<String>): Long {
        val totalSize = 70000000
        val (fileStructure, dirSize) = buildFileStructure(input)
        val sizes = fileStructure.keys.map { dirName ->
            Pair(dirName, sizeOf(dirName, fileStructure, dirSize))
        }
        val rootSize = sizes.find { it.first == "/" }!!.second
        val remainingSize = totalSize - rootSize
        return sizes.filter {
            it.first != "/" && remainingSize + it.second >= 30000000
        }.minOf { it.second }
    }


    val testInput = readInput("Day07_test")
    expect(part1(testInput), 95437)
    val input = readInput("Day07")
    expect(part1(input), 1307902)
    println(part1(input))
    expect(part2(testInput), 24933642)
    println(part2(input))
}