package BookStore;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 enum parameter {
    TITLE(0),
    AUTHORS(1),
    PRICE(2),
    ISBN(3),
    GENRE(4),
    YEAR(5);

    private final int index;

    parameter(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static parameter getByIndex(int index) {
        for (parameter param : parameter.values()) {
            if (param.getIndex() == index) {
                return param;
            }
        }
        throw new IllegalArgumentException("Invalid index");
    }
    
    public static String getFieldName(int index) {
        return getByIndex(index).name().toLowerCase();
    }
}
public class PartOne {
//	private static final int EXPECTED_FIELDS_COUNT = 6;
//	private static final String CSV_REGEX = ;
	//private static final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
	private static final String[] VALID_GENRES = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};

	   public static void do_part1() {
	        // Read input file names from Part1_input_file_names.txt
		   try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\part1_input_file_names.txt"))) { 
	            String line;
	            // Skip the first line which contains the count of files
	            reader.readLine();
	            while ((line = reader.readLine()) != null) {
	                processFile(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	   
	   private static void processFile(String fileName) {
	        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ADMIN\\Desktop\\BookStore\\Comp6481_W24_Assg2-Needed-Files\\"+fileName))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                try {
	                    validateRecord(line);
	                    // Write valid records to corresponding genre-based output files
	                    // Handle writing to output files here
	                    writeValidRecord(line);
	                } catch (TooManyFieldsException | TooFewFieldsException | MissingFieldException | UnknownGenreException|IllegalArgumentException e) {
	                    // Write the error message and record to syntax_error_file.txt
	                    // Handle writing to syntax_error_file.txt here
	                	writeErrorToFile(fileName, e.getMessage(), line);
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading file: " + fileName);
	            e.printStackTrace();
	        }
	    }
	   
	   private static void validateRecord(String record) throws TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownGenreException {
		    String[] fields = parseCSV(record);
		    if (fields.length < 6) {
		        throw new TooFewFieldsException("Too few fields");
		    }
		    
		    for (int i = 0; i < fields.length; i++) {
		    	
		        if (fields[i].isEmpty()) {
		        	if(i!=3)
			    	{
		            throw new MissingFieldException("Missing field " + parameter.getFieldName(i));
			    	}
		        }
		    	
		    }
		    
		    String genre = fields[4].trim();
		    if(Arrays.asList(VALID_GENRES).contains(genre)==false)
		    {
		    	throw new UnknownGenreException("Invalid genre: " + genre);
		    }
//		    if (!isValidGenre(genre)) {
//		        throw new UnknownGenreException("Invalid genre: " + genre);
//		    }
	    }
	   
	   private static String[] parseCSV(String line) throws TooManyFieldsException {
		   final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
		   Matcher m = CSV_PATTERN.matcher(line);
		    String[] fields = new String[6]; // Initialize array to store fields
		    int index = 0; // Index to track current field

		    while (m.find()) {
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

		        // Break loop if we have processed all expected fields
		        if (index >= 6) {
		            break;
		        }
		    }
		    if(index>6)
		    {
		    	throw new TooManyFieldsException("Too many fields");
		    }

		    return fields;
		   
		}

//		private static boolean isValidGenre(String genre) {
//		    return Arrays.asList(VALID_GENRES).contains(genre);
//		}


		private static void writeValidRecord(String record) throws TooManyFieldsException {
			  String[] fields = parseCSV(record);
			    String genre = fields[4].trim();
			    String outputFile ="";
			    // Determine the output file based on the genre
			    //String outputFile = getOutputFile(genre);
			    switch (genre) {
		        case "CCB":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Cartoons_Comics.csv";
		        	break;
		        case "HCB":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Hobbies_Collectibles.csv";
		        	break;
		        case "MTV":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Movies_TV_Books.csv";
		        	break;
		        case "MRB":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Music_Radio_Books.csv";
		        	break;
		        case "NEB":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Nostalgia_Eclectic_Books.csv";
		        	break;
		        case "OTR":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Old_Time_Radio_Books.csv";
		        	break;
		        case "SSM":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Sports_Sports_Memorabilia.csv";
		        	break;
		        case "TPA":
		        	outputFile= "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Trains_Planes_Automobiles.csv";
		        	break;
		        default:
		        	outputFile= "";
			    }
			    // Write the record to the appropriate output file
			    try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile,true))) {
			        writer.println(record);
			    } catch (IOException e) {
			        System.err.println("Error writing to output file: " + e.getMessage());
			        e.printStackTrace();
			    }
			
		}
//		private static String getOutputFile(String genre) {
//		    // Determine and return the output file based on the genre
////		    switch (genre) {
////		        case "CCB":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Cartoons_Comics.csv";
////		        case "HCB":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Hobbies_Collectibles.csv";
////		        case "MTV":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Movies_TV_Books.csv";
////		        case "MRB":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Music_Radio_Books.csv";
////		        case "NEB":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Nostalgia_Eclectic_Books.csv";
////		        case "OTR":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Old_Time_Radio_Books.csv";
////		        case "SSM":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Sports_Sports_Memorabilia.csv";
////		        case "TPA":
////		            return "C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\Trains_Planes_Automobiles.csv";
////		        default:
////		            return "";
//		    }
//		}
		private static void writeErrorToFile(String fileName, String errorMessage, String record) {
		    try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\\\Users\\\\ADMIN\\\\Desktop\\\\BookStore\\\\Comp6481_W24_Assg2-Needed-Files\\\\syntax_error_file.txt",true))) {
		        writer.println("Syntax error in file: " + fileName);
		        writer.println("====================");
		        writer.println("Error: " + errorMessage);
		        writer.println("Record: " + record);
		        writer.println();
		    } catch (IOException e) {
		        System.err.println("Error writing to syntax error file: " + e.getMessage());
		        e.printStackTrace();
		    }
		}
}

