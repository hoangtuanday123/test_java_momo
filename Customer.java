public class Customer{
    private Integer id;
    private Double balance;
    public Customer(Integer id,Double balance ){
        this.id=id;
        this.balance=balance;
    }
    public Integer getId() { return id; }
    public Double getBalance() { return balance; }
    public void IncreaseBalance(Double money){
        this.balance=this.balance+money;
    }
    public Boolean DecreaseBalance(Double money){
        if (money>balance){
            return false;
        }
        else{
            this.balance=this.balance-money;
            return true;
        }
        
    }
}