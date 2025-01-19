'''Advent of Code 2024 - Day 12, Part 1'''

from collections import defaultdict
from dataclasses import dataclass, field
from typing import Tuple, Set

dirY = [-1, 0, 1, 0]
dirX = [0, 1, 0, -1]

@dataclass(eq=True)
class Region:
    outside_edge_parts: Set[Tuple[int, Tuple[int, int]]] = field(default_factory=lambda: set())
    """This set contains a collection of outside edge parts.

    Returns:
        Set[Tuple[int, Tuple[int, int]]]: A collection of garden coordinates
        (Tuple[int, int]) and a direction from the respective coordinate that
        points outside of this region.
    """
    area: int = 0

    def calculate_no_of_edges(self) -> int:
        no_of_edge = 0
        #print(f"Outside Edge Parts: {self.outside_edge_parts}")
        while len(self.outside_edge_parts) > 0:
            edge_part = self.outside_edge_parts.pop()
            curr_edge = [edge_part]
            curr_edge_part = edge_part
            # run in one direction
            search_direction_index = (curr_edge_part[0] + 1) % len(dirX)
            while (next_edge_part := (curr_edge_part[0], (curr_edge_part[1][0] + dirX[search_direction_index], curr_edge_part[1][1] + dirY[search_direction_index]))) in self.outside_edge_parts:
                curr_edge.append(next_edge_part)
                self.outside_edge_parts.remove(next_edge_part)
                curr_edge_part = next_edge_part
            # run in other direction
            search_direction_index = (edge_part[0] - 1) % len(dirX)
            curr_edge_part = edge_part
            while (next_edge_part := (curr_edge_part[0], (curr_edge_part[1][0] + dirX[search_direction_index], curr_edge_part[1][1] + dirY[search_direction_index]))) in self.outside_edge_parts:
                curr_edge.append(next_edge_part)
                self.outside_edge_parts.remove(next_edge_part)
                curr_edge_part = next_edge_part
            print(f"Found edge: {curr_edge}")
            no_of_edge += 1
        return no_of_edge

    def calculate_cost(self) -> int:
        no_of_edges: int = self.calculate_no_of_edges()
        print(f"Cost: Edges {no_of_edges} x {self.area} Area")
        return no_of_edges * self.area

class Solution:
    def __init__(self):
        self.handledSpots = set()

    def solvePart2(self, input: str) -> int:
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
    
    def discover_new_region(self, start_point: Tuple[int, int]):
        print(f"Starting to discover Region {self.map[start_point[1]][start_point[0]]} from {start_point}")
        region = Region()
        self.visit_point(start_point, region)
        print(f"Discovered Region: {region}")
        return region

    def visit_point(self, point: Tuple[int, int], region: Region):
        if point in self.handledSpots:
            return
        self.handledSpots.add(point)
        region.area += 1
        x,y = point
        for direction, dir_x in enumerate(dirX):
            next_point = (x + dir_x, y + dirY[direction])
            if self.is_out_of_bounds(next_point)\
            or self.map[y + dirY[direction]][x + dir_x] != self.map[y][x]:
                region.outside_edge_parts.add((direction, point))
            else:
                self.visit_point(next_point, region)
            
    def is_out_of_bounds(self, point) -> bool:
        x,y = point
        return y < 0 or y >= len(self.map) or x < 0 or x >= len(self.map[y])

if __name__=='__main__':
    solver = Solution()
    with open("input", 'r') as input_part2:
        print(f"Solution Part 2: {solver.solvePart2(input_part2.read())}")
