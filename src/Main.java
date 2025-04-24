import java.util.*;


// Main class to run the program
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ApartmentManager apartmentManager = new ApartmentManager();
    private static final CustomerManager customerManager = new CustomerManager();
    private static final ContractManager contractManager = new ContractManager();

    public static void main(String[] args) {
        initializeSystemData();

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    makeNewContract();
                    break;
                case 2:
                    manageContracts();
                    break;
                case 3:
                    displayTotalUtilityUsage();
                    break;
                case 4:
                    displayOccupiedApartments();
                    break;
                case 5:
                    displayVacantApartments();
                    break;
                case 6:
                    searchCustomer();
                    break;
                case 7:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeSystemData() {
        // Initialize apartments
        apartmentManager.initializeApartments();

        // Initialize price chart
        PriceChart.initializePrices();
    }

    private static void displayMainMenu() {
        System.out.println("\n===== LeadCity Apartment Management System =====");
        System.out.println("1. Make a new contract");
        System.out.println("2. Manage contracts");
        System.out.println("3. See total utility usage");
        System.out.println("4. See occupied apartments");
        System.out.println("5. See vacant apartments");
        System.out.println("6. Search for a customer");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void makeNewContract() {
        System.out.println("\n===== Create New Contract =====");

        // Select contract type
        System.out.println("Select contract type:");
        System.out.println("1. Long-term (1-year minimum)");
        System.out.println("2. Mid-term (1-month minimum)");
        System.out.println("3. Short-term (1-day minimum)");
        System.out.print("Enter choice: ");
        int contractTypeChoice = scanner.nextInt();
        scanner.nextLine();

        String contractType;
        switch (contractTypeChoice) {
            case 1: contractType = "L"; break;
            case 2: contractType = "M"; break;
            case 3: contractType = "S"; break;
            default:
                System.out.println("Invalid choice. Defaulting to Short-term.");
                contractType = "S";
        }

        // Customer handling
        System.out.print("Enter customer NIC: ");
        String nic = scanner.nextLine();

        Customer customer = customerManager.getCustomer(nic);
        if (customer == null) {
            System.out.println("New customer detected. Please enter details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Vehicle License Plate: ");
            String licensePlate = scanner.nextLine();
            System.out.print("Contact Number: ");
            String contactNumber = scanner.nextLine();

            customer = new Customer(nic, name, address, licensePlate, contactNumber);
            customerManager.addCustomer(customer);
        }

        // Display vacant apartments
        displayVacantApartments();
        List<Apartment> selectedApartments = new ArrayList<>();

        while (true) {
            System.out.print("Enter apartment ID to add (or 'done' to finish): ");
            String aptId = scanner.nextLine();

            if (aptId.equalsIgnoreCase("done")) {
                if (selectedApartments.isEmpty()) {
                    System.out.println("You must select at least one apartment.");
                    continue;
                }
                break;
            }

            Apartment apartment = apartmentManager.getApartment(aptId);
            if (apartment == null || !apartment.isVacant()) {
                System.out.println("Invalid or occupied apartment ID. Try again.");
                continue;
            }

            selectedApartments.add(apartment);
            System.out.println("Apartment " + aptId + " added to contract.");
        }

        // Set contract duration
        System.out.print("Enter contract duration: ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        // Create contract
        Contract contract = contractManager.createContract(contractType, customer, selectedApartments, duration);

        System.out.println("\nContract created successfully!");
        System.out.println("Contract ID: " + contract.getContractId());
    }

    private static void manageContracts() {
        System.out.println("\n===== Manage Contracts =====");
        System.out.println("1. View all contracts");
        System.out.println("2. View ended contracts");
        System.out.println("3. Search contract by ID");
        System.out.println("4. Search contract by customer NIC");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                displayAllContracts();
                break;
            case 2:
                displayEndedContracts();
                break;
            case 3:
                searchContractById();
                break;
            case 4:
                searchContractByCustomer();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void displayAllContracts() {
        List<Contract> contracts = contractManager.getAllContracts();
        System.out.println("\n===== All Contracts =====");
        System.out.println("Contract ID | Customer ID | Apartment IDs | Checked-in | Checkout | Status");
        System.out.println("------------------------------------------------------------");

        for (Contract contract : contracts) {
            System.out.printf("%s | %s | %s | %s | %s | %s%n",
                    contract.getContractId(),
                    contract.getCustomer().getNic(),
                    getApartmentIdsString(contract.getApartments()),
                   // contract.getStartDate(),
                   // contract.getEndDate(),
                    contract.getStatus());
        }
        System.out.println("Total: " + contracts.size());
    }

    private static void displayEndedContracts() {
        List<Contract> contracts = contractManager.getEndedContracts();
        System.out.println("\n===== Ended Contracts =====");
        System.out.println("Contract ID | Customer ID | Apartment IDs | Checked-in | Checkout | Status");
        System.out.println("------------------------------------------------------------");

        for (Contract contract : contracts) {
            System.out.printf("%s | %s | %s | %s | %s | %s%n",
                    contract.getContractId(),
                    contract.getCustomer().getNic(),
                    getApartmentIdsString(contract.getApartments()),
                    //contract.getStartDate(),
                   // contract.getEndDate(),
                    contract.getStatus());
        }
        System.out.println("Total: " + contracts.size());
    }

    private static void searchContractById() {
        System.out.print("Enter contract ID to search: ");
        String contractId = scanner.nextLine();

        Contract contract = contractManager.getContract(contractId);
        if (contract == null) {
            System.out.println("Contract not found.");
            return;
        }

        displayContractDetails(contract);

        // Additional options for managing the contract
        System.out.println("\n1. View invoice");
        System.out.println("2. Early terminate contract");
        System.out.println("3. Close contract");
        System.out.print("Enter choice (or any other key to go back): ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                displayInvoice(contract);
                break;
            case "2":
                if (contract.getStatus().equals("OPENED")) {
                    System.out.print("Are you sure you want to terminate this contract? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        contractManager.terminateContract(contract);
                        System.out.println("Contract terminated successfully.");
                    }
                } else {
                    System.out.println("Only OPENED contracts can be terminated.");
                }
                break;
            case "3":
                if (contract.getStatus().equals("ENDED")) {
                    contractManager.closeContract(contract);
                    System.out.println("Contract closed successfully.");
                } else {
                    System.out.println("Only ENDED contracts can be closed.");
                }
                break;
        }
    }

    private static void searchContractByCustomer() {
        System.out.print("Enter customer NIC to search: ");
        String nic = scanner.nextLine();

        List<Contract> contracts = contractManager.getContractsByCustomer(nic);
        if (contracts.isEmpty()) {
            System.out.println("No contracts found for this customer.");
            return;
        }

        System.out.println("\n===== Contracts for Customer: " + nic + " =====");
        System.out.println("Contract ID | Apartment IDs | Checked-in | Checkout | Status");
        System.out.println("------------------------------------------------------------");

        for (Contract contract : contracts) {
            System.out.printf("%s | %s | %s | %s | %s%n",
                    contract.getContractId(),
                    getApartmentIdsString(contract.getApartments()),
                   // contract.getStartDate(),
                   // contract.getEndDate(),
                    contract.getStatus());
        }
    }

    private static void displayContractDetails(Contract contract) {
        System.out.println("\n===== Contract Details =====");
        System.out.println("Contract ID: " + contract.getContractId());
        System.out.println("Customer NIC: " + contract.getCustomer().getNic());
        System.out.println("Customer Name: " + contract.getCustomer().getName());
        System.out.println("Contract Type: " + (contract.getContractType().equals("L") ? "Long-term" :
                contract.getContractType().equals("M") ? "Mid-term" : "Short-term"));
        System.out.println("Start Date: qq");// + contract.getStartDate());
        System.out.println("End Date: qq");// + contract.getEndDate());
        System.out.println("Status: " + contract.getStatus());

        System.out.println("\nApartments:");
        for (Apartment apartment : contract.getApartments()) {
            System.out.println("- " + apartment.getId() + " (" + apartment.getType() + ")");
        }
    }

    private static void displayInvoice(Contract contract) {
        System.out.println("\n===== Invoice =====");
        System.out.println("Customer NIC: " + contract.getCustomer().getNic());
        System.out.println("Customer Name: " + contract.getCustomer().getName());
        System.out.println("Customer Contact: " + contract.getCustomer().getContactNumber());

        System.out.println("\nContract ID: " + contract.getContractId() +
                " (" + (contract.getContractType().equals("L") ? "Long Term" :
                contract.getContractType().equals("M") ? "Mid Term" : "Short Term") + ")");

        double totalAmount = 0;

        for (Apartment apartment : contract.getApartments()) {
            System.out.println("\n--- Apartment ID: " + apartment.getId() + " (" + apartment.getType() + ")");
            System.out.println("--- Item\tRate\tUnits\tAmount");
            System.out.println("------------------------------------------------");

            // Generate random utility readings
            int waterUsage = new Random().nextInt(200);
            int electricityUsage = new Random().nextInt(300);
            int acUsage = apartment.getType().equals("Eco") ? 0 : new Random().nextInt(20000);
            int cableTvDays = apartment.getType().equals("Luxury") ? contract.getDuration() : 0;

            // Calculate charges for this apartment
            double deposit = PriceChart.getDeposit(apartment.getType());
            double rental = PriceChart.getRental(apartment.getType()) * contract.getDuration();
            double discount = contract.getContractType().equals("L") ? PriceChart.getLongTermDiscount(apartment.getType()) : 0;
            double parking = PriceChart.getParkingCharges(apartment.getType()) * contract.getDuration();
            double terminationFine = contract.getStatus().equals("TERMINATED_DUE") ?
                    PriceChart.getEarlyTerminationFine(apartment.getType()) * contract.getRemainingDays() : 0;

            double waterCharge = 0;
            if (contract.getContractType().equals("S")) {
                waterCharge = PriceChart.getWaterShortTerm() * waterUsage;
            } else {
                if (waterUsage <= 100) {
                    waterCharge = PriceChart.getWaterLongMidTermLow() * waterUsage;
                } else {
                    waterCharge = PriceChart.getWaterLongMidTermLow() * 100 +
                            PriceChart.getWaterLongMidTermHigh() * (waterUsage - 100);
                }
            }

            double electricityCharge = 0;
            if (contract.getContractType().equals("S")) {
                electricityCharge = PriceChart.getElectricityShortTerm() * electricityUsage;
            } else {
                if (electricityUsage <= 100) {
                    electricityCharge = PriceChart.getElectricityLongMidTermLow() * electricityUsage;
                } else {
                    electricityCharge = PriceChart.getElectricityLongMidTermLow() * 100 +
                            PriceChart.getElectricityLongMidTermHigh() * (electricityUsage - 100);
                }
            }

            double acCharge = apartment.getType().equals("Semi-Luxury") ?
                    PriceChart.getAcSemiLuxury() * acUsage :
                    apartment.getType().equals("Luxury") ?
                            PriceChart.getAcLuxury() * acUsage : 0;

            double cableTvCharge = apartment.getType().equals("Luxury") ?
                    PriceChart.getCableTv() * cableTvDays : 0;

            // Print all items
            System.out.printf("Deposit\t%.2f\t1\t%.2f%n", deposit, deposit);
            System.out.printf("Rental\t%.2f\t%d\t%.2f%n",
                    PriceChart.getRental(apartment.getType()), contract.getDuration(), rental);

            if (discount != 0) {
                System.out.printf("Long-term discount\t%.2f\t1\t%.2f%n", discount, discount);
            }

            System.out.printf("Parking Charges\t%.2f\t%d\t%.2f%n",
                    PriceChart.getParkingCharges(apartment.getType()), contract.getDuration(), parking);

            if (terminationFine != 0) {
                System.out.printf("Early termination fine\t%.2f\t%d\t%.2f%n",
                        PriceChart.getEarlyTerminationFine(apartment.getType()), contract.getRemainingDays(), terminationFine);
            }

            // Water charges
            if (contract.getContractType().equals("S")) {
                System.out.printf("Water (fixed rate) S\t%.2f\t%d\t%.2f%n",
                        PriceChart.getWaterShortTerm(), waterUsage, waterCharge);
            } else {
                if (waterUsage <= 100) {
                    System.out.printf("Water (0-100 m3) L&M\t%.2f\t%d\t%.2f%n",
                            PriceChart.getWaterLongMidTermLow(), waterUsage, waterCharge);
                } else {
                    System.out.printf("Water (0-100 m3) L&M\t%.2f\t100\t%.2f%n",
                            PriceChart.getWaterLongMidTermLow(), PriceChart.getWaterLongMidTermLow() * 100);
                    System.out.printf("Water (100<) L&M\t%.2f\t%d\t%.2f%n",
                            PriceChart.getWaterLongMidTermHigh(), waterUsage - 100,
                            PriceChart.getWaterLongMidTermHigh() * (waterUsage - 100));
                }
            }

            // Electricity charges
            if (contract.getContractType().equals("S")) {
                System.out.printf("Electricity (fixed rate) S\t%.2f\t%d\t%.2f%n",
                        PriceChart.getElectricityShortTerm(), electricityUsage, electricityCharge);
            } else {
                if (electricityUsage <= 100) {
                    System.out.printf("Electricity (0-100 KWh) L&M\t%.2f\t%d\t%.2f%n",
                            PriceChart.getElectricityLongMidTermLow(), electricityUsage, electricityCharge);
                } else {
                    System.out.printf("Electricity (0-100 KWh) L&M\t%.2f\t100\t%.2f%n",
                            PriceChart.getElectricityLongMidTermLow(), PriceChart.getElectricityLongMidTermLow() * 100);
                    System.out.printf("Electricity (100<) L&M\t%.2f\t%d\t%.2f%n",
                            PriceChart.getElectricityLongMidTermHigh(), electricityUsage - 100,
                            PriceChart.getElectricityLongMidTermHigh() * (electricityUsage - 100));
                }
            }

            // AC and Cable TV
            if (!apartment.getType().equals("Eco")) {
                System.out.printf("AC\t%.4f\t%d\t%.2f%n",
                        apartment.getType().equals("Semi-Luxury") ? PriceChart.getAcSemiLuxury() : PriceChart.getAcLuxury(),
                        acUsage, acCharge);
            }

            if (apartment.getType().equals("Luxury")) {
                System.out.printf("Cable TV\t%.2f\t%d\t%.2f%n",
                        PriceChart.getCableTv(), cableTvDays, cableTvCharge);
            }

            // Calculate subtotal for this apartment
            double subtotal = deposit + rental + discount + parking + terminationFine +
                    waterCharge + electricityCharge + acCharge + cableTvCharge;
            totalAmount += subtotal;

            System.out.println("------------------------------------------------");
            System.out.printf("Sub Total\t\t\t%.2f%n", subtotal);
        }

        System.out.println("\n================================================");
        System.out.printf("Grand Total\t\t\t%.2f%n", totalAmount);
    }

    private static void displayTotalUtilityUsage() {
        System.out.println("\n===== Total Utility Usage =====");
        System.out.println("Electricity: " + contractManager.getTotalElectricityUsage() + " KWh");
        System.out.println("Water: " + contractManager.getTotalWaterUsage() + " m³");
        System.out.println("AC: " + contractManager.getTotalAcUsage() + " CFM");
    }

    private static void displayOccupiedApartments() {
        List<Apartment> occupied = apartmentManager.getOccupiedApartments();
        System.out.println("\n===== Occupied Apartments =====");
        System.out.println("Apartment ID | Type | Customer NIC | Contract ID");
        System.out.println("-----------------------------------------------");

        for (Apartment apartment : occupied) {
            Contract contract = contractManager.getContractForApartment(apartment.getId());
            System.out.printf("%s | %s | %s | %s%n",
                    apartment.getId(),
                    apartment.getType(),
                    contract.getCustomer().getNic(),
                    contract.getContractId());
        }
        System.out.println("Total: " + occupied.size());
    }

    private static void displayVacantApartments() {
        List<Apartment> vacant = apartmentManager.getVacantApartments();
        System.out.println("\n===== Vacant Apartments =====");
        System.out.println("Floor | Apartment ID | Type");
        System.out.println("---------------------------");

        for (Apartment apartment : vacant) {
            System.out.printf("%d | %s | %s%n",
                    apartment.getFloorLevel(),
                    apartment.getId(),
                    apartment.getType());
        }
        System.out.println("Total: " + vacant.size());
    }

    private static void searchCustomer() {
        System.out.print("Enter customer NIC to search: ");
        String nic = scanner.nextLine();

        Customer customer = customerManager.getCustomer(nic);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("\n===== Customer Details =====");
        System.out.println("Customer ID (NIC): " + customer.getNic());
        System.out.println("Name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Vehicle License Number: " + customer.getVehicleLicensePlate());
        System.out.println("Contact number: " + customer.getContactNumber());

        List<Contract> contracts = contractManager.getContractsByCustomer(nic);
        if (!contracts.isEmpty()) {
            System.out.println("\nContracts Signed:");
            for (Contract contract : contracts) {
                System.out.println(contract.getContractId());
            }
        } else {
            System.out.println("\nNo contracts signed by this customer.");
        }
    }

    private static String getApartmentIdsString(List<Apartment> apartments) {
        StringBuilder sb = new StringBuilder();
        for (Apartment apartment : apartments) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(apartment.getId());
        }
        return sb.toString();
    }
}
