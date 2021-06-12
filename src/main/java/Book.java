public class Book{


    String title;
    String author;
    int year;
    int pages;
    String isbn;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

        public Book(String title,String author,int year,int pages,String isbn){
            this.title = title;
            this.author = author;
            this.year = year;
            this.pages = pages;
            this.isbn = isbn;
        }

        public Book(){

        }




}
