import os
import pprint

map = []

with open(os.path.realpath(os.path.join(os.getcwd(), os.path.dirname(__file__), "./day06_part_2.input")), 'r') as f:
    map = [[c for c in line.strip()] for line in f.readlines()]

directions = [(0, -1), (1, 0), (0, 1), (-1, 0)] # UP, RIGHT, DOWN, LEFT

TOTAL_FIELDS = len(map) * len(map[0])
PROGRESS = 0

LOOPS_FOUND = 0

start_x, start_y = 0,0

pprint.pprint(map)

for y in range(len(map)):
    for x in range(len(map[0])):
        if map[y][x] == '^':
            start_x, start_y = x, y

for y in range(len(map)):
    for x in range(len(map[0])):
        PROGRESS += 1
        if map[y][x] == '#' or map[y][x] == '^':
            continue
        
        visited = set()
        dir = 0

        curr_x, curr_y = start_x, start_y
        while True:
            if not 0 <= curr_x + directions[dir][0] < len(map[0]) or not 0 <= curr_y + directions[dir][1] < len(map):
                #print("out of bounds")
                break # out of bounds
            visited.add((curr_x, curr_y, dir))
            while map[curr_y + directions[dir][1]][curr_x + directions[dir][0]] == '#'\
            or (curr_y + directions[dir][1], curr_x + directions[dir][0]) == (y,x):
                #print(f"turning right because [{curr_x + directions[dir][0]:3d},{curr_y + directions[dir][1]:3d}]")
                dir += 1 # turn right at obstable
                dir %= len(directions)
            curr_x, curr_y = curr_x + directions[dir][0], curr_y + directions[dir][1]
            if (curr_x, curr_y, dir) in visited:
                #print(f"LOOP FOUND @[{curr_x:3d}, {curr_y:3d}, dir={dir}] for Obstacle @[{x:3d}, {y:3d}]")
                LOOPS_FOUND += 1
                break
        if PROGRESS % (TOTAL_FIELDS // 100) == 0:
            print(f"{(PROGRESS * 100) // TOTAL_FIELDS:3d}% [{LOOPS_FOUND:4d}]")
