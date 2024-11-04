package AI;

import Main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gamePanel;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReach = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNode();
    }

    public void instantiateNode() {
        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            node[col][row] = new Node(col,row);

            col++;
            if(col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNode() {

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReach = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        resetNode();

        //set start node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            //check tile
            int tilenum = gamePanel.tileSet.mapTile[col][row];
            if(gamePanel.tileSet.tiles[tilenum].collision == true) {
                node[col][row].solid = true;
            }

            //set cost
            getCost(node[col][row]);

            col++;
            if(col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {
        //gCost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //hCost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        //fCost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while(!goalReach && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            //check currNode
            currentNode.checked = true;
            openList.remove(currentNode);

            //open the UpNode
            if (row - 1 >= 0) {
                openNode(node[col][row-1]);
            }
            // Open the left node
            if(col - 1 >= 0) {
                openNode(node[col-1][row]);
            }
            //Open the down node
            if (row + 1 < gamePanel.maxWorldRow)  {
                openNode(node[col][row+1]);
            }
            // Open the right node
            if (col + 1 < gamePanel.maxWorldCol) {
                openNode (node [col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++) {
                //check if this fCost is better
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;

                //check if f cost is equal, check gCost
                }else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            //no node in open list
            if(openList.isEmpty()) {
                break;
            }
            //after loop openList[bestBodeIndex] is the next step
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode) {
                goalReach = true;
                trackPath();
            }
            step++;
        }

        return goalReach;
    }
    public void openNode(Node node) {
        if(!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath() {
        Node current = goalNode;

        while(current != startNode) {
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
