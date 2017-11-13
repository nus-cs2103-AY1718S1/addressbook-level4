package seedu.address.ui;

import seedu.address.logic.MrtMapLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

public class MrtMapUI {
    //radius of circle use to visualise mrt stations in the graph
    private int DEFAULT_CIRCLE_RADIUS = 5;
    private int MAX_CIRCLE_RADIUS = 20;

    HashMap<String, Point> mrtToPoint = new HashMap<String, Point>();

    HashMap<String, Integer> mrtNameToIndex = new HashMap<String, Integer>();
    ArrayList<String> mrtStationNames = new ArrayList<String>();
    ArrayList<String> mrtStationShortNames = new ArrayList<String>();
    ArrayList<ArrayList<String>> mrtLineNames = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<Integer>> mrtLineNumbers = new ArrayList<ArrayList<Integer>>();

    int canvassWidth = -1;
    int canvassHeight = -1;

    /**
     * Point class to store coordinates on screen/canvass display
     * @author YewOnn
     *
     */
    class Point {
        private int x; //the x coordinate
        private int y; //the y coordinate
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX(){
            return x;
        }
        int getY() {
            return y;
        }
    }

    public MrtMapUI(){
        initialise();
    }

    private void initialiseMrtToPointHashMap() {
		/*following values are hard coded;
		the info inside point specify the location of the Mrt within the screen
		as a percentage. e.g. new Point(20, 40) means that the point located at
		the 20% of the width from the left, and 40 of the height from the bottom. */

        //EW-coded (East-West line) stops
        mrtToPoint = new HashMap<String, Point>();
        mrtToPoint.put("TLK", new Point(4, 90)); //Tuas Link EW33
        mrtToPoint.put("CNG", new Point(4, 55)); //Chinese Garden EW25
        mrtToPoint.put("JUR", new Point(4, 50)); //Jurong East EW24 NS1
        mrtToPoint.put("BNV", new Point(16, 40)); //Buona Vista EW21 NS22
        mrtToPoint.put("OTP", new Point(32, 25)); //Outram Park EW16 NE3
        mrtToPoint.put("RFP", new Point(44, 15)); //Raffles Place EW14 NS26
        mrtToPoint.put("CTH", new Point(52, 30)); //City Hall EW13 NS25
        mrtToPoint.put("BGS", new Point(60, 35)); //Bugis EW12 DT14
        mrtToPoint.put("PYL", new Point(76, 45)); //Paya Lebar EW8 CC9
        mrtToPoint.put("TNM", new Point(88, 45)); //Tanah Merah EW4 CG0
        mrtToPoint.put("TAM", new Point(92, 60)); //Tampines EW2 DT32
        mrtToPoint.put("PSR", new Point(92, 70)); //Pasir Ris EW1

        //CG-coded (ChangiAirport) stops
        mrtToPoint.put("XPO", new Point(96, 45)); //CG1
        mrtToPoint.put("CGA", new Point(96, 35)); //CG2

        //NS-coded (North-South line) stops
        mrtToPoint.put("BBT", new Point(8, 55)); //Bukit Batok NS2
        mrtToPoint.put("KRJ", new Point(8, 90)); //Kranji NS7
        mrtToPoint.put("YIS", new Point(48, 90)); //Yishun NS13
        mrtToPoint.put("BSH", new Point(44, 70)); //Bishan NS17 CC15
        mrtToPoint.put("NEW", new Point(44, 55)); //NewTon NS21 DT11
        mrtToPoint.put("DBG", new Point(40, 40)); //Dhoby Ghaut NS24 NE6
        mrtToPoint.put("MRB", new Point(52, 10)); //Marina Bay NS27 CE2
        mrtToPoint.put("MSP", new Point(60, 6)); //Marina South Pier NS28

        //NE-coded (North-East line) stops
        mrtToPoint.put("HBF", new Point(20, 10)); //Harbour Front NE1 CC29
        mrtToPoint.put("CNT", new Point(36, 30)); //Chinatown NE4 DT19
        mrtToPoint.put("LTI", new Point(48, 45)); //Little India NE7 DT12
        mrtToPoint.put("SER", new Point(68, 70)); //Serangoon NE12 CC13
        mrtToPoint.put("PGL", new Point(88, 95)); //Punggol NE17

        //CC-coded (Circle line) stops
        mrtToPoint.put("BTN", new Point(32, 55)); //Botanic Garden CC19 DT9
        mrtToPoint.put("MPS", new Point(72, 55)); //Macpherson CC10 DT26
        mrtToPoint.put("PMN", new Point(68, 30)); //Promenade CC4 DT15

        //CE-coded (Circle line) stops
        mrtToPoint.put("BFT", new Point(60, 20)); //Bayfront CE1 DT16

        //DT-coded (Downtown line) stops
        mrtToPoint.put("BPJ", new Point(24, 80)); //BukitPanjang DT1
        mrtToPoint.put("SAV", new Point(24, 55)); //Sixth Avenue DT7
    }

    private Point getMrtStationCanvassCoordinate(int index) {
        String mrtName = mrtStationShortNames.get(index);
        int x = -1; //x-coordinate
        int y = -1; //y-coordinate
        Point point = null;

        if(mrtToPoint.containsKey(mrtName)) {
            point = mrtToPoint.get(mrtName);
        }else{
            int previousIndex = index;
            boolean previousMrtFound = false;
            boolean nextMrtFound = false;
            Point previousMrtCoordinate = null;
            Point nextMrtCoordinate = null;
            while(!previousMrtFound) {
                previousIndex--;
                String previousMrtShortName = mrtStationShortNames.get(previousIndex);
                if(mrtToPoint.containsKey(previousMrtShortName)) {
                    previousMrtFound = true;
                    previousMrtCoordinate = mrtToPoint.get(previousMrtShortName);
                }
            }
            int nextIndex = index;
            while(!nextMrtFound) {
                nextIndex++;
                String nextMrtShortName = mrtStationShortNames.get(nextIndex);
                if(mrtToPoint.containsKey(nextMrtShortName)) {
                    nextMrtFound = true;
                    nextMrtCoordinate = mrtToPoint.get(nextMrtShortName);
                }
            }
            double nextX = nextMrtCoordinate.getX();
            double nextY = nextMrtCoordinate.getY();

            double previousX = previousMrtCoordinate.getX();
            double previousY = previousMrtCoordinate.getY();

            //Number of station between previousStation and nextStation
            int numStation = nextIndex - previousIndex;

            //Number of station between previousStation and currentStatiom
            int numStationFromCurrToPrevious = index - previousIndex;

            double xDistance = nextX - previousX;
            double yDistance = nextY - previousY;

            int xCor = (int) (previousX + xDistance*numStationFromCurrToPrevious/numStation);
            int yCor = (int) (previousY + yDistance*numStationFromCurrToPrevious/numStation);
            point = new Point(xCor, yCor);
        }
        x = point.getX() * canvassWidth / 100;
        y = point.getY() * canvassHeight / 100;
        return new Point(x, y);
    }

    private void drawMrtMap() {
        prepareCanvass();
        initialiseMrtToPointHashMap();
        Point currPoint = null;
        Point previousPoint = null;
        for(int i = 0; i < mrtStationNames.size(); i++) {
            currPoint = getMrtStationCanvassCoordinate(i);

            int currMrtIndex = i;
            int previousMrtIndex = i - 1;

            if(isNeighbour(previousMrtIndex,currMrtIndex)) {
                StdDraw.setPenColor(StdDraw.GRAY);
                ArrayList<String> commonLines = getCommonLines(previousMrtIndex, currMrtIndex);
                StdDraw.setPenColor(getStationColor(commonLines.get(0)));
                StdDraw.line(currPoint.getX(), currPoint.getY(), previousPoint.getX(),
                        previousPoint.getY());
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.filledCircle(currPoint.getX(), currPoint.getY(), DEFAULT_CIRCLE_RADIUS);
                StdDraw.filledCircle(previousPoint.getX(), previousPoint.getY(), DEFAULT_CIRCLE_RADIUS);
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            previousPoint = currPoint;
        }
    }

    private void displayConvenientPoints(int[] mrtIndexes, int numDisplayStation) {

        for(int i = 0; i < numDisplayStation && i < mrtIndexes.length; i++) {
            int currMrtIndex = mrtIndexes[i];
            String currMrtName = mrtStationNames.get(currMrtIndex);
            int circleDifference = (MAX_CIRCLE_RADIUS - DEFAULT_CIRCLE_RADIUS)/numDisplayStation;
            int circleRadius = MAX_CIRCLE_RADIUS - i * circleDifference;
            displayMrtPoint(currMrtIndex, circleRadius);
        }
    }

    /**
     * All the data (mrtStationsNames, mrtNameToIndex, mrtStationShortNames, mrtStationLine,
     * mrtStation number) must be initialise before calling this method. This is resolve
     * by the only constructor of this class which call the initializer method. That is,
     * before this class could be called, the data are already initialiszed by the constructor
     * method.
     * @param mrtNames is the list of mrt stations sorted with the most convenient at the front.
     * @param numDisplayStation is the number of mrt stations to highlight
     */
    public void displayConvenientPoints(ArrayList<String> mrtNames, int numDisplayStation){
        int[] mrtIndexes = new int[mrtNames.size()];
        for(int i = 0; i < mrtNames.size(); i++){
            String mrtName = mrtNames.get(i);
            int mrtIndex = mrtNameToIndex.get(mrtName);
            mrtIndexes[i] = mrtIndex;
        }
        displayConvenientPoints(mrtIndexes, numDisplayStation);
    }

    private void displayMrtPoint(int mrtIndex, int circleRadius){
        Point point = getMrtStationCanvassCoordinate(mrtIndex);
        StdDraw.filledCircle(point.getX(), point.getY(), circleRadius);
        StdDraw.setPenColor(Color.BLACK);
        //write the text right about the circle
        StdDraw.text(point.getX(), point.getY() + circleRadius, mrtStationNames.get(mrtIndex));
    }

    private boolean isNeighbour(int mrtIndexOne, int mrtIndexTwo) {
        if(mrtIndexOne < 0 || mrtIndexTwo < -1) {
            return false;
        }

        ArrayList<String> mrtLineNameOne = mrtLineNames.get(mrtIndexOne);
        ArrayList<Integer> mrtLineNumbersOne = mrtLineNumbers.get(mrtIndexOne);
        int numInterchangeOne = mrtLineNameOne.size();

        ArrayList<String> mrtLineNameTwo = mrtLineNames.get(mrtIndexTwo);
        ArrayList<Integer> mrtLineNumbersTwo = mrtLineNumbers.get(mrtIndexTwo);
        int numInterchangeTwo = mrtLineNameTwo.size();

        for(int i = 0; i < numInterchangeOne; i++) {
            for(int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtLineNameOne.get(i);
                String mrtLineTwo = mrtLineNameTwo.get(j);
                if(mrtLineOne.equals(mrtLineTwo)) {
                    int stationNumberOne = mrtLineNumbersOne.get(i);
                    int stationNumberTwo = mrtLineNumbersTwo.get(j);
                    int stationDiff = stationNumberOne - stationNumberTwo;
                    int maxMrtGap = 2; //in Singapore mrt, the maximum jump in station number observed is 2;
                    if(Math.abs(stationDiff) <= maxMrtGap) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private ArrayList<String> getCommonLines(int mrtIndexOne, int mrtIndexTwo) {
        ArrayList<String> commonLines = new ArrayList<String>();

        ArrayList<String> mrtCodesOne = mrtLineNames.get(mrtIndexOne);
        int numInterchangeOne = mrtCodesOne.size();

        ArrayList<String> mrtCodesTwo = mrtLineNames.get(mrtIndexTwo);
        int numInterchangeTwo = mrtCodesTwo.size();

        for(int i = 0; i < numInterchangeOne; i++) {
            for(int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtCodesOne.get(i);
                String mrtLineTwo = mrtCodesTwo.get(j);
                if(mrtLineOne.equals(mrtLineTwo)) {
                    commonLines.add(mrtLineOne);
                }
            }
        }
        return commonLines;
    }

    /**
     * Prepare a blank canvass that occupies 75% of the screen
     */
    private void prepareCanvass() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        double threeQuarter = 0.75;

        //create a canvass that occupy 75% of the computer screen
        canvassWidth = (int) (screenWidth * threeQuarter);
        canvassHeight = (int) (screenHeight * threeQuarter);

        StdDraw.setCanvasSize(canvassWidth, canvassHeight);
        StdDraw.setXscale(0, canvassWidth);
        StdDraw.setYscale(0, canvassHeight);
    }

    private Color getStationColor(String lineName) {
        if(lineName.equals("EW") || lineName.equals("CG")) {
            return Color.GREEN;
        }else if(lineName.equals("NS")) {
            return Color.RED;

        }else if(lineName.equals("CC") || lineName.equals("CE")) {
            return Color.YELLOW;

        }else if(lineName.equals("NE")) {
            return Color.MAGENTA;

        }else if(lineName.equals("DT")){
            return Color.BLUE;

        }else {
            //if reach here, the station lineName is wrong;
            return null;
        }
    }

    private void initialise(){
        MrtMapLogic mrtMapLogic = new MrtMapLogic();

        mrtStationNames = mrtMapLogic.getMrtStationNames();
        mrtStationShortNames = mrtMapLogic.getMrtStationShortNames();
        mrtLineNames = mrtMapLogic.getMrtLineNames();
        mrtLineNumbers = mrtMapLogic.getMrtLineNumbers();

        initialiseMrtToPointHashMap();
        generateMrtNameToIndexHashMap();
        drawMrtMap();
    }

    private void generateMrtNameToIndexHashMap(){
        for(int i = 0; i < mrtLineNames.size(); i++){
            mrtNameToIndex.put(mrtStationNames.get(i), i);
        }
    }

    public static void main (String[] args) {
        MrtMapUI mrtUI = new MrtMapUI();
        ArrayList<String> convenientMrts = new ArrayList<String>();
        convenientMrts.add("Jurong East");
        convenientMrts.add("Dhoby Ghaut");
        mrtUI.displayConvenientPoints(convenientMrts, 2);
    }
}
