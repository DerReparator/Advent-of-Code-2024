import unittest

from day12_2 import Solution

class Solution2024Day12Part2Test(unittest.TestCase):
    def test_Day12Part2Test(self):
        print("Starting Day12 Part 2 simple")
        self.assertEqual(80, Solution().solvePart2("""AAAA
BBCD
BBCC
EEEC"""))

# class Solution2024Day12Part2EnclosedRegions(unittest.TestCase):
#     def test_Day12Part2EnclosedRegions(self):
#         print("Starting Day12 Part 2 Enclosed Regions")
#         self.assertEqual(436, Solution().solvePart2("""OOOOO
# OXOXO
# OOOOO
# OXOXO
# OOOOO"""))

# class Solution2024Day12Part2EShaped(unittest.TestCase):
#     def test_Day12Part2EShaped(self):
#         print("Starting Day12 Part 2 E-shaped")
#         self.assertEqual(236, Solution().solvePart2("""EEEEE
# EXXXX
# EEEEE
# EXXXX
# EEEEE"""))

# class Solution2024Day12Part2NoMiddleCross(unittest.TestCase):
#     def test_Day12Part2NoMiddleCross(self):
#         print("Starting Day12 Part 2 No Middle Cross")
#         self.assertEqual(368, Solution().solvePart2("""AAAAAA
# AAABBA
# AAABBA
# ABBAAA
# ABBAAA
# AAAAAA"""))

# class Solution2024Day12Part2LargerExample(unittest.TestCase):
#     def test_Day12Part2LargerExample(self):
#         print("Starting Day12 Part 2 Larger Example")
#         self.assertEqual(1206, Solution().solvePart2("""RRRRIICCFF
# RRRRIICCCF
# VVRRRCCFFF
# VVRCCCJFFF
# VVVVCJJCFE
# VVIVCCJJEE
# VVIIICJJEE
# MIIIIIJJEE
# MIIISIJEEE
# MMMISSJEEE"""))

if __name__ == '__main__':
    unittest.main()
