import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class BookController {

    public static String sendRequest(String req, String body, String method) throws IOException {

        String inline = "";
        String respMessage = "";

        URL url = new URL(req);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        if(method == "GET"){
            respMessage = http.getResponseMessage();
            http.disconnect();

            if(http.getResponseCode() == 200){
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                return inline;
            }
            else{
                return respMessage;
            }
        }
        else if(method=="POST" || method=="PUT"){
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
            byte[] out = body.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            http.disconnect();

            return http.getResponseMessage();
        }
        else if(method=="DELETE"){
            http.setRequestMethod(method);

            http.disconnect();

            return http.getResponseMessage();
        }
        else{
            return "";
        }

    }

    public static String getBook(String isbn, String address) throws IOException {
        String req = address+isbn;
        String b = sendRequest(req, "", "GET");


        if(b.equals("Object Not Found")){
            return "Book with ISBN: "+ isbn +" was not found";
        }else{
            Book book = new Gson().fromJson(b, Book.class);

            return "------------\nYour book:\n\nTitle: "+ book.title+"\nAuthor: "+ book.author+"\nYear: "+ book.year
                    +"\nPages: "+ book.pages+"\nISBN: "+ book.isbn+"\n------------";
        }

    }

    public static String addBook(Book book, String address) throws IOException {
       String command = address+book.isbn;
       sendRequest(command, new Gson().toJson(book),"POST");
       return "Your book was added";
    }

    public static String updateBook(Book book, String address) throws IOException {
        String command = address+book.isbn;
        sendRequest(command, new Gson().toJson(book),"PUT");
        return "Your book was updated";
    }

    public static String deleteBook(String isbn, String address) throws IOException {
        String req = address+isbn;
        sendRequest(req,"", "DELETE");

        return "Book with ISBN: "+isbn+" was deleted\n------------";
    }

}
