import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Handle matrix A input
        System.out.print("Matrix A: ");
        String matrixACoordinate = sc.nextLine();

        String isCoordinateValid = handleCoordinateInput(matrixACoordinate); // validate matrix A input
        if (!(isCoordinateValid.equals("valid"))) {
            System.out.println(isCoordinateValid);
        } else {
            // retrieve row and column coordinate and convert them into integer
            String[] coordinates = matrixACoordinate.split(",");
            String rowString = coordinates[0];
            int rowA = Integer.parseInt(rowString);
            String colString = coordinates[1];
            int colA = Integer.parseInt(colString);

            if (rowA == 1) {  // in case we are dealing with one dimension matrix
                String matrixInput = sc.nextLine();
                String isMatrixValid = handleMatrixInput(matrixInput, colA); // validate matrix input
                if (!(isMatrixValid.equals("valid"))) {
                    System.out.println(isMatrixValid);
                } else {
                    String[] stringMatrix = matrixInput.split(" ");
                    int[] matrixA = matrixOneDimension(colA, stringMatrix); // create a one dimension matrix

                    // Handle matrix B input
                    System.out.print("Matrix B: ");
                    String matrixBCoordinate = sc.nextLine();

                    String isCoordinateValidB = handleCoordinateInput(matrixBCoordinate); // validate matrix input
                    if (!(isCoordinateValidB.equals("valid"))) {
                        System.out.println(isCoordinateValidB);
                    } else {
                        // retrieve row and column coordinate and convert them into integer
                        String[] coordinatesB = matrixBCoordinate.split(",");
                        String rowStringB = coordinatesB[0];
                        int rowB = Integer.parseInt(rowStringB);
                        String colStringB = coordinatesB[1];
                        int colB = Integer.parseInt(colStringB);

                        if (rowB == 1) { // when dealing with a one dimension array
                            String matrixInputB = sc.nextLine();
                            String isMatrixValidB = handleMatrixInput(matrixInputB, colB); // validate matrix
                            if (!(isMatrixValidB.equals("valid"))) {
                                System.out.println(isMatrixValidB);
                            } else {
                                String[] stringMatrixB = matrixInputB.split(" ");
                                int[] matrixB = matrixOneDimension(colB, stringMatrixB); // create a one dimension

                                // handle matrix multiplication
                                if (colA == rowB) {
                                    // ex: A[1,2] and B[1,1,2]
                                    multiplyMatrix(matrixA, matrixB, colB);
                                } else {
                                    System.out.printf("Matrices cannot be multiplied. Matrix_col_A (%d) != Matrix_row_B (%d)", colA, rowB);
                                }

                            }

                        } else { // in case we are dealing with a two dimension matrix
                            int[][] matrixB = matrixTwoDimension(rowB, colB); // create a 2D array

                            // block the flow in case matrixTwoDimension function returns [[0]]
                            // I used [[0]] to be returned when there is an error while creating 2D array
                            if (!(matrixB.length == 1 && matrixB[0].length == 1 && matrixB[0][0] == 0)) {
                                // handle matrix multiplication
                                if (colA == rowB) {
                                    // ex: A[1,1] and B[[0,1], [1,2]]
                                    multiplyMatrix(matrixA, matrixB, colA, colB);
                                } else {
                                    System.out.printf("Matrices cannot be multiplied. Matrix_col_A (%d) != Matrix_row_B (%d)", colA, rowB);
                                }
                            }
                        }
                    }
                }

            } else { // in case Matrix A is of 2D array
                int[][] matrixA = matrixTwoDimension(rowA, colA); // create a 2D array
                // block the flow in case matrixTwoDimension function returns [[0]]
                // I used [[0]] to be returned when there is an error while creating 2D array
                if (!(matrixA.length == 1 && matrixA[0].length == 1 && matrixA[0][0] == 0)) {

                    // Handle matrix B input
                    System.out.print("Matrix B: ");
                    String matrixBCoordinate = sc.nextLine();

                    String isCoordinateValidB = handleCoordinateInput(matrixBCoordinate); // validate matrix B input
                    if (!(isCoordinateValidB.equals("valid"))) {
                        System.out.println(isCoordinateValidB);
                    } else {
                        // retrieve row and column coordinate in Integer type
                        String[] coordinatesB = matrixBCoordinate.split(",");
                        String rowStringB = coordinatesB[0];
                        int rowB = Integer.parseInt(rowStringB);
                        String colStringB = coordinatesB[1];
                        int colB = Integer.parseInt(colStringB);

                        if (rowB == 1) { // when dealing with a one dimension array
                            String matrixInputB = sc.nextLine();
                            String isMatrixValidB = handleMatrixInput(matrixInputB, colB);// validate matrix input
                            if (!(isMatrixValidB.equals("valid"))) {
                                System.out.println(isMatrixValidB);
                            } else {
                                String[] stringMatrixB = matrixInputB.split(" ");
                                int[] matrixB = matrixOneDimension(colB, stringMatrixB); // create one dimension array

                                // handle matrix multiplication
                                if (colA == rowB) {
                                    //ex: A[[2],[0]] and B[1,2,0]
                                    multiplyMatrix(matrixA, matrixB, rowA, colB);
                                } else {
                                    System.out.printf("Matrices cannot be multiplied. Matrix_col_A (%d) != Matrix_row_B (%d)", colA, rowB);
                                }
                            }

                        } else {
                            int[][] matrixB = matrixTwoDimension(rowB, colB);
                            if (!(matrixB.length == 1 && matrixB[0].length == 1 && matrixB[0][0] == 0)) {

                                // handle matrix multiplication
                                if (colA == rowB) {
                                    // ex: A[[1,2], [0,-1]] and B[[-2,2], [3,1]]
                                    multiplyMatrix(matrixA, matrixB, rowA, colA, colB);
                                } else {
                                    System.out.printf("Matrices cannot be multiplied. Matrix_col_A (%d) != Matrix_row_B (%d)", colA, rowB);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // create a one dimension array
    static int[] matrixOneDimension(int col, String[] stringMatrix) {
        int[] matrix = new int[col];
        for (int i = 0; i < stringMatrix.length; i++) {
            String valueInString = stringMatrix[i];
            matrix[i] = Integer.parseInt(valueInString);
        }
        return matrix;
    }

    // create a 2D array
    static int[][] matrixTwoDimension(int row, int col) {
        Scanner sc = new Scanner(System.in);
        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            String matrixInput = sc.nextLine();
            // validate matrix input
            String isMatrixValid = handleMatrixInput(matrixInput, col);
            if (!(isMatrixValid.equals("valid"))) {
                System.out.println(isMatrixValid);
                // Once the error message is display return [[0]] for validation
                int[][] errorArray = new int[1][1];
                errorArray[0][0] = 0;
                return errorArray;
            } else {
                String[] stringMatrix = matrixInput.split(" ");
                for (int j = 0; j < col; j++) {
                    String valueInString = stringMatrix[j];
                    matrix[i][j] = Integer.parseInt(valueInString);
                }
            }
        }
        return matrix;
    }

    // checks if input coordinate is valid with x,y format
    static String handleCoordinateInput(String coordinates) {
        if (!(Pattern.matches("[0-9,]+", coordinates))) {
            return "Please use only numbers and comma to separate matrix coordinate";
        } else if (!(coordinates.length() == 3)) {
            return "Please the matrix coordinate is invalid!";
        } else {
            if (!(coordinates.contains(","))) {
                return "Please separate coordinates with a comma!";
            } else {
                return "valid";
            }
        }
    }

    // validates matrix values based on the matrix coordinate set
    static String handleMatrixInput(String values, int col) {
        if (!(Pattern.matches("[0-9 -]+", values))) {
            return "Please use only numbers and a space to separate matrix values";
        } else if (col == 1) {
            if (values.contains(" ")) {
                return "Remove space since the column is set to be equaled to one";
            } else {
                return "valid";
            }
        } else {
            if (!(values.contains(" "))) {
                return "Please separate values with a space!";
            } else {
                // handle matrix column mismatch with the matrix values set
                String[] coordinateValues = values.split(" ");
                if (coordinateValues.length != col) {
                    return "The number of matrix column should be " + col + " not " + coordinateValues.length;
                } else {
                    return "valid";
                }
            }
        }
    }

    // Multiple when A[][] and B[][] means, they all 2D array.
    static void multiplyMatrix(int[][] matrixA, int[][] matrixB, int rowA, int colA, int colB) {
        /*
        The formula logic: A(n,p) x B(p,k) = C(n,k)
        in this use-case both matrices are of 2D array,therefore n == rowA || p == colA || k == colB
        */

        int[][] result = new int[rowA][colB];

        for (int n = 0; n < rowA; n++) {
            for (int k = 0; k < colB; k++) {
                int sum = 0;
                for (int p = 0; p < colA; p++) {
                    sum += matrixA[n][p] * matrixB[p][k];
                }
                result[n][k] = sum;
            }
        }

        // display result array matrix
        System.out.println("Matrix C:");
        for (int i = 0; i < result.length; i++) {
            System.out.print("|");
            for (int j = 0; j < result[i].length; j++) {
                System.out.printf(" %d ", result[i][j]);
            }
            System.out.println("|");
        }
    }

    // Multiply when A[] and B[] means, they are both of one dimension array.
    static void multiplyMatrix(int[] matrixA, int[] matrixB, int colB) {
        /* The formula logic: A(n,p) x B(p,k) = C(n,k)
        since the matrix A has one row and one column, just multiply matrixA[0] by every column in matrix B
        and the result matrix will be of row 1 and col is the matrix B column number */

        int[] result = new int[colB];

        for (int k = 0; k < colB; k++) {
            result[k] = matrixA[0] * matrixB[k];
        }

        //display result matrix
        System.out.println("Matrix C: ");
        System.out.print("|");
        for (int value : result) {
            System.out.printf(" %d ", value);
        }
        System.out.println("|");
    }

    // Multiply when A[][] and B[] means, Matrix A is 2D array and Matrix B is one dimension array.
    static void multiplyMatrix(int[][] matrixA, int[] matrixB, int rowA, int colB) {
        /* The formula logic: A(n,p) x B(p,k) = C(n,k)
         in this use-case the matrix A has column number of 1, therefore each row of matrix A
        ...is multiplying the entire column of matrix B */

        int[][] result = new int[rowA][colB];

        for (int n = 0; n < rowA; n++) {
            for (int k = 0; k < colB; k++) {
                result[n][k] += matrixA[n][0] * matrixB[k];
            }
        }

        // display result array matrix
        System.out.println("Matrix C:");
        for (int i = 0; i < result.length; i++) {
            System.out.print("|");
            for (int j = 0; j < result[i].length; j++) {
                System.out.printf(" %d ", result[i][j]);
            }
            System.out.println("|");
        }
    }

    // Multiply when A[] and B[][] means, Matrix A is one dimension array and Matrix B is 2D array.
    static void multiplyMatrix(int[] matrixA, int[][] matrixB, int colA, int colB) {
        /* The formula logic: A(n,p) x B(p,k) = C(n,k)
        in this case matrix A row equals one so the result matrix will be of row 1 */

        int[] result = new int[colB];

        for (int k = 0; k < colB; k++) {
            int sum = 0;
            for (int p = 0; p < colA; p++) {
                sum += matrixA[p] * matrixB[p][k];
            }
            result[k] = sum;
        }

        //display result matrix
        System.out.println("Matrix C: ");
        System.out.print("|");
        for (int value : result) {
            System.out.printf(" %d ", value);
        }
        System.out.println("|");
    }
}