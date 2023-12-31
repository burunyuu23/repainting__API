# -*- coding: utf-8 -*-
import random
import sys
import threading
from collections import deque

class Game:
    MAX_ROUNDS = 22
    FIELD_SIZE = 12

    STATUS = {
        "PLAYING": 0, 'LOSE': 1, 'WON': 2
    }
    COLORS = {
        0: '#a5260a',  # Бисмарк-фуриозо
        1: '#f36223',  # Морковный
        2: '#ff9218',  # Последний вздох Жако
        3: '#3caa3c',  # Цвет влюблённой жабы
        4: '#1fcecb',  # Цвет яиц странствующего дрозда
        5: '#7442c8'   # Пурпурное сердце
    }
    @staticmethod
    def set_color_by_id(color_id, hex_color):
        Game.COLORS[color_id] = hex_color

    class ColorEntry:
        def __init__(self, id, hex_color):
            self._id = id
            self._hex_color = hex_color

        @property
        def id(self):
            return self._id
        @property
        def hex_color(self):
            return self._hex_color

    @staticmethod
    def convert_colors():
        color_entries = []
        for id, hex_color in Game.COLORS.items():
            color_entry = Game.ColorEntry(id, hex_color)
            color_entries.append(color_entry)
        return color_entries

    def __init__(self):
        self.map = [[Cell() for i in range(Game.FIELD_SIZE)] for j in range(Game.FIELD_SIZE)]
        self.main_cell = self.map[0][0]
        self.main_cell.captured(self.main_cell.value)
        self.status = Game.STATUS["PLAYING"]
        self.round = 0
        self.colors_count = [0 for i in Game.COLORS]
        self.captured_count = 0

        self.check_map()

        self.best = 23
        self.best_text = '∞'

        threading.stack_size(2**26)
        sys.setrecursionlimit(10**6)

    def step(self, num):
        if not self.color_repeated(num) and self.status == Game.STATUS["PLAYING"]:
            self.map[0][0].change_value(num)

            self.colors_count = [0 for i in Game.COLORS]
            self.map = self.map_capture()
            self.round += 1
            # self.status = Game.STATUS["WON"]

            if self.check_map():
                self.status = Game.STATUS["WON"]

            if self.status == Game.STATUS["WON"]:
                if self.best >= self.round:
                    self.best = self.round
                    self.best_text = str(self.best)

            if self.status != Game.STATUS["WON"] and self.round == Game.MAX_ROUNDS:
                self.status = Game.STATUS["LOSE"]

    def color_repeated(self, color):
        return self.main_cell.value == color

    def restart(self):
        # self.map = [[Cell(color=2) for i in range(Game.FIELD_SIZE)] for j in range(Game.FIELD_SIZE)]
        # self.map[0][0] = Cell(color=1)

        self.map = [[Cell() for i in range(Game.FIELD_SIZE)] for j in range(Game.FIELD_SIZE)]

        self.main_cell = self.map[0][0]
        self.main_cell.captured(self.main_cell.value)
        self.status = Game.STATUS["PLAYING"]
        self.round = 0

    def map_capture(self):
        old_map = self.map
        reached_map = [[False for i in range(Game.FIELD_SIZE)] for i in range(Game.FIELD_SIZE)]
        reached_map[0][0] = False

        max_i = len(old_map)
        max_j = len(old_map[0])

        check_queue = deque()
        check_queue.append([0, 0])

        def check(m, k, target):
            return (old_map[m][k].is_captured is True or old_map[m][k].value == target) and reached_map[m][k] is False

        def check_brother(i, j, value):
            if check(i, j, value):
                old_map[i][j].captured(value)
                check_queue.append([i, j])

        def j_check(x, y, num):
            if y == 0:
                check_brother(x, y + 1, num)
            elif y == max_j - 1:
                check_brother(x, y - 1, num)
            else:
                check_brother(x, y + 1, num)
                check_brother(x, y - 1, num)

        def i_check(i, j):
            if old_map[i][j].is_captured is True and reached_map[i][j] is False:
                value = old_map[i][j].value
                reached_map[i][j] = True

                if i == 0:
                    check_brother(i + 1, j, value)
                elif i == max_i - 1:
                    check_brother(i - 1, j, value)
                else:
                    check_brother(i + 1, j, value)
                    check_brother(i - 1, j, value)

                j_check(i, j, value)

        while check_queue:
            element = check_queue.pop()
            i_check(element[0], element[1])

        return old_map

    def check_map(self):
        win = True
        self.captured_count = 0

        main_cell = self.map[0][0]
        for line in self.map:
            for cell in line:
                self.colors_count[cell.value] += 1
                if cell.value != main_cell.value:
                    win = False
                if cell.is_captured:
                    self.captured_count += 1

        return win

    def __repr__(self):
       return self.__str__()

    def __str__(self):
        return str(self.map)

class Cell:
    def __init__(self):
        self.value = random.randint(0, len(Game.COLORS) - 1)
        self.is_captured = False

    def captured(self, value):
        self.is_captured = True
        self.value = value

    def change_value(self, value):
        self.value = value

    def __repr__(self):
        return self.__str__()

    def __str__(self):
        return "{captured: " + str(self.is_captured) + "; value: " + str(Game.COLORS.get(self.value)) + "}"

    def __eq__(self, other):
        return self.value == other.value
