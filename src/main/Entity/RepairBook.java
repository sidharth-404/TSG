package main.Entity;

import java.time.LocalDate;

public class RepairBook {
    int bookId;
    String damageType;
    String repairStatus;
    LocalDate repairStartDate;
    
   public RepairBook()
    {

    }
    

public RepairBook(int bookId, String damageType, String repairStatus, LocalDate repairStartDate) {
    this.bookId = bookId;
    this.damageType = damageType;
    this.repairStatus = repairStatus;
    this.repairStartDate = repairStartDate;
}


public int getBookId() {
    return bookId;
}

public void setBookId(int bookId) {
    this.bookId = bookId;
}

public String getDamageType() {
    return damageType;
}

public void setDamageType(String damageType) {
    this.damageType = damageType;
}

public String getRepairStatus() {
    return repairStatus;
}

public void setRepairStatus(String repairStatus) {
    this.repairStatus = repairStatus;
}

public LocalDate getRepairStartDate() {
    return repairStartDate;
}

public void setRepairStartDate(LocalDate repairStartDate) {
    this.repairStartDate = repairStartDate;
}


@Override
public String toString() {
    return "RepairBook [bookId=" + bookId + ", damageType=" + damageType + ", repairStatus=" + repairStatus
            + ", repairStartDate=" + repairStartDate + "]";
}
   


    

}