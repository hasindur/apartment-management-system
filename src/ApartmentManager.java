import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ApartmentManager {
    private Map<String, Apartment> apartments = new HashMap<>();

    public void initializeApartments() {
        // Floor 1
        addApartments(1, "Eco", 4);
        addApartments(1, "Semi-Luxury", 5);
        addApartments(1, "Luxury", 3);

        // Floor 2
        addApartments(2, "Eco", 5);
        addApartments(2, "Semi-Luxury", 5);
        addApartments(2, "Luxury", 5);

        // Floor 3
        addApartments(3, "Eco", 5);
        addApartments(3, "Semi-Luxury", 5);
        addApartments(3, "Luxury", 5);

        // Floor 4
        addApartments(4, "Eco", 2);
        addApartments(4, "Semi-Luxury", 5);
        addApartments(4, "Luxury", 10);

        // Floor 5
        addApartments(5, "Eco", 2);
        addApartments(5, "Semi-Luxury", 5);
        addApartments(5, "Luxury", 10);
    }

    private void addApartments(int floor, String type, int count) {
        for (int i = 1; i <= count; i++) {
            String id = String.format("%d%s%02d", floor, type.charAt(0), i);
            apartments.put(id, new Apartment(id, type, floor, i));
        }
    }

    public Apartment getApartment(String id) {
        return apartments.get(id);
    }

    public List<Apartment> getVacantApartments() {
        List<Apartment> vacant = new ArrayList<>();
        for (Apartment apartment : apartments.values()) {
            if (apartment.isVacant()) {
                vacant.add(apartment);
            }
        }
        return vacant;
    }

    public List<Apartment> getOccupiedApartments() {
        List<Apartment> occupied = new ArrayList<>();
        for (Apartment apartment : apartments.values()) {
            if (!apartment.isVacant()) {
                occupied.add(apartment);
            }
        }
        return occupied;
    }

    public void markApartmentOccupied(String id) {
        Apartment apartment = apartments.get(id);
        if (apartment != null) {
            apartment.setVacant(false);
        }
    }

    public void markApartmentVacant(String id) {
        Apartment apartment = apartments.get(id);
        if (apartment != null) {
            apartment.setVacant(true);
        }
    }
}
