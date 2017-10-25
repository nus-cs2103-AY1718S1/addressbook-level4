package seedu.address.model.relationship;

/**
 * This class defines the direction of relationships for Relationship class
 */
public enum RelationshipDirection {

    UNDIRECTED,
    DIRECTED;

    public String getDirection() {
        if (isDirected()) {
            return "directed";
        } else {
            return "undirected";
        }
    }

    public boolean isDirected() {
        return this == DIRECTED;
    }

    public String toString() {
        return this.getDirection();
    }
}
