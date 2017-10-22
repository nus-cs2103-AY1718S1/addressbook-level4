package seedu.address.model.person;

import java.util.HashMap;
import java.util.Vector;

/**
* stores valid mrt names and its corresponding coordinates
 */
public class ValidMrt {

    public final HashMap<String, Coordinates> validMrt = new MrtListBuilder()
            .addLine()          //only circle, NW, NE and EW line available
            .build();

    public ValidMrt() {

    }
}

class MrtListBuilder{
    public HashMap<String, Coordinates> mrt;

    public MrtListBuilder() {
        mrt = new HashMap<String, Coordinates>();
    }

    public MrtListBuilder add(String station, double x, double y) {
        mrt.put(station, new Coordinates(x, y));
        return this;
    }

    public void S(String station, double x, double y) {
        this.add(station, x, y);
    }

    public MrtListBuilder addLine() {
        addEWline();
        addNSline();
        addNWline();
        addCircleLine();
        return this;
    }

    private void addEWline() {
        S("Tuas Link",1.340349, 103.636813);
        S("Tuas West Road",1.330048, 103.639617);
        S("Tuas Crescent",1.321049, 103.649059);
        S("Gul Circle",1.319484, 103.660542);
        S("Joo Koon",1.327751, 103.678308);
        S("Pioneer",1.337313, 103.696862);
        S("Lakeside",1.344243, 103.720728);
        S("Boon Lay",1.338557, 103.705826);
        S("Chinese Garden",1.342360, 103.732551);
        S("Jurong East",1.333134, 103.742260);
        S("Clementi",1.315194, 103.764792);
        S("Dover",1.311325, 103.778653);
        S("Buona Vista",1.307327, 103.789937);
        S("Commonwealth",1.302450, 103.798288);
        S("Queenstown",1.295274, 103.805633);
        S("Redhill",1.289629, 103.816758);
        S("Tiong Bahru",1.286199, 103.827017);
        S("Outram Park",1.279770, 103.839575);
        S("Tanjong Pagar",1.276406, 103.846856);
        S("City Hall",1.293092, 103.851924);
        S("Raffles Place",1.283017, 103.851331);
        S("Bugis",1.301099, 103.856113);
        S("Lavender",1.307232, 103.862951);
        S("Kallang",1.311484, 103.871351);
        S("Aljunied",1.316432, 103.882903);
        S("Paya Lebar",1.317438, 103.892171);
        S("Eunos",1.319783, 103.902864);
        S("Kembangan",1.321033, 103.912914);
        S("Bedok",1.324022, 103.930151);
        S("Tanah Merah",1.327233, 103.946541);
        S("Simei",1.343211, 103.953374);
        S("Tampines",1.353218, 103.945205);
        S("Pasir Ris",1.373090, 103.949310);
    }

    private void addNSline() {
        S("Jurong East",1.333134, 103.742260);
        S("Bukit Batok",1.348549, 103.749036);
        S("Bukit Gombak",1.359030, 103.751834);
        S("Choa Chu Kang",1.385168, 103.744355);
        S("Yew Tee",1.397380, 103.747493);
        S("Kranji",1.425190, 103.761951);
        S("Marsling",1.432616, 103.774108);
        S("Woodlands",1.436980, 103.786480);
        S("Admiralty",1.440597, 103.800970);
        S("Sembawang",1.448895, 103.820084);
        S("Yishun",1.429341, 103.834985);
        S("Khatib",1.417349, 103.833019);
        S("Yio Chu Kang",1.381758, 103.844797);
        S("Ang Mo Kio",1.369864, 103.849534);
        S("Bishan",1.351314, 103.849152);
        S("Braddell",1.340434, 103.846801);
        S("Toa Payoh",1.332707, 103.847062);
        S("Novena",1.320441, 103.843807);
        S("Newton",1.313618, 103.837806);
        S("Orchard",1.304588, 103.831931);
        S("Somerset",1.300591, 103.838518);
        S("Dhoby Ghaut",1.299711, 103.845488);
        S("City Hall",1.293092, 103.851924);
        S("Raffles Place",1.283017, 103.851331);
        S("Marina Bay",1.276382, 103.854583);
        S("Marina South Pier",1.270978, 103.863303);
    }

    private void addNWline() {
        S("HarbourFront", 1.265925, 103.820900);
        S("Outram Park",1.279770, 103.839575);
        S("Chinatown", 1.284793, 103.843926);
        S("Clarke Quay", 1.288625, 103.847105);
        S("Dhoby Ghaut",1.299711, 103.845488);
        S("Little India", 1.306291, 103.849391);
        S("Farrer Park", 1.312456, 103.854813);
        S("Boon Keng", 1.319859, 103.861828);
        S("Potong Pasir", 1.331302, 103.868793);
        S("Woodleigh", 1.338918, 103.870472);
        S("Serangoon", 1.349726, 103.873567);
        S("Kovan", 1.359996, 103.885298);
        S("Hougang", 1.371139, 103.892461);
        S("Buangkok", 1.383069, 103.893108);
        S("Sengkang", 1.391530, 103.895411);
        S("Punggol", 1.405170, 103.902353);
    }


    private void addCircleLine() {
        S("Dhoby Ghaut", 1.299711, 103.845488);
        S("Bras Basah", 1.296948, 103.850793);
        S("Esplanade", 1.293433, 103.855378);
        S("Promenade", 1.292886, 103.860853);
        S("Nicoll Highway", 1.300054, 103.863667);
        S("Stadium", 1.302503, 103.874557);
        S("Mountbatten", 1.306205, 103.882542);
        S("Dakota", 1.308358, 103.888683);
        S("Paya Lebar", 1.317438, 103.892171);
        S("MacPherson", 1.326750, 103.889874);
        S("Tai Seng", 1.335935, 103.887681);
        S("Bartley", 1.342331, 103.880246);
        S("Serangoon", 1.349726, 103.873567);
        S("Lorong Chuan", 1.351484, 103.864662);
        S("Bishan",1.351314, 103.849152);
        S("Marymount", 1.348524, 103.839499);
        S("Caldecott", 1.337663, 103.839541);
        S("Botanic Gardens", 1.322112, 103.814983);
        S("Farrer Road", 1.317581, 103.807758);
        S("Holland Village", 1.311190, 103.796140);
        S("Buona Vista",1.307327, 103.789937);
        S("one-north", 1.299881, 103.787427);
        S("Kent Ridge", 1.292500, 103.784886);
        S("Haw Par Villa", 1.283128, 103.781952);
        S("Pasir Panjang", 1.276036, 103.791901);
        S("Labrador Park", 1.272221, 103.802414);
        S("Telok Blangah", 1.270689, 103.809876);
        S("HarbourFront", 1.265925, 103.820900);
        S("Bayfront", 1.281362, 103.858914);
        S("Marina Bay",1.276382, 103.854583);
    }


    public HashMap<String, Coordinates> build() {
        return mrt;
    }
}

class Coordinates{
    private double x;
    private double y;
    Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() { return x; }
    public double getY() { return y; }
}