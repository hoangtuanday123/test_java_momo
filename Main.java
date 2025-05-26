import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Main {
    private static PaymentService service;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String[] args){
        List<Bill> initialBills = List.of(
            new Bill(1, "ELECTRIC", 200000.0, LocalDate.parse("25/11/2020", formatter),"NOT_PAID", "EVN HCMC"),
            new Bill(2, "WATER", 175000.0, LocalDate.parse("25/10/2020", formatter),"NOT_PAID", "SAVACO HCMC"),
            new Bill(3, "INTERNET", 800000.0, LocalDate.parse("30/10/2020", formatter),"NOT_PAID", "VNPT")
        );
        Customer initialCustomer = new Customer(1, 200000.0);
        List<Payment> initialPayments = new ArrayList<>();
        service = new PaymentService(initialCustomer,initialBills,initialPayments);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("EXIT")) {
                service.exit();
                break;
            }
            else if(input.equals("LIST_BILL")){
                List<Bill> bills=service.List_bills();
                System.out.println("List of bills:");
                for (Bill bill : bills) {
                    System.out.println("Bill No: " + bill.getId() +
                            ", Type: " + bill.getType() +
                            ", Amount: " + bill.getAmount() +
                            ", Due Date: " + bill.getDueDate().format(formatter) +
                            ", State: " + bill.getState() +
                            ", Provider: " + bill.getProvider());
                }
            }
            else if(input.equals("DUE_DATE")){
                List<Bill>bills=service.due_date();
                System.out.println("List of bills:");
                for (Bill bill : bills) {
                    if (bill.getState().equals("NOT_PAID")) {
                        System.out.println("Bill No: " + bill.getId() +
                            ", Type: " + bill.getType() +
                            ", Amount: " + bill.getAmount() +
                            ", Due Date: " + bill.getDueDate().format(formatter) +
                            ", State: " + bill.getState() +
                            ", Provider: " + bill.getProvider());
                    }
        }
            }
            else if(input.equals("LIST_PAYMENT")){
                List<Payment>payments=service.List_payment();
                System.out.println("List of payments:");
                for (Payment payment : payments) {
                    System.out.println("Payment No: " + payment.getId() +
                            ", Amount: " + payment.getAmount() +
                            ", Payment Date: " + payment.getPaymentDate().format(formatter) +
                            ", State: " + payment.getState() +
                            ", Bill No: " + payment.getBillNo());
                }
            }
            else{
                String[] parts = input.split("\\s+"); 
                String command = parts[0].toUpperCase();
                switch (command) {
                    case "CASH_IN":
                        Double money = Double.parseDouble(parts[1]);
                        service.addFund(money);
                        break;
                    case "PAY":
                        Integer[] ids = new Integer[parts.length - 1];;
                        for (int i = 1; i < parts.length; i++) {
                            ids[i - 1] = Integer.parseInt(parts[i]);
                        }
                        service.pay_bills(ids);
                        break;
                    case "SEARCH_BILL_BY_PROVIDER":
                        Bill bill=service.Search_provider(parts[1]);
                        System.out.println("Bill No: " + bill.getId() +
                            ", Type: " + bill.getType() +
                            ", Amount: " + bill.getAmount() +
                            ", Due Date: " + bill.getDueDate().format(formatter) +
                            ", State: " + bill.getState() +
                            ", Provider: " + bill.getProvider());
                        break;
                    case "SCHEDULE":
                        ScheduledPayment scheduled= service.schedulePayment(Integer.parseInt(parts[1]), LocalDate.parse(parts[2], formatter));
                        System.out.println("Payment for bill id " + scheduled.getBillId() + "  is scheduled on " + scheduled.getScheduledDate().format(formatter));
                        break;
                    default:
                    throw new AssertionError();
                }
            }
        }

    }
}
