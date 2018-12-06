import java.util.List;

public class Task {
    private Lines lines;
    private Integer[][] adjacencyMatrix;
    private Boolean[] deletedLines = null;

    public Task() {
    }

    public Task(Lines lines) {
        this.lines = lines;
    }

    public Lines getLines() {
        return lines;
    }

    public void setLines(Lines lines) {
        this.lines = lines;
    }

    private void printAdjacencyMatrix() {
        int count = lines.getLines().size();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public Boolean[] getDeletedLines() {
        return deletedLines;
    }

    public Integer solve() {
        fillAdjacencyMatrix();
        return solveWithGreedyAlgorithm();
    }

    private Integer solveWithGreedyAlgorithm() {
        int linesCount = lines.getLines().size();
        Integer result = linesCount;
        Integer maxGlobal;
        int maxIndex = 0;
        Integer[][] adjacencyMatrixCopy;

        deletedLines = new Boolean[linesCount];
        for (int i = 0; i < linesCount; i++) {
            deletedLines[i] = false;
        }

        adjacencyMatrixCopy = new Integer[linesCount][linesCount];
        for (int i = 0; i < linesCount; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, adjacencyMatrixCopy[i], 0, linesCount);
        }

        /*
           Применяем жадный алгоритм для решения задачи
           1) Находим вершину графа с max числом связей
           2) удаляем соседние вершины и ребра соседних вершин
           3) повторяем 1-2 до тех пор, пока у нас есть вершины с соседями

         */
        do {
            // find vertex with max edges count
            maxGlobal = 0;
            for (int i = 0; i < linesCount; i++) {
                Integer maxLocal = 0;
                for (int j = 0; j < linesCount; j++) {
                    if (adjacencyMatrixCopy[i][j].equals(1)) {
                        maxLocal++;
                    }
                }
                if(maxLocal > maxGlobal) {
                    maxGlobal = maxLocal;
                    maxIndex = i;
                }
            }
            // end find

            if (maxGlobal > 0) {
                for (int j = 0; j < linesCount; j++) {
                    if (adjacencyMatrixCopy[maxIndex][j].equals(1)) {
                        result--;
                        deletedLines[j] = true;
                        for (int k = 0; k < linesCount; k++) {
                            adjacencyMatrixCopy[j][k] = 0;
                            adjacencyMatrixCopy[k][j] = 0;
                        }
                    }
                }

            } // end if(maxGlobal > 0)

        } while (maxGlobal != 0);
        /* конец жадного алгоритма */

        return result;
    }

    private void fillAdjacencyMatrix() {
        int count = lines.getLines().size();
        adjacencyMatrix = new Integer[count][count];
        List<Line> linesList = lines.getLines();
        for (int i = 0; i < count; i++) {
            adjacencyMatrix[i][i] = 0;
            for (int j = i+1; j < count; j++) {
                if (linesList.get(i).checkIntersection(linesList.get(j))) {
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;
                } else {
                    adjacencyMatrix[i][j] = 0;
                    adjacencyMatrix[j][i] = 0;
                }
            }
        }
    }

}
