package BookStore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class PartThree {
	private static Book[][] bookArrays = new Book[8][]; // Array to store Book arrays from binary files
	static int selectedFileIndex = -1; // Index of the selected file (array)
    public static void do_part3() {
        // Deserialize each binary file and store the Book array
//        for (int i = 0; i < 8; i++) {
//            String fileName = "C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\";
//            switch (i) {
//                case 0:
//                    fileName += "Cartoons_Comics.csv.txt.ser";
//                    break;
//                case 1:
//                    fileName += "Hobbies_Collectibles.csv.txt.ser";
//                    break;
//                case 2:
//                    fileName += "Movies_TV_Books.csv.txt.ser";
//                    break;
//                case 3:
//                    fileName += "Music_Radio_Books.csv.txt.ser";
//                    break;
//                case 4:
//                    fileName += "Nostalgia_Eclectic_Books.csv.txt.ser";
//                    break;
//                case 5:
//                    fileName += "Old_Time_Radio_Books.csv.txt.ser";
//                    break;
//                case 6:
//                    fileName += "Sports_Sports_Memorabilia.csv.txt.ser";
//                    break;
//                case 7:
//                    fileName += "Trains_Planes_Automobiles.csv.txt.ser";
//                    break;
//            }
//
//            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
//                bookArrays[i] = (Book[]) inputStream.readObject();
//            } catch (IOException | ClassNotFoundException e) {
//                System.err.println("Error reading binary file: " + fileName);
//                e.printStackTrace();
//            }
//        }
        // Deserialize each binary file and store the Book array
        for (int i = 0; i < 8; i++) {
            String fileName = "C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\";
            switch (i) {
                case 0:
                    fileName += "Cartoons_Comics.csv.ser";
                    break;
                case 1:
                    fileName += "Hobbies_Collectibles.csv.ser";
                    break;
                case 2:
                    fileName += "Movies_TV_Books.csv.ser";
                    break;
                case 3:
                    fileName += "Music_Radio_Books.csv.ser";
                    break;
                case 4:
                    fileName += "Nostalgia_Eclectic_Books.csv.ser";
                    break;
                case 5:
                    fileName += "Old_Time_Radio_Books.csv.ser";
                    break;
                case 6:
                    fileName += "Sports_Sports_Memorabilia.csv.ser";
                    break;
                case 7:
                    fileName += "Trains_Planes_Automobiles.csv.ser";
                    break;
            }

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
                bookArrays[i] = (Book[]) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading binary file: " + fileName);
                e.printStackTrace();
            }
        }

  
        // Start interactive navigation
        navigateBooks();
    }

    private static void navigateBooks() {
        Scanner scanner = new Scanner(System.in);
//        int selectedFileIndex = -1; // Index of the selected file (array)
        int currentObjectIndex = 0; // Index of the current object in the selected array

        while (true) {
            System.out.println("----------------------------");
            System.out.println("Main Menu");
            System.out.println("----------------------------");
            System.out.println("v View the selected file");
            System.out.println("s Select a file to view");
            System.out.println("x Exit");
            System.out.print("Enter Your Choice: ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "v":
                    if (selectedFileIndex == -1) {
                        System.out.println("No file selected.");
                    } else {
                        viewSelectedFile(selectedFileIndex, currentObjectIndex);
                    }
                    break;
                case "s":
                    selectFile(scanner);
                    break;
                case "x":
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void selectFile(Scanner scanner) {
        System.out.println("------------------------------");
        System.out.println("File Sub-Menu");
        System.out.println("------------------------------");
        for (int i = 0; i < bookArrays.length; i++) {
            System.out.println((i + 1) + " " + getFileName(i) + " (" + (bookArrays[i] != null ? bookArrays[i].length : 0) + " records)");
        }
        System.out.println(bookArrays.length + 1 + " Exit");
        System.out.print("Enter Your Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice >= 1 && choice <= bookArrays.length) {
            // Select the file
            selectedFileIndex = choice - 1;
            System.out.println("File selected: " + getFileName(selectedFileIndex));
        } else if (choice == bookArrays.length + 1) {
            // Exit
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void viewSelectedFile(int selectedFileIndex, int currentObjectIndex) {
        Book[] selectedFile = bookArrays[selectedFileIndex];
        System.out.println("------------------------------");
        System.out.println("Viewing: " + getFileName(selectedFileIndex) + " (" + selectedFile.length + " records)");
        System.out.println("Enter the viewing commands (integer values): ");
        System.out.println("(Enter 0 to end viewing)");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                System.out.println("Viewing ended.");
                break;
            } else if (choice < 0) {
                // Move up
//            	System.out.println(Math.abs(choice));
//            	System.out.println(Math.abs(currentObjectIndex));
            	
                int newObjectIndex = currentObjectIndex - Math.abs(choice)+1; //changed to -1
//                System.out.println(newObjectIndex);
                if (newObjectIndex < 0) {
                    System.out.println("BOF has been reached.");
                    newObjectIndex = 0;
                }
                displayObjects(selectedFile, newObjectIndex, currentObjectIndex);
                currentObjectIndex = newObjectIndex;
            } else {
                // Move down
                int newObjectIndex = currentObjectIndex + choice - 1;
                if (newObjectIndex >= selectedFile.length) {
                    System.out.println("EOF has been reached.");
                    newObjectIndex = selectedFile.length - 1;
                }
                displayObjects(selectedFile, currentObjectIndex, newObjectIndex);
                currentObjectIndex = newObjectIndex;
            }
        }
    }

    private static void displayObjects(Book[] selectedFile, int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println(selectedFile[i]);
        }
    }

    private static String getFileName(int index) {
        switch (index) {
            case 0:
                return "Cartoons_Comics_Books.csv.ser";
            case 1:
                return "Hobbies_Collectibles_Books.csv.ser";
            case 2:
                return "Movies_TV.csv.ser";
            case 3:
                return "Music_Radio_Books.csv.ser";
            case 4:
                return "Nostalgia_Eclectic_Books.csv.ser";
            case 5:
                return "Old_Time_Radio.csv.ser";
            case 6:
                return "Sports_Sports_Memorabilia.csv.ser";
            case 7:
                return "Trains_Planes_Automobiles.csv.ser";
            default:
                return "";
        }
    }

}
