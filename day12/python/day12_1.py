# Advent of Code 2024 - Day 12, Part 1

from collections import defaultdict
from dataclasses import dataclass
from typing import Tuple

@dataclass(eq=True)
class Region:
    perimeter: int = 0
    area: int = 0

    def calculate_cost(self) -> int:
        return self.perimeter * self.area

class Solution:
    def __init__(self):
        self.dirY = [-1, 0, 1, 0]
        self.dirX = [0, 1, 0, -1]
        self.handledSpots = set()

    def solvePart1(self, input: str) -> int:
        self.map = [[c for c in line.strip()] for line in input.splitlines()]

        total_price = 0
        for y in range(len(self.map)):
            for x in range(len(self.map[y])):
                if (x,y) in self.handledSpots:
                    continue
                else:
                    complete_region: Region = self.discover_new_region((x,y))
                    total_price += complete_region.calculate_cost()
        return total_price
    
    def discover_new_region(self, startPoint: Tuple[int, int]):
        region = Region()
        self.visit_point(startPoint, region)
        return region

    def visit_point(self, point: Tuple[int, int], region: Region):
        if point in self.handledSpots:
            return
        self.handledSpots.add(point)
        region.area += 1
        x,y = point
        for dir in range(len(self.dirX)):
            next_point = (x + self.dirX[dir], y + self.dirY[dir])
            if self.is_out_of_bounds(next_point)\
            or self.map[y + self.dirY[dir]][x + self.dirX[dir]] != self.map[y][x]:
                region.perimeter += 1
            else:
                self.visit_point(next_point, region)
            
    def is_out_of_bounds(self, point) -> bool:
        x,y = point
        return y < 0 or y >= len(self.map) or x < 0 or x >= len(self.map[y])

if __name__=='__main__':
    solver = Solution()
    with open("input", 'r') as input_part1:
        print(f"Solution Part 1: {solver.solvePart1(input_part1.read())}")