package ca.dakota.cmpt305;

/**
 * Neighbourhood Object
 *
 * Stores the various attributes of a neighbourhood
 *
 * @author Dakota Doolaege
 */
public class Neighbourhood {
    private String neighbourhoodId;
    private String neighbourhood;
    private String ward;

    /**
     * Constructor
     *
     * @param neighbourhoodId the id of the neighbourhood
     * @param neighbourhood the name of the neighbourhood
     * @param ward the ward of the neighbourhood
     */
    public Neighbourhood(String neighbourhoodId,String neighbourhood, String ward){
        this.neighbourhoodId = neighbourhoodId;
        this.neighbourhood = neighbourhood;
        this.ward = ward;
    }

    /**
     * Gets the ID for the neighbourhood
     * @return string value id
     */
    public String getNeighbourhoodId() {
        return neighbourhoodId;
    }

    /**
     * Gets the neighbourhood name
     * @return string value name of the neighbourhood
     */
    public String getNeighbourhood(){
        return neighbourhood;
    }

    /**
     * Gets the ward of the neighbourhood
     * @return string value ward
     */
    public String getWard() {
        return ward;
    }

   public String toString(){
        return neighbourhood;
   }
}
