import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main( String[] args ) throws IOException, ExecutionException, InterruptedException {

        String address = "http://localhost:8098";
        String bucketName = "s14014";
        Book book1 = new Book("Perswazje","Jane_Austen",2020, 254, "978-83-813-9627-1");

        //addBook
        String resp1 = BookController.addBook(book1, bucketName, address);
        System.out.println(resp1);


        //getBook
        System.out.println(BookController.getBook(book1.isbn, bucketName, address));

        //updateBook
        book1.setPages(400);
        String resp2 = BookController.updateBook(book1, bucketName, address);
        System.out.println(resp2);

        //getBook
        System.out.println(BookController.getBook(book1.isbn, bucketName, address));

        //deleteBook
        System.out.println(BookController.deleteBook(book1.isbn, bucketName, address));

        //getBook
        System.out.println(BookController.getBook(book1.isbn, bucketName, address));


    }
}
