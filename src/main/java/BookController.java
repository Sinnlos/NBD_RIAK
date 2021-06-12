import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class BookController {

    public static String sendRequest(String req) throws IOException {
        String command = req;
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader iny = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = iny.readLine()) != null) {
            response.append(output);
        }
        iny.close();

        return response.toString();
    }

    public static String getBook(String isbn, String bucketName, String address) throws IOException {
        String req = "curl "+address+"/buckets/"+bucketName+"/keys/"+isbn;
        String b = sendRequest(req);

        if(b.equals("not found")){
            return "Book with ISBN: "+ isbn +" was not found";
        }else{
            Book book = new Gson().fromJson(b, Book.class);

            return "------------\nYour book:\n\nTitle: "+ book.title+"\nAuthor: "+ book.author+"\nYear: "+ book.year
                    +"\nPages: "+ book.pages+"\nISBN: "+ book.isbn+"\n------------";
        }

    }

    public static String addBook(Book book, String bucketName, String address) throws IOException {
       String command = "curl –XPUT -H \"Content-Type: application/json\" -d " + new Gson().toJson(book) +"  "+address+"/buckets/"+bucketName+"/keys/"+book.isbn;
       sendRequest(command);
       return "Your book was added";
    }

    public static String updateBook(Book book, String bucketName, String address) throws IOException {
        String command = "curl –XPUT -H \"Content-Type: application/json\" -d " + new Gson().toJson(book) +"  "+address+"/buckets/"+bucketName+"/keys/"+book.isbn;
        sendRequest(command);
        return "Your book was updated";
    }

    public static String deleteBook(String isbn, String bucketName, String address) throws IOException {
        String req = "curl -XDELETE "+address+"/buckets/"+bucketName+"/keys/"+isbn;
        String b = sendRequest(req);

        return "Book with ISBN: "+isbn+" was deleted\n------------";
    }

}
