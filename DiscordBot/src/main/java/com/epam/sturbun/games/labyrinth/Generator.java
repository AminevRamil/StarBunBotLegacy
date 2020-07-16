package com.epam.sturbun.games.labyrinth;


import java.util.Stack;

public class Generator{

    public static long build(Maze maze) {
        //System.out.println("Начало генерации лабиринта");
        long start = System.currentTimeMillis();
        Cell currentCell = maze.getCellAt(0, 0);
        Cell startingCell = maze.getCellAt(0, 0);
        Stack<Cell> stack = new Stack<>();

        do {
            currentCell.visited = true;
            if (currentCell.isThereUnvisitedNeighborsG() != 0) {
                stack.push(currentCell);
                Cell randNeighbor = currentCell.getUnvisitedNeighborG();
                currentCell.makePass(randNeighbor);
                //randNeighbor.visited = true;
                currentCell = randNeighbor;
            } else if (!stack.empty()) {
                currentCell = stack.peek();
                stack.pop();
            }
        }while (currentCell != startingCell);

        long end = System.currentTimeMillis();
        maze.makeAllUnvisited();
        //System.out.println("Лабиринт сгенерирован");
        return end - start;
    }
}


