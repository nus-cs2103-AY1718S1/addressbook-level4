package seedu.address.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

//@@author Yew Onn
/**
 * The java class that computes the list of mrt stations that reduces the total travelling
 * time. The timing is base on actual mrt data.
 */
public class MrtMapLogic {
    public MrtMapLogic(){
        initialise();
    }
    //require five minutes to transfer station
    private final int INTERCHANGE_TRANSFER_TIME = 5;

    ArrayList<MrtStation> mrtStations = new ArrayList<MrtStation>();
    ArrayList<Integer> trainTimings = new ArrayList<Integer>();

    //the index of the mrt in the mrtList
    HashMap<String, Integer> stationCodeToIndex = new HashMap<String, Integer>();
    HashMap<String, Integer> nameToIndex = new HashMap<String, Integer>();

    //adjacency list for DijkStra's Algorithm
    ArrayList<ArrayList<IntPair>> adjList = new ArrayList<ArrayList<IntPair>>();

    /**
     * MrtStation object represents a physical Mrt Station's information
     */
    class MrtStation{
        private String name;
        private String shortName;

        /*ne line can have more than one station code
        e.g. DhobyGhaut has NS24, NE6, CC1*/
        private String[] stationCodes;

        public MrtStation(String name, String shortName, String[] stationCodes) {
            this.name = name;
            this.shortName = shortName;
            this.stationCodes = new String[stationCodes.length];
            for(int i = 0; i < stationCodes.length; i++) {
                this.stationCodes[i] = stationCodes[i];
            }
        }

        public String getName() {
            return name;
        }

        public String getShortName() {
            return shortName;
        }

        /**
         *
         * @param index
         * @return the mrtCode specified by that index.
         */

        public String getStationCode(int index) {
            return stationCodes[index];
        }

        /**
         *
         * @param index
         * @return return the line name for the line specified by its
         * index in the stationCodes array.
         */
        public String getMrtLine(int index) {
            String code = stationCodes[index];
            String mrtLine = code.substring(0, 2);
            return mrtLine;
        }

        /**
         *
         * @param index
         * @return return the station number for the line specified by its
         * index in the stationCodes array.
         */
        public int getStationNumber(int index) {
            String code = stationCodes[index];
            String stationNumber = code.substring(2, code.length());
            return Integer.parseInt(stationNumber);
        }

        public String[] getStationCodes() {
            String[] stationCodes = new String[getNumInterchange()];
            for(int i = 0; i < getNumInterchange(); i++) {
                stationCodes[i] = this.stationCodes[i];
            }
            return stationCodes;
        }

        public int getNumInterchange() {
            //number of interchange is equals to number of station codes.
            //e.g. DhobyGhaut has 3 interchange as its station number are NS24, NE6, CC1.
            return stationCodes.length;
        }
    }

    /**
     * IntPair is for the adjacenctList
     * @author YewOnn
     *
     */
    class IntPair implements Comparator<IntPair> {
        int mrtIndex;
        int travelDuration;

        IntPair(int mrtIndex, int travelInterval){
            this.mrtIndex = mrtIndex;
            this.travelDuration = travelInterval;
        }

        @Override
        public int compare(IntPair o1, IntPair o2) {
            // TODO Auto-generated method stub
            return o1.travelDuration - o2.travelDuration;
        }
    }

    /**
     * This method acts as data storage.
     */
    private void populateMrtStations() {
        mrtStations.add(new MrtStation("Jurost East", "JUR", new String[] {"NS1", "EW24"}));
        mrtStations.add(new MrtStation("Bukit Batok", "BBT", new String[] {"NS2"}));
        mrtStations.add(new MrtStation("Bukit Gombak", "BGB", new String[] {"NS3"}));
        mrtStations.add(new MrtStation("Choa Chua Kang", "CCK", new String[] {"NS4"}));
        mrtStations.add(new MrtStation("Yew Tee", "YWT", new String[] {"NS5"}));
        mrtStations.add(new MrtStation("Kranji", "KRJ", new String[] {"NS7"}));
        mrtStations.add(new MrtStation("Marsiling", "MSL", new String[] {"NS8"}));
        mrtStations.add(new MrtStation("Woodlands", "WDL", new String[] {"NS9"}));
        mrtStations.add(new MrtStation("Admiralty", "ADM", new String[] {"NS10"}));
        mrtStations.add(new MrtStation("Sembawang", "SBW", new String[] {"NS11"}));
        mrtStations.add(new MrtStation("Yishun", "YIS", new String[] {"NS13"}));
        mrtStations.add(new MrtStation("Khathib", "KTB", new String[] {"NS14"}));
        mrtStations.add(new MrtStation("Yio Chu Kang", "YCK", new String[] {"NS15"}));
        mrtStations.add(new MrtStation("Ang Mo Kio", "AMK", new String[] {"NS16"}));
        mrtStations.add(new MrtStation("Bishan", "BSH", new String[] {"NS17", "CC15"}));
        mrtStations.add(new MrtStation("Braddell", "BDL", new String[] {"NS18"}));
        mrtStations.add(new MrtStation("Toa Payoh", "TAP", new String[] {"NS19"}));
        mrtStations.add(new MrtStation("Novena", "NOV", new String[] {"NS20"}));
        mrtStations.add(new MrtStation("Newton", "NEW", new String[] {"NS21", "DT11"}));
        mrtStations.add(new MrtStation("Orchard", "ORC", new String[] {"NS22"}));
        mrtStations.add(new MrtStation("Somerset", "SOM", new String[] {"NS23"}));
        mrtStations.add(new MrtStation("Dhoby Ghaut", "DBG", new String[] {"NS24", "NE6", "CC1"}));
        mrtStations.add(new MrtStation("City Hall", "CTH", new String[] {"NS25", "EW13"}));
        mrtStations.add(new MrtStation("Raffles Place", "RFP", new String[] {"NS26", "EW14"}));
        mrtStations.add(new MrtStation("Marina Bay", "MRB", new String[] {"NS27", "CE2"}));
        mrtStations.add(new MrtStation("Marina South Pier", "MSP", new String[] {"NS28"}));
        mrtStations.add(new MrtStation("Pasir Ris", "PSR", new String[] {"EW1"}));
        mrtStations.add(new MrtStation("Tampines", "TAM", new String[] {"EW2", "DT32"}));
        mrtStations.add(new MrtStation("Simei", "SIM", new String[] {"EW3"}));
        mrtStations.add(new MrtStation("Tanah Merah", "TNM", new String[] {"EW4", "CG0"}));
        mrtStations.add(new MrtStation("Bedok", "BDK", new String[] {"EW5"}));
        mrtStations.add(new MrtStation("Kembangan", "KEM", new String[] {"EW6"}));
        mrtStations.add(new MrtStation("Eunos", "EUN", new String[] {"EW7"}));
        mrtStations.add(new MrtStation("Paya Lebar", "PYL", new String[] {"EW8", "CC9"}));
        mrtStations.add(new MrtStation("Aljunied", "ALJ", new String[] {"EW9"}));
        mrtStations.add(new MrtStation("Kallang", "KAL", new String[] {"EW10"}));
        mrtStations.add(new MrtStation("Lavender", "LVR", new String[] {"EW11"}));
        mrtStations.add(new MrtStation("Bugis", "BGS", new String[] {"EW12", "DT14"}));
        mrtStations.add(new MrtStation("City Hall", "CTH", new String[] {"EW13", "NS25"}));
        mrtStations.add(new MrtStation("Raffles Place", "RFP", new String[] {"EW14", "NS26"}));
        mrtStations.add(new MrtStation("Tanjong Pagar", "TPG", new String[] {"EW15"}));
        mrtStations.add(new MrtStation("Outram Park", "OTP", new String[] {"EW16", "NE3"}));
        mrtStations.add(new MrtStation("Tiong Bahru", "TIB", new String[] {"EW17"}));
        mrtStations.add(new MrtStation("Redhill", "RDH", new String[] {"EW18"}));
        mrtStations.add(new MrtStation("Queenstown", "QUE", new String[] {"EW19"}));
        mrtStations.add(new MrtStation("Commonwealth", "COM", new String[] {"EW20"}));
        mrtStations.add(new MrtStation("Buona Vista", "BNV", new String[] {"EW21", "CC22"}));
        mrtStations.add(new MrtStation("Dover", "DVR", new String[] {"EW22"}));
        mrtStations.add(new MrtStation("Clementi", "CLE", new String[] {"EW23"}));
        mrtStations.add(new MrtStation("Jurong East", "JUR", new String[] {"EW24", "NS1"}));
        mrtStations.add(new MrtStation("Chinese Garden", "CNG", new String[] {"EW25"}));
        mrtStations.add(new MrtStation("Lakeside", "LKS", new String[] {"EW26"}));
        mrtStations.add(new MrtStation("Boon Lay", "BNL", new String[] {"EW27"}));
        mrtStations.add(new MrtStation("Pioneer", "PNR", new String[] {"EW28"}));
        mrtStations.add(new MrtStation("Joo Koon", "JKN", new String[] {"EW29"}));
        mrtStations.add(new MrtStation("Gul Circle", "GCL", new String[] {"EW30"}));
        mrtStations.add(new MrtStation("Tuas Crescent", "TCR", new String[] {"EW31"}));
        mrtStations.add(new MrtStation("Tuas West Road", "TWR", new String[] {"EW32"}));
        mrtStations.add(new MrtStation("Tuas Link", "TLK", new String[] {"EW33"}));
        mrtStations.add(new MrtStation("Tanah Merah", "TNM", new String[] {"CG0"}));
        mrtStations.add(new MrtStation("Expo", "XPO", new String[] {"CG1", "DT35"}));
        mrtStations.add(new MrtStation("Changi Airport", "CGA", new String[] {"CG2"}));
        mrtStations.add(new MrtStation("HarbourFront", "HBF", new String[] {"NE1", "CC29"}));
        mrtStations.add(new MrtStation("Outram Park", "OTP", new String[] {"NE3", "EW16"}));
        mrtStations.add(new MrtStation("Chinatown", "CNT", new String[] {"NE4", "DT19"}));
        mrtStations.add(new MrtStation("Clarke Quay", "CQY", new String[] {"NE5"}));
        mrtStations.add(new MrtStation("Dhoby Ghaut", "DBG", new String[] {"NE6", "NS24", "CC1"}));
        mrtStations.add(new MrtStation("Little India", "LTI", new String[] {"NE7", "DT12"}));
        mrtStations.add(new MrtStation("Farrer Park", "FRP", new String[] {"NE8"}));
        mrtStations.add(new MrtStation("Boon Keng", "BNK", new String[] {"NE9"}));
        mrtStations.add(new MrtStation("Potong Pasir", "PTP", new String[] {"NE10"}));
        mrtStations.add(new MrtStation("Woodleigh", "WLH", new String[] {"NE11"}));
        mrtStations.add(new MrtStation("Serangoon", "SER", new String[] {"NE12", "CC13"}));
        mrtStations.add(new MrtStation("Kovan", "KVN", new String[] {"NE13"}));
        mrtStations.add(new MrtStation("Hougang", "HGN", new String[] {"NE14"}));
        mrtStations.add(new MrtStation("Buangkok", "BGK", new String[] {"NE15"}));
        mrtStations.add(new MrtStation("Sengkang", "SKG", new String[] {"NE16"}));
        mrtStations.add(new MrtStation("Punggol", "PGL", new String[] {"NE17"}));
        mrtStations.add(new MrtStation("Dhoby Ghaut", "DBG", new String[] {"CC1", "NS24", "NE6"}));
        mrtStations.add(new MrtStation("Bras Basah", "BBS", new String[] {"CC2"}));
        mrtStations.add(new MrtStation("Esplanade", "EPN", new String[] {"CC3"}));
        mrtStations.add(new MrtStation("Promenade", "PMN", new String[] {"CC4", "DT15", "CE0"}));
        mrtStations.add(new MrtStation("Nicoll Highway", "NCH", new String[] {"CC5"}));
        mrtStations.add(new MrtStation("Stadium", "SDM", new String[] {"CC6"}));
        mrtStations.add(new MrtStation("Mountbatten", "MBT", new String[] {"CC7"}));
        mrtStations.add(new MrtStation("Dakota", "DKT", new String[] {"CC8"}));
        mrtStations.add(new MrtStation("Paya Lebar", "PYL", new String[] {"CC9", "EW8"}));
        mrtStations.add(new MrtStation("MacPherson", "MPS", new String[] {"CC10", "DT26"}));
        mrtStations.add(new MrtStation("Tai Seng", "TSG", new String[] {"CC11"}));
        mrtStations.add(new MrtStation("Bartley", "BLY", new String[] {"CC12"}));
        mrtStations.add(new MrtStation("Serangoon", "SER", new String[] {"CC13", "NE12"}));
        mrtStations.add(new MrtStation("Lorong Chuan", "LRC", new String[] {"CC14"}));
        mrtStations.add(new MrtStation("Bishan", "BSH", new String[] {"CC15", "NS17"}));
        mrtStations.add(new MrtStation("Marymount", "MRM", new String[] {"CC16"}));
        mrtStations.add(new MrtStation("Caldecott", "CDT", new String[] {"CC17"}));
        mrtStations.add(new MrtStation("Botanic Gardens", "BTN", new String[] {"CC19", "DT9"}));
        mrtStations.add(new MrtStation("Farrer Road", "FRR", new String[] {"CC20"}));
        mrtStations.add(new MrtStation("Holland Village", "HLV", new String[] {"CC21"}));
        mrtStations.add(new MrtStation("Buona Vista", "BNV", new String[] {"CC22", "EW21"}));
        mrtStations.add(new MrtStation("one-north", "ONH", new String[] {"CC23"}));
        mrtStations.add(new MrtStation("Kent Ridge", "KRG", new String[] {"CC24"}));
        mrtStations.add(new MrtStation("Haw Par Villa", "HPV", new String[] {"CC25"}));
        mrtStations.add(new MrtStation("Pasir Panjang", "PPJ", new String[] {"CC26"}));
        mrtStations.add(new MrtStation("Labrador Park", "LBD", new String[] {"CC27"}));
        mrtStations.add(new MrtStation("Telok Blangah", "TLB", new String[] {"CC28"}));
        mrtStations.add(new MrtStation("HarbourFront", "HBF", new String[] {"CC29", "NE1"}));
        mrtStations.add(new MrtStation("Promenade", "PMN", new String[] {"CE0", "DT15", "CC4"}));
        mrtStations.add(new MrtStation("Bayfront", "BFT", new String[] {"CE1", "DT16"}));
        mrtStations.add(new MrtStation("Marina Bay", "MRB", new String[] {"CE2", "NS27"}));
        mrtStations.add(new MrtStation("Bukit Panjang", "BPJ", new String[] {"DT1"}));
        mrtStations.add(new MrtStation("Cashew", "CSW", new String[] {"DT2"}));
        mrtStations.add(new MrtStation("Hillview", "HVW", new String[] {"DT3"}));
        mrtStations.add(new MrtStation("BeautyWorld", "BTW", new String[] {"DT5"}));
        mrtStations.add(new MrtStation("King Albert Park", "KAP", new String[] {"DT6"}));
        mrtStations.add(new MrtStation("Sixth Avenue", "SAV", new String[] {"DT7"}));
        mrtStations.add(new MrtStation("Tan Kah Kee", "TKK", new String[] {"DT8"}));
        mrtStations.add(new MrtStation("Botanic Gardens", "BTN", new String[] {"DT9", "CC19"}));
        mrtStations.add(new MrtStation("Stevens", "STV", new String[] {"DT10"}));
        mrtStations.add(new MrtStation("Newton", "NEW", new String[] {"DT11", "NS21"}));
        mrtStations.add(new MrtStation("Little India", "LTI", new String[] {"DT12", "NE7"}));
        mrtStations.add(new MrtStation("Rochor", "RCR", new String[] {"DT13"}));
        mrtStations.add(new MrtStation("Bugis", "BGS", new String[] {"DT14", "EW12"}));
        mrtStations.add(new MrtStation("Promenade", "PMN", new String[] {"DT15", "CC4", "CE0"}));
        mrtStations.add(new MrtStation("Bayfront", "BFT", new String[] {"DT16", "CE1"}));
        mrtStations.add(new MrtStation("Downtown", "DTN", new String[] {"DT17"}));
        mrtStations.add(new MrtStation("Telok Ayer", "TLA", new String[] {"DT18"}));
        mrtStations.add(new MrtStation("Chinatown", "CNT", new String[] {"DT19", "NE4"}));
        mrtStations.add(new MrtStation("Fort Canning", "FCN", new String[] {"DT20"}));
        mrtStations.add(new MrtStation("Bencoolen", "BCL", new String[] {"DT21"}));
        mrtStations.add(new MrtStation("Jalan Besar", "JLB", new String[] {"DT22"}));
        mrtStations.add(new MrtStation("Bendemeer", "BDM", new String[] {"DT23"}));
        mrtStations.add(new MrtStation("Geylang Bahru", "GLB", new String[] {"DT24"}));
        mrtStations.add(new MrtStation("Mattar", "MTR", new String[] {"DT25"}));
        mrtStations.add(new MrtStation("MacPherson", "MPS", new String[] {"DT26", "CC10"}));
        mrtStations.add(new MrtStation("Ubi", "UBI", new String[] {"DT27"}));
        mrtStations.add(new MrtStation("Kaki Bukit", "KKB", new String[] {"DT28"}));
        mrtStations.add(new MrtStation("Bedok North", "BDN", new String[] {"DT29"}));
        mrtStations.add(new MrtStation("Bedok Reservoir", "BDR", new String[] {"DT30"}));
        mrtStations.add(new MrtStation("Tampines West", "TPW", new String[] {"DT31"}));
        mrtStations.add(new MrtStation("Tampines", "TAM", new String[] {"DT32", "EW2"}));
        mrtStations.add(new MrtStation("Tampines East", "TPE", new String[] {"DT33"}));
        mrtStations.add(new MrtStation("Upper Changi", "UPC", new String[] {"DT34"}));
        mrtStations.add(new MrtStation("Expo", "XPO", new String[] {"DT35", "CG1"}));
    }

    /**
     * The number represent the time it takes to get to the MrtStation
     * from the first stop. This information is needed for Dijkstra's Algorithm
     * This method acts as data storage.
     */
    private void populateTrainTimings() {{
        trainTimings.add(0);
        trainTimings.add(3);
        trainTimings.add(5);
        trainTimings.add(9);
        trainTimings.add(11);
        trainTimings.add(16);
        trainTimings.add(18);
        trainTimings.add(21);
        trainTimings.add(23);
        trainTimings.add(26);
        trainTimings.add(30);
        trainTimings.add(32);
        trainTimings.add(38);
        trainTimings.add(40);
        trainTimings.add(42);
        trainTimings.add(44);
        trainTimings.add(46);
        trainTimings.add(48);
        trainTimings.add(50);
        trainTimings.add(52);
        trainTimings.add(54);
        trainTimings.add(55);
        trainTimings.add(57);
        trainTimings.add(59);
        trainTimings.add(61);
        trainTimings.add(63);
        trainTimings.add(0);
        trainTimings.add(3);
        trainTimings.add(5);
        trainTimings.add(9);
        trainTimings.add(12);
        trainTimings.add(15);
        trainTimings.add(17);
        trainTimings.add(20);
        trainTimings.add(22);
        trainTimings.add(24);
        trainTimings.add(26);
        trainTimings.add(28);
        trainTimings.add(30);
        trainTimings.add(32);
        trainTimings.add(34);
        trainTimings.add(36);
        trainTimings.add(39);
        trainTimings.add(41);
        trainTimings.add(43);
        trainTimings.add(45);
        trainTimings.add(47);
        trainTimings.add(49);
        trainTimings.add(52);
        trainTimings.add(57);
        trainTimings.add(60);
        trainTimings.add(62);
        trainTimings.add(65);
        trainTimings.add(67);
        trainTimings.add(70);
        trainTimings.add(73);
        trainTimings.add(75);
        trainTimings.add(77);
        trainTimings.add(80);
        trainTimings.add(0);
        trainTimings.add(3);
        trainTimings.add(8);
        trainTimings.add(0);
        trainTimings.add(3);
        trainTimings.add(5);
        trainTimings.add(7);
        trainTimings.add(9);
        trainTimings.add(11);
        trainTimings.add(13);
        trainTimings.add(15);
        trainTimings.add(17);
        trainTimings.add(19);
        trainTimings.add(21);
        trainTimings.add(23);
        trainTimings.add(26);
        trainTimings.add(28);
        trainTimings.add(30);
        trainTimings.add(32);
        trainTimings.add(0);
        trainTimings.add(2);
        trainTimings.add(4);
        trainTimings.add(6);
        trainTimings.add(8);
        trainTimings.add(10);
        trainTimings.add(12);
        trainTimings.add(14);
        trainTimings.add(16);
        trainTimings.add(18);
        trainTimings.add(20);
        trainTimings.add(22);
        trainTimings.add(25);
        trainTimings.add(27);
        trainTimings.add(29);
        trainTimings.add(32);
        trainTimings.add(33);
        trainTimings.add(38);
        trainTimings.add(40);
        trainTimings.add(43);
        trainTimings.add(45);
        trainTimings.add(47);
        trainTimings.add(49);
        trainTimings.add(51);
        trainTimings.add(53);
        trainTimings.add(56);
        trainTimings.add(58);
        trainTimings.add(60);
        trainTimings.add(0);
        trainTimings.add(2);
        trainTimings.add(4);
        trainTimings.add(0);
        trainTimings.add(2);
        trainTimings.add(4);
        trainTimings.add(8);
        trainTimings.add(10);
        trainTimings.add(12);
        trainTimings.add(14);
        trainTimings.add(16);
        trainTimings.add(18);
        trainTimings.add(20);
        trainTimings.add(22);
        trainTimings.add(23);
        trainTimings.add(25);
        trainTimings.add(27);
        trainTimings.add(29);
        trainTimings.add(31);
        trainTimings.add(32);
        trainTimings.add(33);
        trainTimings.add(35);
        trainTimings.add(37);
        trainTimings.add(39);
        trainTimings.add(41);
        trainTimings.add(43);
        trainTimings.add(45);
        trainTimings.add(47);
        trainTimings.add(49);
        trainTimings.add(51);
        trainTimings.add(53);
        trainTimings.add(55);
        trainTimings.add(57);
        trainTimings.add(59);
        trainTimings.add(61);
        trainTimings.add(64);
        trainTimings.add(66);
    }

    }

    private void populateStationCodeToIndex() {
        for(int i=0; i<mrtStations.size(); i++) {
            MrtStation mrtStation = mrtStations.get(i);
            String stationCode = mrtStation.getStationCode(0);
            stationCodeToIndex.put(stationCode, i);
        }
    }

    private void populateNameToIndex(){
        for(int i = 0; i<mrtStations.size(); i++){
            MrtStation mrtStation = mrtStations.get(i);
            String name = mrtStation.getName();
            nameToIndex.put(name, i);
        }
    }

    private void populateAdjList(){
        for(int i = 0; i<mrtStations.size(); i++){
            addEdgeList(i);
        }
    }

    //Only used by populateAdjList() method
    private void addEdgeList(int mrtStationIndex){
        MrtStation mrtStation = mrtStations.get(mrtStationIndex);
        int numInterchange = mrtStation.getNumInterchange();
        ArrayList<IntPair> edgeList = new ArrayList<IntPair>();

        for(int i = 0; i < numInterchange; i++) {
            String stationCode = mrtStation.getStationCode(i);
            //the index of the same MrtStation but in line specified by i.
            int stationIndex = stationCodeToIndex.get(stationCode);
            addNeighbourDetails(edgeList, i, stationIndex);
        }
        adjList.add(edgeList);
    }

    //Only used by addEdgeList.
    //Note: addEdgeList is only used by populateAdjList() method
    private void addNeighbourDetails(ArrayList<IntPair> edgeList, int adjIndex, int stationIndex) {

        //add neighbour whose index is lower if neighbour exist.
        if(stationIndex > 0) {
            int previousStationIndex = stationIndex - 1;
            addNeighbour(edgeList, stationIndex, previousStationIndex);
        }

        //add neighbour whose index is higher if neighbour exist.
        if(stationIndex < mrtStations.size() - 1) {
            int nextStationIndex = stationIndex + 1;
            addNeighbour(edgeList, stationIndex, nextStationIndex);
        }
    }

    /**
     * Given two mrt station, first check if they belong to the same line
     * if they don't, do nothing. If they do, add the relevant detail into
     * the adjacency list
     *
     * This method is only used by addNeighbourDetail.AddNeighbourDetails is only
     * used by addEdgeList which is only used by populateAdjList() method.
     */
    private void addNeighbour(ArrayList<IntPair> edgeList, int sourceIndex, int destIndex) {
        MrtStation sourceMrt = mrtStations.get(sourceIndex);
        MrtStation destMrt = mrtStations.get(destIndex);

        String sourceLineName = sourceMrt.getMrtLine(0);
        String destLineName = destMrt.getMrtLine(0);

        //Check if the two mrt are of the same line.
        if(!sourceLineName.equals(destLineName)) {
            //do nothing
            //these two MRT are not from the same line, hence they are not neighbour.
            return;
        }else {
            int sourceTiming = trainTimings.get(sourceIndex);
            int destTiming = trainTimings.get(destIndex);
            int duration = 0;
            if(sourceTiming > destTiming) {
                duration = sourceTiming - destTiming;
            }else { //destTiming >= sourceTiming;
                duration = destTiming - sourceTiming;
            }
            IntPair intPair = new IntPair(destIndex, duration);
            edgeList.add(intPair);
        }
    }

    /**
     * Prepare all the relevant information in order to run Dijkstra's Algoritm
     */
    private void initialise(){
        populateMrtStations();
        populateTrainTimings();
        populateStationCodeToIndex();
        populateNameToIndex();
        populateAdjList();
    }

    /**
     * Return the minimum time require to travel to all station
     * for the specified souce station
     * @return
     */
    private int[] getMinTime(int sourceIndex) {
        ArrayList<IntPair> minTimeList = new ArrayList<IntPair>();
        int[] parentIndexPointer = new int[mrtStations.size()];

        for(int i = 0; i < mrtStations.size(); i++) {
            //set all travelDuration(weight) to largest value;
            int travelDuration = Integer.MAX_VALUE;
            IntPair intPair = new IntPair(i, travelDuration);
            minTimeList.add(intPair);

            //set parent pointer to -1;
            parentIndexPointer[i] = -1;
        }

        //create minHeap and populate it
        PriorityQueue<IntPair> minHeap = new PriorityQueue<IntPair>(1, new IntPair(0, 0));
        for(int i = 0; i < mrtStations.size(); i++) {
            minHeap.add(minTimeList.get(i));
        }

        minTimeList.get(sourceIndex).travelDuration = 0;
        MrtStation sourceMrt = mrtStations.get(sourceIndex);

        for(int i = 0; i < sourceMrt.getNumInterchange(); i++) {
            int mrtIndex = stationCodeToIndex.get(sourceMrt.getStationCode(i));
            minTimeList.get(mrtIndex).travelDuration = 0;
            minHeap.remove(minTimeList.get(mrtIndex));
            minHeap.add(minTimeList.get(mrtIndex));
        }
        //update all duplicate
        minHeap.remove(minTimeList.get(sourceIndex));
        minHeap.add(minTimeList.get(sourceIndex));

        //Dijkstra's algorithm
        while(minHeap.size() != 0) {
            IntPair currIntPair = minHeap.poll();
            int currIndex = currIntPair.mrtIndex;
            int currTravelDuration = currIntPair.travelDuration;

            //iterate through the neighbor, update their traveling time.
            ArrayList<IntPair> edgeList = adjList.get(currIndex);
            for(int i = 0; i < edgeList.size(); i++) {
                int destIndex = edgeList.get(i).mrtIndex;
                int travelInterval = edgeList.get(i).travelDuration;

                int oldTravelDuration = minTimeList.get(destIndex).travelDuration;
                int newTravelDuration = currTravelDuration + travelInterval;
                if(!isSameLine(parentIndexPointer[currIndex], destIndex)) {
                    newTravelDuration += INTERCHANGE_TRANSFER_TIME;
                }

                if(newTravelDuration < oldTravelDuration) {
                    MrtStation mrt = mrtStations.get(destIndex);
                    int numInterchange = mrt.getNumInterchange();

                    //update all duplicates of mrt for those that are interchange
                    for(int j = 0; j < numInterchange; j++) {
                        int destinationIndex = stationCodeToIndex.get(mrt.getStationCode(j));
                        minTimeList.get(destinationIndex).travelDuration = newTravelDuration;
                        minHeap.remove(minTimeList.get(destinationIndex));
                        minHeap.add(minTimeList.get(destinationIndex));
                        parentIndexPointer[destinationIndex] = currIndex;
                    }
                }
            }
        }

        int[] minDurations = new int[minTimeList.size()];
        for(int i = 0; i < minTimeList.size(); i++) {
            minDurations[i] = minTimeList.get(i).travelDuration;
        }

        return minDurations;
    }

    private int[] getMinTotalTime(int[] sourceIndexes) {
        int[] minTotalTimeList = new int[mrtStations.size()];
        for(int i = 0; i < sourceIndexes.length; i++) {
            int[] tempTimeList = getMinTime(sourceIndexes[i]);
            for(int j = 0; j < tempTimeList.length; j++) {
                minTotalTimeList[j] += tempTimeList[j];
            }
        }
        return minTotalTimeList;
    }

    private int[] getMinTotalTime(String[] mrtStationNames){
        int[] minTotalTimeList = new int[mrtStations.size()];
        for(int i = 0; i < mrtStationNames.length; i++) {
            String mrtStationName = mrtStationNames[i];
            int mrtStationIndex = nameToIndex.get(mrtStationName);
            int[] tempTimeList = getMinTime(mrtStationIndex);
            for(int j = 0; j < tempTimeList.length; j++) {
                minTotalTimeList[j] += tempTimeList[j];
            }
        }
        return minTotalTimeList;
    }

    //Duplicates no longer in mrt string
    private ArrayList<String> getSortedMrtList(String[] mrtStationNames){
        return getSortedMrtListFromTotalTimeList(getMinTotalTime(mrtStationNames));
    }

    /**
     * The only public method in this entire code apart from the constructor
     * Return a list of sorted mrt stations base on total travelling time
     * from stations indicated in mrtStationNames.
     * @param mrtStationNames
     * @return
     */
    public ArrayList<String> getSortedMrtList(ArrayList<String> mrtStationNames){
        ArrayList<String> validStationList = removeInvalidMrtStation(mrtStationNames);
        String[] mrtStations = new String[validStationList.size()];
        for(int i = 0; i < mrtStations.length; i++){
            mrtStations[i] = validStationList.get(i);
        }
        return getSortedMrtList(mrtStations);
    }


    //Return a sorted list of mrt Stations base on minMrtTimes array
    private ArrayList<String> getSortedMrtListFromTotalTimeList(int[] minMrtTimes) {
        ArrayList<String> sortedMrtStations = new ArrayList<String>();

        PriorityQueue<IntPair> minHeap = new PriorityQueue<IntPair>(1, new IntPair(0, 0));
        for(int i = 0; i < minMrtTimes.length; i++) {
            minHeap.add(new IntPair(i, minMrtTimes[i]));
        }

        while(minHeap.size() != 0) {
            IntPair intPair = minHeap.poll();
            int stationIndex = intPair.mrtIndex;
            MrtStation mrt = mrtStations.get(stationIndex);
            sortedMrtStations.add(mrt.getName());
        }
        return removeDuplicateMrt(sortedMrtStations);
    }

    private boolean isSameLine(int mrtIndexOne, int mrtIndexTwo) {
        if(mrtIndexOne == -1 || mrtIndexTwo == -1) {
            return true;
        }
        MrtStation mrtOne = mrtStations.get(mrtIndexOne);
        int numInterchangeOne = mrtOne.getNumInterchange();

        MrtStation mrtTwo = mrtStations.get(mrtIndexTwo);
        int numInterchangeTwo = mrtTwo.getNumInterchange();

        for(int i = 0; i < numInterchangeOne; i++) {
            for(int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtOne.getMrtLine(i);
                String mrtLineTwo = mrtTwo.getMrtLine(j);
                if(mrtLineOne.equals(mrtLineTwo)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param mrtStations
     * @return the mrtStation list without duplicate. Order remains the same.
     */
    private ArrayList<String> removeDuplicateMrt(ArrayList<String> mrtStations){
        HashMap<String, Boolean> mrtList = new HashMap<String, Boolean>();
        ArrayList<String> noDuplicateMrtList = new ArrayList<String>();
        for(int i = 0; i < mrtStations.size(); i++){
            String mrtName = mrtStations.get(i);
            if(!mrtList.containsKey(mrtName)){
                mrtList.put(mrtName, true);
                noDuplicateMrtList.add(mrtName);
            }
        }
        return noDuplicateMrtList;
    }

    private ArrayList<String> removeInvalidMrtStation(ArrayList<String> mrtStations){
        ArrayList<String> validMrtStations = new ArrayList<String>();
        for(int i = 0; i < mrtStations.size(); i++){
            if(nameToIndex.containsKey(mrtStations.get(i))){
                validMrtStations.add(mrtStations.get(i));
            }
        }
        return validMrtStations;
    }

    //For debugging purpose only.
    private void printAdjList(){
        for(int i = 0; i < adjList.size(); i++) {
            MrtStation mrt = mrtStations.get(i);
            String name = mrt.getName();
            String stationCode = mrt.getStationCode(0);
            System.out.print(i+": "+name+" ["+stationCode+"]: ");
            ArrayList<IntPair> adjRow = adjList.get(i);
            for(int j = 0; j < adjRow.size(); j++) {
                int neighbourIndex = adjRow.get(j).mrtIndex;
                int travelInterval = adjRow.get(j).travelDuration;
                String neighbourCode = mrtStations.get(neighbourIndex).getStationCode(0);
                String neighbourName = mrtStations.get(neighbourIndex).getName();
                System.out.print("("+neighbourName+" ["+neighbourCode+"], "+travelInterval+") ");
            }
            System.out.println();
        }
    }

    private void printSingleMrtTiming(int sourceIndex){
        int[] minTable = getMinTime(sourceIndex);
        for(int i = 0; i < minTable.length; i++){
            MrtStation mrtStation = mrtStations.get(i);
            String stationName = mrtStation.getName();
            System.out.println(i+", "+stationName+": "+minTable[i]);
        }
    }

    private void printSortedMrt(ArrayList<String> stationNames){
        ArrayList<String> sortedList = getSortedMrtList(stationNames);
        for(int i = 0; i < sortedList.size(); i++){
            System.out.println(i+" = "+sortedList.get(i));
        }
    }

    public ArrayList<String> getMrtStationNames (){
        ArrayList<String> stationNames = new ArrayList<String>();
        for(int i = 0; i < mrtStations.size(); i++){
            stationNames.add(mrtStations.get(i).getName());
        }
        return stationNames;
    }

    public ArrayList<String> getMrtStationShortNames (){
        ArrayList<String> stationShortNames = new ArrayList<String>();
        for(int i = 0; i < mrtStations.size(); i++){
            stationShortNames.add(mrtStations.get(i).getShortName());
        }
        return stationShortNames;
    }

    public ArrayList<ArrayList<String>> getMrtLineNames(){
        ArrayList<ArrayList<String>> stationLineNames = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < mrtStations.size(); i++){
            MrtStation mrtStation = mrtStations.get(i);
            ArrayList<String> singleStationLines = new ArrayList<String>();
            for(int j = 0; j < mrtStation.getNumInterchange(); j++){
                singleStationLines.add(mrtStation.getMrtLine(j));
            }
            stationLineNames.add(singleStationLines);
        }
        return stationLineNames;
    }

    public ArrayList<ArrayList<Integer>> getMrtLineNumbers(){
        ArrayList<ArrayList<Integer>> stationLineNumbers = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < mrtStations.size(); i++){
            MrtStation mrtStation = mrtStations.get(i);
            ArrayList<Integer> singleStationLineNumbers = new ArrayList<Integer>();
            for(int j = 0; j < mrtStation.getNumInterchange(); j++){
                singleStationLineNumbers.add(mrtStation.getStationNumber(j));
            }
            stationLineNumbers.add(singleStationLineNumbers);
        }
        return stationLineNumbers;
    }


}
