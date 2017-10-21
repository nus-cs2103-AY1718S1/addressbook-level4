package seedu.address.model.relationship;

/**
 * This class defines the direction of relationships for Relationship class
 */
public enum RelationshipDirection {

    UNDIRECTED("undirected"),
    DIRECTED("directed");

    private final String directed = "directed";
    private String direction;

    RelationshipDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isDirected() {
        return this.direction.equals(directed);
    }

    public String toString() {
        return this.getDirection();
    }
}
