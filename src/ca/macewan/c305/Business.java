package ca.macewan.c305;

public class Business implements Comparable{
    private int license;
    private String category;
    private String tradeName;
    private Address address;
    private Neighbourhood neighborhood;
    private Location loc;

    public Business(int license, String category, String tradeName, Address address, Neighbourhood neighborhood, Location loc) {
        this.license = license;
        this.category = category;
        this.tradeName = tradeName;
        this.address = address;
        this.neighborhood = neighborhood;
        this.loc = loc;
    }

    public int getLicense() { return this.license; }

    public String getCategory() {
        return this.category;
    }

    public String getTradeName() {
        return this.tradeName;
    }

    public Address getAddress() {
        return this.address;
    }

    public Neighbourhood getNeighborhood() {
        return this.neighborhood;
    }

    public Location getLoc() {
        return this.loc;
    }


    // Override toString, equals, and hashCode here
    @Override
    public String toString() {
        return this.tradeName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Business))
            return false;
        Business otherBusiness = (Business) obj;
        return this.getLicense() == otherBusiness.getLicense();
    }

    @Override
    public int hashCode() {
        return this.license;
    }

    @Override
    public int compareTo(Object o) {
        return tradeName.compareTo(((Business)o).tradeName);
    }
}