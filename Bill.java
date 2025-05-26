import java.time.LocalDate;

public class Bill{
    private Integer BillNo;
    private String type;
    private Double amount;
    private LocalDate dueDate;
    private String state;
    private String provider;

    public Bill(Integer BillNo, String type, Double amount, LocalDate dueDate,String state, String provider) {
        this.BillNo = BillNo;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public Integer getId() { return BillNo; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDate getDueDate() { return dueDate; }
    public String getState() { return state; }
    public String getProvider() { return provider; }

    public boolean isPaid() { return state == "PAID"; }

    public void markAsPaid() {
        this.state = "PAID";
    }
}