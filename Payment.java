import java.time.LocalDate;

public class Payment {
    private Integer id;
    private LocalDate paymentDate;
    private String state;
    private Integer BillNo;
    private Double amount;
    public Payment(Integer id,LocalDate paymentDate,String state,Integer BillNo,Double amount){
        this.id=id;
        this.paymentDate=paymentDate;
        this.state=state;
        this.BillNo=BillNo;
        this.amount=amount;
    }
    public Integer getId() {
        return id;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public String getState() {
        return state;
    }
    public Integer getBillNo() {
        return BillNo;
    }
    public Double getAmount() {
        return amount;
    }
}
