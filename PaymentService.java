import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PaymentService {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final List<Bill> bills = new ArrayList<>();
    private Customer customer ;
    static List<Payment> payments = new ArrayList<>();
    private static Integer paymentId = 0;
    public PaymentService(Customer customer,List<Bill> bills,List<Payment> payments) {
        this.payments.addAll(payments);
        this.bills.addAll(bills);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
    public List<Bill> getBills() {
        return bills;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addFund(Double Money){
        customer.IncreaseBalance(Money);
        System.out.println("Your available balance: "+customer.getBalance());
    }

    public List<Bill> List_bills() {
        bills.sort(Comparator.comparing(Bill::getDueDate));
        return bills;
        
    }

    public void pay_bills(Integer[] ids){
        for (int i = 0; i < ids.length; i++) {
            Integer id = ids[i];
            Integer check= 0;
            for (Bill bill : bills){
                if(id==bill.getId()){
                    if (bill.getState().equals("NOT_PAID")) {
                        Boolean check_balance=customer.DecreaseBalance(bill.getAmount());
                        if (check_balance){
                            bill.markAsPaid();
                            LocalDate today = LocalDate.now();                
                            String dateString = today.format(formatter);
                            payments.add(new Payment(paymentId,LocalDate.parse(dateString, formatter),"PROCESSED",bill.getId(),bill.getAmount()));
                            paymentId++;
                            System.out.println("Payment has been completed for Bill with " + bill.getId());
                        }
                        else{
                            System.out.println("Sorry! Not enough fund to proceed with payment.");
                        }
                        ++check;
                    }
                    else if (bill.getState().equals("PAID")) {
                        System.out.println("Bill No: " + bill.getId() + " has already been paid.");
                    }
                }
            }
            if(check==0){
                System.out.println("Sorry! Not found a bill with such id");
            }
        }
    }
    public  List<Payment> List_payment(){
        return payments;
    }
    public  Bill Search_provider(String provider){
        for (Bill bill : bills) {
            if(bill.getProvider().equalsIgnoreCase(provider)){
                return new Bill(bill.getId(), bill.getType(), bill.getAmount(), bill.getDueDate(), bill.getState(), bill.getProvider());

            }
        }
        return null;
    }
    public  List<Bill> due_date(){
        bills.sort(Comparator.comparing(Bill::getDueDate));
        return bills;
        
    }
    public static void exit() {
        System.out.println("Good bye!");
        System.exit(0);
    }
}
