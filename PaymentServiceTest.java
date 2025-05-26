import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PaymentServiceTest {
    private static PaymentService service;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void setup() {
       List<Bill> initialBills = List.of(
            new Bill(1, "ELECTRIC", 200000.0, LocalDate.parse("25/11/2020", formatter),"NOT_PAID", "EVN HCMC"),
            new Bill(2, "WATER", 175000.0, LocalDate.parse("25/10/2020", formatter),"NOT_PAID", "SAVACO HCMC"),
            new Bill(3, "INTERNET", 800000.0, LocalDate.parse("30/10/2020", formatter),"NOT_PAID", "VNPT")
        );
        Customer initialCustomer = new Customer(1, 200000.0);
        List<Payment> initialPayments = new ArrayList<>();
        service = new PaymentService(initialCustomer,initialBills,initialPayments);
    }
 
    public static void main(String[] args) {
        setup();
        boolean allMatch = true;
        service.addFund(100000.0);
        if(service.getCustomer().getBalance() == 300000.0) {
            System.out.println("Test addFund passed");
        } else {
            System.out.println("Test addFund failed");
        }
        List<Bill> initialBills = List.of(
            new Bill(1, "ELECTRIC", 200000.0, LocalDate.parse("25/11/2020", formatter),"NOT_PAID", "EVN HCMC"),
            new Bill(2, "WATER", 175000.0, LocalDate.parse("25/10/2020", formatter),"NOT_PAID", "SAVACO HCMC"),
            new Bill(3, "INTERNET", 800000.0, LocalDate.parse("30/10/2020", formatter),"NOT_PAID", "VNPT")
        );
        List<Bill> expectBills = new ArrayList<>(initialBills);
        expectBills.sort(Comparator.comparing(Bill::getDueDate));
        List<Bill> bills=service.List_bills();
        for (int i = 0; i < bills.size(); i++){
            Bill actual = bills.get(i);
            Bill expected = expectBills.get(i);
            if (actual.getId() != expected.getId()
            || !actual.getType().equals(expected.getType())
            || Math.abs(actual.getAmount() - expected.getAmount()) > 0.0001
            || !actual.getDueDate().equals(expected.getDueDate())
            || !actual.getState().equals(expected.getState())
            || !actual.getProvider().equals(expected.getProvider())) {
            
            System.out.println("❌ Test failed at index " + i + ":");
            System.out.println("Expected: " + expected.getId() + ", " + expected.getType() + ", " + expected.getAmount() + ", " + expected.getDueDate() + ", " + expected.getState() + ", " + expected.getProvider());
            System.out.println("Actual  : " + actual.getId() + ", " + actual.getType() + ", " + actual.getAmount() + ", " + actual.getDueDate() + ", " + actual.getState() + ", " + actual.getProvider());
            allMatch = false;
        }
        }
        service.pay_bills(new Integer[]{1});
        for (Bill bill : service.getBills()) {
            if (bill.getId() == 1) {
                if (bill.getState().equals("PAID")) {
                    System.out.println("Test pay_bills passed for Bill ID 1");
                } else {
                    System.out.println("Test pay_bills failed for Bill ID 1");
                    allMatch = false;
                }
            }
        }
        service.pay_bills(new Integer[]{2,3});
        for (Bill bill : service.getBills()) {
            if (bill.getId() == 2) {
                if (bill.getState().equals("NOT_PAID")) {
                    System.out.println("Test pay_bills passed for Bill ID 2");
                } else {
                    System.out.println("Test pay_bills failed for Bill ID 2");
                    allMatch = false;
                }
            }
            if (bill.getId() == 3) {
                if (bill.getState().equals("NOT_PAID")) {
                    System.out.println("Test pay_bills passed for Bill ID 3");
                } else {
                    System.out.println("Test pay_bills failed for Bill ID 3");
                    allMatch = false;
                }
            }
        }
        List<Bill> initialBillsDueDate = List.of(
            new Bill(1, "ELECTRIC", 200000.0, LocalDate.parse("25/11/2020", formatter),"PAID", "EVN HCMC"),
            new Bill(2, "WATER", 175000.0, LocalDate.parse("25/10/2020", formatter),"NOT_PAID", "SAVACO HCMC"),
            new Bill(3, "INTERNET", 800000.0, LocalDate.parse("30/10/2020", formatter),"NOT_PAID", "VNPT")
        );
        List<Bill> expectDueDateBills = new ArrayList<>(initialBillsDueDate);
        expectDueDateBills.sort(Comparator.comparing(Bill::getDueDate));
        List<Bill> billsDueDate=service.due_date();
        for (int i = 0; i < bills.size(); i++){
            Bill actual = billsDueDate.get(i);
            Bill expected = expectDueDateBills.get(i);
            if (actual.getId() != expected.getId()
            || !actual.getType().equals(expected.getType())
            || Math.abs(actual.getAmount() - expected.getAmount()) > 0.0001
            || !actual.getDueDate().equals(expected.getDueDate())
            || !actual.getState().equals(expected.getState())
            || !actual.getProvider().equals(expected.getProvider())) {
            
            System.out.println("❌ Test failed at index " + i + ":");
            System.out.println("Expected: " + expected.getId() + ", " + expected.getType() + ", " + expected.getAmount() + ", " + expected.getDueDate() + ", " + expected.getState() + ", " + expected.getProvider());
            System.out.println("Actual  : " + actual.getId() + ", " + actual.getType() + ", " + actual.getAmount() + ", " + actual.getDueDate() + ", " + actual.getState() + ", " + actual.getProvider());
            allMatch = false;
        }
        }
        LocalDate today = LocalDate.now();                
                            String dateString = today.format(formatter);
        Payment payment1 = new Payment(0, LocalDate.parse(dateString, formatter), "PROCESSED", 1, 200000.0);
        for(Payment payment:service.getPayments()){
            if(payment.getId()!=payment1.getId()||
            !payment.getPaymentDate().equals(payment1.getPaymentDate())||
            !payment.getState().equals(payment1.getState())||
            !payment.getBillNo().equals(payment1.getBillNo())||
            Math.abs(payment.getAmount() - payment1.getAmount()) > 0.0001){
                System.out.println("❌ Test failed for Payment ID "+ payment.getId());
                allMatch = false;
            } 
        }
        Bill bill_provider=new Bill(3, "INTERNET", 800000.0, LocalDate.parse("30/10/2020", formatter),"NOT_PAID", "VNPT");
        Bill actualProviderBill = service.Search_provider("VNPT");
        if (actualProviderBill.getId() != bill_provider.getId()
            || !actualProviderBill.getType().equals(bill_provider.getType())
            || Math.abs(actualProviderBill.getAmount() - bill_provider.getAmount()) > 0.0001
            || !actualProviderBill.getDueDate().equals(bill_provider.getDueDate())
            || !actualProviderBill.getState().equals(bill_provider.getState())
            || !actualProviderBill.getProvider().equals(bill_provider.getProvider())){
            System.out.println("❌ Test failed for Search_provider: Expected " + bill_provider.getId() + ", " + bill_provider.getType() + ", " + bill_provider.getAmount() + ", " + bill_provider.getDueDate() + ", " + bill_provider.getState() + ", " + bill_provider.getProvider());
            allMatch = false;
            }
        if (allMatch) {
            System.out.println("✅ Test passed: All bills match expected.");
        }
        System.exit(0);


    }
}
