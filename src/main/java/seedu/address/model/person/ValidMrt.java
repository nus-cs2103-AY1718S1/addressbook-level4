package seedu.address.model.person;

import java.util.HashMap;
import java.util.Vector;

/**
* stores valid mrt names and its corresponding coordinates
 */
public class ValidMrt {

    public final HashMap<String, Coordinates> validMrt = new MrtListBuilder()
            .add("Clementi",1.315194, 103.764792)
            .add("Ang Mo Kio", 1.369864, 103.849534)
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

    public MrtListBuilder addLine(String Line) {

    }

    private void addNSline() {
        S("T",1.348549, 103.749036);
        S("Bukit Gombak",1.359030, 103.751834);
        S("Choa Chu Kang",1.385168, 103.744355);
        S("Yew Tee",1.397380, 103.747493);
        S("Kranji",1.425190, 103.761951);
        S("Marsling",1.432616, 103.774108);
        S("Jurong East",1.333134, 103.742260);
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

    private void addEWline() {
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