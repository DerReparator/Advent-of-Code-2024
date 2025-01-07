from day12_1 import Solution

import unittest

class Solution2024Day12Part1Test(unittest.TestCase):
    def test_example1(self):
        self.assertEqual(140, Solution().solvePart1("""
AAAA
BBCD
BBCC
EEEC
                                                    """))

if __name__ == '__main__':
    unittest.main()