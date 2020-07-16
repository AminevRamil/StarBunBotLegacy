package com.epam.sturbun.games.labyrinth;


import lombok.Getter;
import lombok.Setter;

public class Maze {
    @Getter
    @Setter
    private int size_h;
    @Getter
    @Setter
    private int size_w;
    private Cell maze[][];

    public Maze(int h, int w) {
        size_h = h;
        size_w = w;

        maze = new Cell[size_h][size_w];

        for (int i = 0; i < size_h; i++) {
            for (int j = 0; j < size_w; j++) {
                maze[i][j] = new Cell();
            }
        }

        for (int i = 0; i < size_h - 1; i++) {
            for (int j = 0; j < size_w - 1; j++) {
                maze[i][j].addToRight(maze[i][j + 1]);
                maze[i][j].addToDown(maze[i + 1][j]);
            }
        }

        for (int i = 0; i < size_h - 1; i++){
            maze[i][size_w - 1].addToDown(maze[i + 1][size_w - 1]);
        }

        for (int j = 0; j < size_w - 1; j++){
            maze[size_h - 1][j].addToRight(maze[size_h - 1][j + 1]);
        }

        System.out.println("Лабиринт инициализирован");
    }

    public Cell getCellAt(int i, int j){
        return maze[i][j];
    }

    void makeAllUnvisited() {
        for (int i = 0; i < size_h; i++) {
            for (int j = 0; j < size_w; j++) {
                maze[i][j].visited = false;
            }
        }
    }
}
