import java.text.SimpleDateFormat;
import java.util.*;

class ContractManager {
    private Map<String, Contract> contracts = new HashMap<>();
    private int contractCounter = 1;

    public Contract createContract(String contractType, Customer customer, List<Apartment> apartments, int duration) {
        // Generate contract ID
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String serial = String.format("%03d", contractCounter++);
        String contractId = year + contractType + serial;

        // Calculate dates
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        if (contractType.equals("L")) {
            calendar.add(Calendar.YEAR, duration);
        } else if (contractType.equals("M")) {
            calendar.add(Calendar.MONTH, duration);
        } else { // S
            calendar.add(Calendar.DAY_OF_YEAR, duration);
        }

        Date endDate = calendar.getTime();

        // Calculate duration in days
        int durationInDays = duration;
        if (contractType.equals("L")) {
            durationInDays = duration * 365;
        } else if (contractType.equals("M")) {
            durationInDays = duration * 30;
        }

        // Create contract
        Contract contract = new Contract(contractId, contractType, customer, apartments, startDate, endDate, durationInDays);
        contracts.put(contractId, contract);

        // Mark apartments as occupied
        for (Apartment apartment : apartments) {
            apartment.setVacant(false);
        }

        return contract;
    }

    public Contract getContract(String contractId) {
        return contracts.get(contractId);
    }

    public List<Contract> getAllContracts() {
        return new ArrayList<>(contracts.values());
    }

    public List<Contract> getEndedContracts() {
        List<Contract> ended = new ArrayList<>();
        Date now = new Date();

        for (Contract contract : contracts.values()) {
            if (contract.getEndDate().before(now) && contract.getStatus().equals("OPENED")) {
                contract.setStatus("ENDED");
                ended.add(contract);
            }
        }

        return ended;
    }

    public List<Contract> getContractsByCustomer(String nic) {
        List<Contract> customerContracts = new ArrayList<>();

        for (Contract contract : contracts.values()) {
            if (contract.getCustomer().getNic().equals(nic)) {
                customerContracts.add(contract);
            }
        }

        return customerContracts;
    }

    public Contract getContractForApartment(String apartmentId) {
        for (Contract contract : contracts.values()) {
            for (Apartment apartment : contract.getApartments()) {
                if (apartment.getId().equals(apartmentId)) {
                    return contract;
                }
            }
        }
        return null;
    }

    public void terminateContract(Contract contract) {
        if (contract.getStatus().equals("OPENED")) {
            contract.setStatus("TERMINATED_DUE");
        }
    }

    public void closeContract(Contract contract) {
        if (contract.getStatus().equals("ENDED")) {
            contract.setStatus("CLOSED");
            // Mark apartments as vacant
            for (Apartment apartment : contract.getApartments()) {
                apartment.setVacant(true);
            }
        }
    }

    // Utility usage tracking (simplified with random numbers)
    public int getTotalElectricityUsage() {
        return new Random().nextInt(2000);
    }

    public int getTotalWaterUsage() {
        return new Random().nextInt(1500);
    }

    public int getTotalAcUsage() {
        return new Random().nextInt(20000);
    }
}
