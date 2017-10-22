package seedu.address.model.person;

import java.util.HashMap;

/**
* stores valid mrt names and its corresponding coordinates
 */
public class ValidMrt {

    public static final HashMap<String, Coordinates> VALID_MRT_SET = new MrtListBuilder()
            .addLines().build();          //only circle, NW, NE and EW line available

    public ValidMrt() {
    }
}

/**
 * builds the list of valid mrt stations
 */
class MrtListBuilder {
    private HashMap<String, Coordinates> mrt;

    public MrtListBuilder() {
        mrt = new HashMap<String, Coordinates>();
    }

    /**
     * for adding special cases of MRT stations that don't belong to any line
     */
    public MrtListBuilder addStation(String station, double x, double y) {
        mrt.put(station, new Coordinates(x, y));
        return this;
    }

    /**
     * add a station to map without returning anything
     */
    public void add(String station, double x, double y) {
        mrt.put(station, new Coordinates(x, y));
    }

    /**
     * consolidates all stations and returns this
     */
    public MrtListBuilder addLines() {
        addEWline();
        addNSline();
        addNWline();
        addCircleLine();
        return this;
    }

    /**
     * adds stations in East-West line
     */
    private void addEWline() {
        add("Tuas Link", 1.340349, 103.636813);
        add("Tuas West Road", 1.330048, 103.639617);
        add("Tuas Crescent", 1.321049, 103.649059);
        add("Gul Circle", 1.319484, 103.660542);
        add("Joo Koon", 1.327751, 103.678308);
        add("Pioneer", 1.337313, 103.696862);
        add("Lakeside", 1.344243, 103.720728);
        add("Boon Lay", 1.338557, 103.705826);
        add("Chinese Garden", 1.342360, 103.732551);
        add("Jurong East", 1.333134, 103.742260);
        add("Clementi", 1.315194, 103.764792);
        add("Dover", 1.311325, 103.778653);
        add("Buona Vista", 1.307327, 103.789937);
        add("Commonwealth", 1.302450, 103.798288);
        add("Queenstown", 1.295274, 103.805633);
        add("Redhill", 1.289629, 103.816758);
        add("Tiong Bahru", 1.286199, 103.827017);
        add("Outram Park", 1.279770, 103.839575);
        add("Tanjong Pagar", 1.276406, 103.846856);
        add("City Hall", 1.293092, 103.851924);
        add("Raffles Place", 1.283017, 103.851331);
        add("Bugis", 1.301099, 103.856113);
        add("Lavender", 1.307232, 103.862951);
        add("Kallang", 1.311484, 103.871351);
        add("Aljunied", 1.316432, 103.882903);
        add("Paya Lebar", 1.317438, 103.892171);
        add("Eunos", 1.319783, 103.902864);
        add("Kembangan", 1.321033, 103.912914);
        add("Bedok", 1.324022, 103.930151);
        add("Tanah Merah", 1.327233, 103.946541);
        add("Simei", 1.343211, 103.953374);
        add("Tampines", 1.353218, 103.945205);
        add("Pasir Ris", 1.373090, 103.949310);
    }

    /**
     * adds stations in North-South line
     */
    private void addNSline() {
        add("Jurong East", 1.333134, 103.742260);
        add("Bukit Batok", 1.348549, 103.749036);
        add("Bukit Gombak", 1.359030, 103.751834);
        add("Choa Chu Kang", 1.385168, 103.744355);
        add("Yew Tee", 1.397380, 103.747493);
        add("Kranji", 1.425190, 103.761951);
        add("Marsling", 1.432616, 103.774108);
        add("Woodlands", 1.436980, 103.786480);
        add("Admiralty", 1.440597, 103.800970);
        add("Sembawang", 1.448895, 103.820084);
        add("Yishun", 1.429341, 103.834985);
        add("Khatib", 1.417349, 103.833019);
        add("Yio Chu Kang", 1.381758, 103.844797);
        add("Ang Mo Kio", 1.369864, 103.849534);
        add("Bishan", 1.351314, 103.849152);
        add("Braddell", 1.340434, 103.846801);
        add("Toa Payoh", 1.332707, 103.847062);
        add("Novena", 1.320441, 103.843807);
        add("Newton", 1.313618, 103.837806);
        add("Orchard", 1.304588, 103.831931);
        add("Somerset", 1.300591, 103.838518);
        add("Dhoby Ghaut", 1.299711, 103.845488);
        add("City Hall", 1.293092, 103.851924);
        add("Raffles Place", 1.283017, 103.851331);
        add("Marina Bay", 1.276382, 103.854583);
        add("Marina South Pier", 1.270978, 103.863303);
    }

    /**
     * adds stations in North-West line
     */
    private void addNWline() {
        add("HarbourFront", 1.265925, 103.820900);
        add("Outram Park", 1.279770, 103.839575);
        add("Chinatown", 1.284793, 103.843926);
        add("Clarke Quay", 1.288625, 103.847105);
        add("Dhoby Ghaut", 1.299711, 103.845488);
        add("Little India", 1.306291, 103.849391);
        add("Farrer Park", 1.312456, 103.854813);
        add("Boon Keng", 1.319859, 103.861828);
        add("Potong Pasir", 1.331302, 103.868793);
        add("Woodleigh", 1.338918, 103.870472);
        add("Serangoon", 1.349726, 103.873567);
        add("Kovan", 1.359996, 103.885298);
        add("Hougang", 1.371139, 103.892461);
        add("Buangkok", 1.383069, 103.893108);
        add("Sengkang", 1.391530, 103.895411);
        add("Punggol", 1.405170, 103.902353);
    }

    /**
     * adds stations in Circle line
     */
    private void addCircleLine() {
        add("Dhoby Ghaut", 1.299711, 103.845488);
        add("Bras Basah", 1.296948, 103.850793);
        add("Esplanade", 1.293433, 103.855378);
        add("Promenade", 1.292886, 103.860853);
        add("Nicoll Highway", 1.300054, 103.863667);
        add("Stadium", 1.302503, 103.874557);
        add("Mountbatten", 1.306205, 103.882542);
        add("Dakota", 1.308358, 103.888683);
        add("Paya Lebar", 1.317438, 103.892171);
        add("MacPherson", 1.326750, 103.889874);
        add("Tai Seng", 1.335935, 103.887681);
        add("Bartley", 1.342331, 103.880246);
        add("Serangoon", 1.349726, 103.873567);
        add("Lorong Chuan", 1.351484, 103.864662);
        add("Bishan", 1.351314, 103.849152);
        add("Marymount", 1.348524, 103.839499);
        add("Caldecott", 1.337663, 103.839541);
        add("Botanic Gardens", 1.322112, 103.814983);
        add("Farrer Road", 1.317581, 103.807758);
        add("Holland Village", 1.311190, 103.796140);
        add("Buona Vista", 1.307327, 103.789937);
        add("one-north", 1.299881, 103.787427);
        add("Kent Ridge", 1.292500, 103.784886);
        add("Haw Par Villa", 1.283128, 103.781952);
        add("Pasir Panjang", 1.276036, 103.791901);
        add("Labrador Park", 1.272221, 103.802414);
        add("Telok Blangah", 1.270689, 103.809876);
        add("HarbourFront", 1.265925, 103.820900);
        add("Bayfront", 1.281362, 103.858914);
        add("Marina Bay", 1.276382, 103.854583);
    }

    /**
     * returns the mrt (accessor)
     */
    public HashMap<String, Coordinates> build() {
        return mrt;
    }
}

/**
 * Class for coordinates
 */
class Coordinates {
    private double x;
    private double y;
    Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //accessor
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    /**
     * returns the distance between 2 coordinates
     */
    public double getDistance(Coordinates coordinates) {
        double xDist = Math.abs(coordinates.getX() - x);
        double yDist = Math.abs(coordinates.getY() - y);
        double res = Math.sqrt(xDist * xDist + yDist * yDist); //pythagoras
        return res;
    }
}
