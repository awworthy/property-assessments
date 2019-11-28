package ca.macewan.cmpt305;

public class Neighborhood {
    private int id;
    private String name;
    private String ward;

    public Neighborhood(int id, String name, String ward) {
        this.id = id;
        this.name = name;
        this.ward = ward;
    }
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getWard() {
        return this.ward;
    }

    // Override toString, equals, and hashCode here
    public String toString() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Neighborhood))
            return false;
        Neighborhood otherNeighborhood = (Neighborhood) obj;
        return this.id == otherNeighborhood.id && this.name.equals(otherNeighborhood.name) && this.ward.equals(otherNeighborhood.ward);
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + name.hashCode();
        result = 31 * result + ward.hashCode();
        return result;
    }
}

