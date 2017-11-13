package seedu.address.ui;

import seedu.address.logic.MrtMapLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;


//@@author Yew Onn
/**
 * The java class displays the UI.
 */
public class MrtMapUI {
    //radius of circle use to visualise mrt stations in the graph
    private static int DEFAULT_CIRCLE_RADIUS = 4;
    private static int MAX_CIRCLE_RADIUS = 35;

    private HashMap<String, Point> mrtToPoint = new HashMap<String, Point>();

    private HashMap<String, Integer> mrtNameToIndex = new HashMap<String, Integer>();
    private ArrayList<String> mrtStationNames = new ArrayList<String>();
    private ArrayList<String> mrtStationShortNames = new ArrayList<String>();
    private ArrayList<ArrayList<String>> mrtLineNames = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<Integer>> mrtLineNumbers = new ArrayList<ArrayList<Integer>>();

    private int canvassWidth = -1;
    private int canvassHeight = -1;

    /**
     * Point class to store coordinates on screen/canvass display
     * @author YewOnn
     *
     */
    class Point {
        private int x; //the x coordinate
        private int y; //the y coordinate
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }
        public int getY() {
            return y;
        }
    }

    /**
     * Initialise all relevant informations required for the running of the UI.
     */
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

    /**
     *
     * @param meetStation is the mrt station where the people meet
     * @param mrtStations are the list of mrtStations where the people meet
     */
    public void displayUserInfo(String meetStation, ArrayList<String> mrtStations) {
        visualiseStations(mrtStations);

        displayMrtPoint(meetStation, MAX_CIRCLE_RADIUS);
        displayMrtName(meetStation, MAX_CIRCLE_RADIUS);
    }

    private void visualiseStations(ArrayList<String> mrtStations){
        HashMap<String, Integer> mrtStationCount = new HashMap<String, Integer>();
        for(int i = 0; i < mrtStations.size(); i++){
            String currStationName = mrtStations.get(i);
            boolean isExist = mrtStationCount.containsKey(currStationName);
            int mrtCount = 0;
            if(isExist){
                mrtCount = mrtStationCount.get(currStationName);
                mrtCount++;
                mrtStationCount.put(currStationName, mrtCount);
            }else{
                mrtStationCount.put(currStationName, 1);
                mrtCount++;
            }
            int circleRadius = DEFAULT_CIRCLE_RADIUS + mrtCount*DEFAULT_CIRCLE_RADIUS;
            if(circleRadius > MAX_CIRCLE_RADIUS){
                circleRadius = MAX_CIRCLE_RADIUS;
            }
            displayMrtPoint(currStationName, circleRadius);
        }
    }

    private void displayMrtPoint(String mrtStationName, int circleRadius){
        int mrtIndex = mrtNameToIndex.get(mrtStationName);
        displayMrtPoint(mrtIndex, circleRadius);
    }

    private void displayMrtName(String mrtStationName, int circleRadius){
        int mrtIndex = mrtNameToIndex.get(mrtStationName);
        displayMrtName(mrtIndex, circleRadius);
    }

    private void displayMrtName(int mrtIndex, int circleRadius){
        Point point = getMrtStationCanvassCoordinate(mrtIndex);
        //display the name right above the circle
        StdDraw.text(point.getX(), point.getY() + circleRadius, mrtStationNames.get(mrtIndex));
    }

    private void displayMrtPoint(int mrtIndex, int circleRadius){
        Point point = getMrtStationCanvassCoordinate(mrtIndex);
        int numInterchange = mrtLineNames.get(mrtIndex).size();
        for(int i = 0; i < numInterchange; i++) {
            Color stationColor = getStationColor(mrtLineNames.get(mrtIndex).get(i));
            StdDraw.setPenColor(stationColor);
            int currCircleRadius = circleRadius - (circleRadius/numInterchange)*i;
            StdDraw.filledCircle(point.getX(), point.getY(), currCircleRadius);
        }

        //a black outline for visibility purposes;
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.circle(point.getX(), point.getY(), circleRadius);
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
        if("EW".equals(lineName) || "CG".equals(lineName)) {
            //East West line which is Green in color in mrt map
            return Color.GREEN;

        }else if("NS".equals(lineName)) {
            //NorthSouth Line which is red in color on Mrt Map
            return Color.RED;

        }else if("CC".equals(lineName) || ("CE").equals(lineName)) {
            //Circle line, which is yellow in color in mrt map;
            return Color.YELLOW;

        }else if("NE".equals(lineName)) {
            //Nourth East line which is purple in color in mrt map;
            return Color.MAGENTA;

        }else if("DT".equals(lineName)){
            //Down Town line is blue in color in the mrt map;
            return Color.BLUE;

        }else {
            //if reach here, the station lineName is wrong;
            //just return a black color anyway
            return Color.BLACK;
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

}
