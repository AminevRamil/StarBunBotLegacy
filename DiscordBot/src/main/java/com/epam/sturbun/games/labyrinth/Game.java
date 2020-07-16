package com.epam.sturbun.games.labyrinth;


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Game {
    Maze maze;
    @Setter
    @Getter
    int height;
    @Setter
    @Getter
    int width;
    List<Player> players;

    public Game() {
        players = new LinkedList<>();
    }

    void addPlayer(Player player) {
        players.add(player);
    }

    void start() {
        if (height <= 0 || width <= 0) {
            throw new RuntimeException("Заданы неверные размеры лабиринта");
        }
        maze = new Maze(height, width);
        Generator.build(maze);
    }
}
