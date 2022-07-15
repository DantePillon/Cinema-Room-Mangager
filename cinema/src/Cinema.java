package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scan.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scan.nextInt();

        char[][] seatGrid = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seatGrid[i][j] = 'S';
            }
        }

        boolean controlFlag = true;
        while (controlFlag) {
            controlFlag = chooseAction(seatGrid);
        }

    }

    static boolean chooseAction(char[][] seatGrid) {
        Scanner scan = new Scanner(System.in);

        System.out.println(
                "\n1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");

        switch (scan.nextInt()) {
            case 0:
                return false; // Ends the program.
            case 1:
                printSeats(seatGrid);
                break;
            case 2:
                purchaseTicket(seatGrid);
                break;
            case 3:
                statistics(seatGrid);
        }
        return true;
    }

    static void printSeats(char[][] seatGrid) {
        System.out.print("\nCinema:\n ");
        for (int i = 0; i < seatGrid[1].length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < seatGrid.length; i++) {
            System.out.print((i + 1) + " ");
            for (char seat : seatGrid[i]) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }

    static void purchaseTicket(char[][] seatGrid) {
        Scanner scan = new Scanner(System.in);

        boolean controlFlag = true;
        while (controlFlag) {
            System.out.println("\nEnter a row number:");
            int row = scan.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNum = scan.nextInt();

            if (row > seatGrid.length || seatNum > seatGrid[0].length) {
                System.out.println("\nWrong input!");
            } else if (seatGrid[row - 1][seatNum - 1] == 'B') {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                System.out.println("\nTicket price: $" + ticketPrice(seatGrid.length, seatGrid[0].length, row) + "\n");
                seatGrid[row - 1][seatNum - 1] = 'B';
                controlFlag = false;
            }
        }
    }

    static int ticketPrice(int rows, int seatsPerRow, int row) {
        if ((rows * seatsPerRow >= 60) && (row > rows / 2)) {
            return 8;
        } else {
            return 10;
        }
    }

    static void statistics(char[][] seatGrid) {
        int ticketsPurchased = ticketsPurchased(seatGrid);
        System.out.printf("\nNumber of purchased tickets: " + ticketsPurchased + "%n" +
                        "Percentage: %.2f%%%n" +
                        "Current income: $" + income(seatGrid) + "%n" +
                        "Total income: $" + totalIncome(seatGrid.length, seatGrid[0].length) + "%n%n",
                computePercent(ticketsPurchased, seatGrid.length * seatGrid[0].length));
    }

    static int ticketsPurchased(char[][] seatGrid) {
        int count = 0;
        for (char[] row : seatGrid) {
            for (char seat : row) {
                if (seat == 'B') {
                    count++;
                }
            }
        }
        return count;
    }

    static double computePercent(int ticketsPurchased, int totalSeats) {
        return (double) 100 * ticketsPurchased / totalSeats;
    }

    static int income(char[][] seatGrid) {
        int count = 0;
        if (seatGrid.length * seatGrid[0].length < 60) {
            for (char[] row : seatGrid) {
                for (char seat : row) {
                    if (seat == 'B') {
                        count += 10;
                    }
                }
            }
        } else {
            int cutOff = seatGrid.length / 2;

            for (int i = 0; i <= cutOff - 1; i++) {
                for (char seat : seatGrid[i]) {
                    if (seat == 'B') {
                        count += 10;
                    }
                }
            }
            for (int i = cutOff; i < seatGrid.length; i++) {
                for (char seat : seatGrid[i]) {
                    if (seat == 'B') {
                        count += 8;
                    }
                }
            }
        }
        return count;
    }

    static int totalIncome(int rows, int seatsPerRow) {
        if (rows * seatsPerRow < 60) {
            return rows * seatsPerRow * 10;
        } else {
            return ((rows / 2) * 10 + ((rows + 1) / 2) * 8) * seatsPerRow;
            /*
            Here is an alternate way to do it with checking a condition (it eliminates some computation):
                return rows % 2 == 0 ?
                    rows * 9 * seatsPerRow :
                    (rows * 9 + 1) * seatsPerRow;
            This can all be done with some algebraic simplification,
            however it requires a nested condition check which I presume is slower.
            */
        }
    }
}
