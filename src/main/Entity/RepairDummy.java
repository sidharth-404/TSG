package main.Entity;

public class RepairDummy {
    int severityLevel;
    int price;
    
    public RepairDummy(){

    }
    public RepairDummy(int severityLevel, int price) {
        this.severityLevel = severityLevel;
        this.price = price;
    }
    public int getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
