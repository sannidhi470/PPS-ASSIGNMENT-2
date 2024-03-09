import java.util.Scanner;

/**
 * The Abacus class is used to provide a simple calculator functionality.
 * @author Priyanshu Adhikari
 */
public class Abacus {

    /**
     * Enum created to represent different arithmetic operations.
     */
    private enum Operation {
        add, sub, mult, div
    };
    
    /**
     * Used for the left operand of arithmetic operations.
     */
    private int leftOperand;
    /**
     *Used for the right operand of arithmetic operations.
     */
    private int rightOperand;
    /**
     * Scanner object to take input from the user.
     */
    static Scanner keyboard;
    /**
     * To store solution of the arithmetic operation.
     */
    private int solution;

    /**
     * Constructor of Abacus class which initializes the solution and scanner.
     */
    public Abacus() {
        solution = 0;
        keyboard = new Scanner(System.in);
    }

    /**
     * This method gets the left operand.
     * @return The left operand value.
     */
    public int getleftOperand() {
        return leftOperand;
    }

    /**
     * This method sets the left operand.
     * @param newLeftOperand The value to set as the left operand.
     */
    public void setLeftOperand(int newLeftOperand) {
        this.leftOperand = newLeftOperand;
    }

    /**
     * This method gets the right operand.
     * @return The right operand value.
     */
    public int getRightOperand() {
        return rightOperand;
    }

    /**
     * This method sets the right operand.
     * @param newRightOperand The value to set as the right operand.
     */
    public void setRightOperand(int newRightOperand) {
        this.rightOperand = newRightOperand;
    }

    /**
     * This method sets the solution.
     * @param newSolution The value to set as the solution.
     */
    public void setSolution(int newSolution) {
        solution = newSolution;
    }

    /**
     * This method gets the solution.
     * @return The solution value.
     */
    public int getSolution() {
        return solution;
    }

    /**
     * This method performs addition operation and sets the solution.
     * @param leftOperand The left operand.
     * @param rightOperand The right operand.
     */
    public void addition(int leftOperand, int rightOperand) {
        setSolution(leftOperand + rightOperand);
    }

    /**
     * This method performs subtraction operation and sets the solution.
     * @param leftOperand The left operand.
     * @param rightOperand The right operand.
     */
    public void subtraction(int leftOperand, int rightOperand) {
        setSolution(leftOperand - rightOperand);
    }

    /**
     * This method performs multiplication operation and sets the solution.
     * @param leftOperand The left operand.
     * @param rightOperand The right operand.
     */
    public void multiplication(int leftOperand, int rightOperand) {
        setSolution(leftOperand * rightOperand);
    }

    /**
     * This method performs division operation and sets the solution.
     * @param leftOperand The left operand.
     * @param rightOperand The right operand.
     */
    public void division(int leftOperand, int rightOperand) {
        setSolution(leftOperand / rightOperand);
    }

    /**
     * This is the main method to run the Abacus program.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Abacus abacus = new Abacus();
        char another = 'n';
        System.out.println("initial value: ");
        abacus.setLeftOperand(Integer.parseInt(keyboard.next()));
        do {
            System.out.println("What operation? ('add', 'sub', 'mult', 'div')");
            Operation op = Operation.valueOf(keyboard.next());
            System.out.println("operand: ");
            abacus.setRightOperand(keyboard.nextInt());
            switch (op) {
                case add:
                    abacus.addition(abacus.getleftOperand(), abacus.getRightOperand());
                    break;
                case sub:
                    abacus.subtraction(abacus.getleftOperand(), abacus.getRightOperand());
                    break;
                case mult:
                    abacus.multiplication(abacus.getleftOperand(), abacus.getRightOperand());
                    break;
                case div:
                    abacus.division(abacus.getleftOperand(), abacus.getRightOperand());
                    break;
            }
            System.out.println("solution =" + abacus.solution);
            abacus.setLeftOperand(abacus.solution);
            System.out.print("continue (y/n)?");
            another = keyboard.next().charAt(0);
        } while (another == 'y');
    }
}
