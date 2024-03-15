package BookStore;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PartTwo {
	//private static final int MAX_BOOKS = 5000;
    private static final Book[] books = new Book[5000];
    private static int bookCount = 0;

    //private static final String CSV_REGEX = ;
    

    public static void do_part2() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\part2_input_file_names.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processFile(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\"+fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Book book = validateRecord(line);
                    if (book != null) {
                        books[bookCount++] = book;
                    }
                } catch (BadIsbn10Exception | BadIsbn13Exception | BadPriceException | BadYearException e) {
                    writeErrorToFile(fileName, e.getMessage(), line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            e.printStackTrace();
        }

        serializeBooks(fileName + ".ser");
    }

    private static Book validateRecord(String record) throws BadIsbn10Exception, BadIsbn13Exception, BadPriceException, BadYearException {
        String[] fields = parseCSV(record);

        // Validate fields and create Book object
        // Add your validation logic here
        // For example:
        String isbn = fields[3].trim();
        if(isbn.length()==10)
        {
        if (!isValidISBN10(isbn)) {
            throw new BadIsbn10Exception("Invalid ISBN-10: " + isbn);
        }
        }
        else if (isbn.length() == 13)
        {
        if(!isValidISBN13(isbn))
        {
        	throw new BadIsbn13Exception("Invalid ISBN-13: " + isbn);
        }
        }
        String price = fields[2].trim();
	    if (!isValidPrice(price)) {
	        throw new BadPriceException("Invalid price: " + price);
	    }

	    String year = fields[5].trim();
	    if (!isValidYear(year)) {
	        throw new BadYearException("Invalid year: " + year);
	    }

        // Create and return Book object
        return new Book(fields[0], fields[1], Double.parseDouble(fields[2]), isbn, fields[4], Integer.parseInt(fields[5]));
    }

    private static String[] parseCSV(String line) {
    	final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
        Matcher m = CSV_PATTERN.matcher(line);
        String[] fields = new String[6]; // Initialize array to store fields
        int index = 0; // Index to track current field

        while (m.find() && index < 6) {
            String field = m.group(1);
            if (field == null) {
                field = m.group(2);
            }

            // Remove surrounding quotes if present
            if (field.startsWith("\"") && field.endsWith("\"")) {
                field = field.substring(1, field.length() - 1);
            }

            // Trim the field and add it to the array
            fields[index++] = field.trim();
        }

        return fields;
    }

    private static void serializeBooks(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\"+fileName))) {
            outputStream.writeObject(books);
        } catch (IOException e) {
            System.err.println("Error serializing books to file: " + fileName);
            e.printStackTrace();
        }
    }
	private static boolean isValidPrice(String price) {
	    try {
	        double parsedPrice = Double.parseDouble(price);
	        return parsedPrice >= 0;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	private static boolean isValidYear(String year) {
	    try {
	        int parsedYear = Integer.parseInt(year);
	        return parsedYear >= 1995 && parsedYear <= 2024;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

//	private static boolean isValidISBN(String isbn) {
//	    isbn = isbn.replaceAll("-", "");
//	    if (isbn.length() == 10) {
//	        return isValidISBN10(isbn);
//	    } else if (isbn.length() == 13) {
//	        return isValidISBN13(isbn);
//	    }
//	    return false;
//	}

	private static boolean isValidISBN10(String isbn) {
		if (isbn.length()!=10) {
	        return false;
	    }
	    int sum = 0;
	    for (int i = 0; i < 10; i++) {
	    	int number=Character.getNumericValue(isbn.charAt(i));
	    	if(number<0||number>9)
	    	{
	    		return false;
	    	}
	        sum += (10 - i) * number;
	    }
//	    char lastChar = isbn.charAt(9);
//	    if (lastChar == 'X') {
//	        // Throw an error if the last character is 'X'
//	        throw new IllegalArgumentException("Invalid ISBN-10: Last character cannot be 'X'");
//	    } else {
//	        sum += Character.getNumericValue(lastChar);
//	    }
	    return sum % 11 == 0;
	}

	private static boolean isValidISBN13(String isbn) {
	    if (isbn.length()!=13) {
	        return false;
	    }
	    int sum = 0;
	    for (int i = 0; i < 12; i++) {
	        int digit = Character.getNumericValue(isbn.charAt(i));
	        sum += (i % 2 == 0) ? digit : digit * 3;
	    }
	    int checkDigit = Character.getNumericValue(isbn.charAt(12));
	    return (10 - (sum % 10)) % 10 == checkDigit;
	}
    private static void writeErrorToFile(String fileName, String errorMessage, String record) {
    	try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\semantic_error_file.txt", true))) {
            writer.println("Semantic error in file: " + fileName);
            writer.println("====================");
            writer.println("Error: " + errorMessage);
            writer.println("Record: " + record);
            writer.println();
        } catch (IOException e) {
            System.err.println("Error writing to semantic error file: " + e.getMessage());
            e.printStackTrace();
        }
    }

 
}
