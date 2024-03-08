package main.Entity;

public class RepairDetails {
    int bookId;
    String bookName;
    String damage;
    String repairStatus;
    public RepairDetails(){

    }
    public RepairDetails(int bookId, String bookName, String damage, String repairStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.damage = damage;
        this.repairStatus = repairStatus;
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
    public String getDamage() {
        return damage;
    }
    public void setDamage(String damage) {
        this.damage = damage;
    }
    public String getRepairStatus() {
        return repairStatus;
    }
    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }


}
