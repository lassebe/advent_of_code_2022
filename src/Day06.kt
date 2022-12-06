fun main() {
    fun solve(input: String, windowSize: Int): Int {
        
        for (i in (0 until input.length - windowSize)) {
            if (input.substring(i, i + windowSize).toCharArray().toSet().size == windowSize)
                return i + windowSize
        }
        return -1

    }

    fun part1(input: String): Int = solve(input, 4)

    fun part2(input: String): Int = solve(input, 14)


    expect(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7)
    expect(part1("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5)
    expect(part1("nppdvjthqldpwncqszvftbrmjlhg"), 6)
    expect(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10)
    expect(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11)
    val input = readInput("Day06").first()
    println(part1(input))

    expect(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19)
    expect(part2("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23)
    expect(part2("nppdvjthqldpwncqszvftbrmjlhg"), 23)
    expect(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29)
    expect(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26)

    println(part2(input))
}