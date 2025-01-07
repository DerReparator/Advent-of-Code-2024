# Advent of Code 2024 - Day 12, Part 1

from collections import defaultdict
from dataclasses import dataclass

@dataclass
class Region:
    region_perim = defaultdict(int)
    region_area = defaultdict(int)

class Solution:
    def __init__(self):
        self.dirY = [-1, 0, 1, 0]
        self.dirX = [0, 1, 0, -1]
        self.handledSpots = {}

    def solvePart1(self, input: str) -> int:
        map = [[c for c in line.strip()] for line in input.strip().splitlines()]

        for y in range(len(map)):
            for x in range(len(map[x])):
                if (x,y) in self.handledSpots:
                    continue
                else:
                    complete_region: Region = discover_new_region((x,y))
                region_area[map[y][x]] += 1
                for dir in range(len(self.dirX)):
                    if y + self.dirY[dir] < 0 or y + self.dirY[dir] > len(map)\
                    or x + self.dirX[dir] < 0 or x + self.dirX[dir] > len(map[x])\
                    or map[y + self.dirY[dir]][x + self.dirX[dir]] != map[y][x]:
                        region_perim[map[y][x]] += 1

        total_price = 0
        for plant, area in region_area:
            total_price += area * region_perim
        return total_price
                
