package main.Entity;

import java.time.LocalDate;

public class Book {
    int bookId;
    String bookName;
    LocalDate pubDate;
    String authorId;
    String genId;

public Book()
{
    
}


public Book(int bookId, String bookName, LocalDate pubDate, String authorId, String genId) {
    this.bookId = bookId;
    this.bookName = bookName;
    this.pubDate = pubDate;
    this.authorId = authorId;
    this.genId = genId;
}


public int getBookId() {
    return bookId;
}

public void setBookId(int bookId) {
    this.bookId = bookId;
}

public String getBookName() {
    return bookName;
}

public void setBookName(String bookName) {
    this.bookName = bookName;
}

public LocalDate getPubDate() {
    return pubDate;
}

public void setPubDate(LocalDate pubDate) {
    this.pubDate = pubDate;
}

public String getAuthorId() {
    return authorId;
}

public void setAuthorId(String authorId) {
    this.authorId = authorId;
}

public String getGenId() {
    return genId;
}

public void setGenId(String genId) {
    this.genId = genId;
}
}
