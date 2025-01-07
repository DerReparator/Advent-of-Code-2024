from day12_2 import Solution

import unittest

class Solution2024Day12Part2Test(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(80, Solution().solvePart2("""
AAAA
BBCD
BBCC
EEEC
                                                    """))
        
class Solution2024Day12Part2EnclosedRegions(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(436, Solution().solvePart2("""
OOOOO
OXOXO
OOOOO
OXOXO
OOOOO
                                                    """))
        
class Solution2024Day12Part2EShaped(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(236, Solution().solvePart2("""
EEEEE
EXXXX
EEEEE
EXXXX
EEEEE
                                                    """))
        
class Solution2024Day12Part2NoMiddleCross(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(368, Solution().solvePart2("""
AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA
                                                    """))
        
class Solution2024Day12Part2LargerExample(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(1206, Solution().solvePart2("""
RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
                                                    """))

if __name__ == '__main__':
    unittest.main()