# YewOnn
###### \java\seedu\address\commons\events\ui\LocateMrtCommandEvent.java
``` java
public class LocateMrtCommandEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LocateMrtCommandEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\FindByPhoneCommand.java
``` java
public class FindByPhoneCommand extends Command {

    public static final String COMMAND_WORD = "findByPhone";
    public static final String COMMAND_ALIAS = "fbp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "91234567";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindByPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByPhoneCommand // instanceof handles nulls
                && this.predicate.equals(((FindByPhoneCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MeetingLocationCommand.java
``` java
/**
 * Display a list of mrt stations that minimises the accumulated travelling time
 * of every specified individual
 */
public class MeetingLocationCommand extends Command {
    public static final String COMMAND_WORD = "meetingLocation";
    public static final String COMMAND_ALIAS = "ml";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": takes in a list of indices of persons who appeared in the last list and get the "
            + "most convenient station for them.\n"
            + "Parameters: Indices separated by spaces (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5 7 8";

    public static final String MESSAGE_MEETING_LOCATION_SUCCESS = "meeting location successfully arranged!";

    private final Index[] listOfIndex;

    public MeetingLocationCommand(int[] listOfIndex) {
        this.listOfIndex = new Index[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            this.listOfIndex[i] = Index.fromOneBased(listOfIndex[i]);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < listOfIndex.length; i++) {
            if (listOfIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        ArrayList<String> mrtStations = new ArrayList<String>();
        ArrayList<String> peopleNameList = new ArrayList<String>();
        for (int i = 0; i < listOfIndex.length; i++) {
            String mrtStation = model.getAddressBook().getPersonList()
                    .get(listOfIndex[i].getZeroBased()).getMrt().value;
            String peopleName = model.getAddressBook().getPersonList()
                    .get(listOfIndex[i].getZeroBased()).getName().fullName;
            mrtStations.add(mrtStation);
            peopleNameList.add(peopleName);
        }

        MrtMapLogic mrtMapLogic = new MrtMapLogic();
        ArrayList<String> sortedStationNames = mrtMapLogic.getSortedMrtList(mrtStations);
        //the first element in the sorted list contains the best meeting location.
        String meetStation = sortedStationNames.get(0);
        MrtMapDisplay mrtMapDisplay = new MrtMapDisplay();
        mrtMapDisplay.displayUserInfo(meetStation, mrtStations);
        String userInfo = getMrtInfo(peopleNameList, mrtStations, meetStation);
        return new CommandResult(String.format(MESSAGE_MEETING_LOCATION_SUCCESS)
                + userInfo);

    }

    public int[] getSortedZeroBasedIndex() {
        int[] thisIndexList = new int[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            thisIndexList[i] = listOfIndex[i].getZeroBased();
        }
        Arrays.sort(thisIndexList);
        return thisIndexList;
    }

    /**
     * Returns the info of schedule to be shown to the user later.
     * peopleNames is the list of people
     * mrtStations is the list of mrt station. The mrt station of a particular person
     * is store in the same index as in peopleNames
     * Meet Station is the Mrt Location to meet
     */
    public String getMrtInfo(ArrayList<String> peopleNames, ArrayList<String> mrtStations,
                              String meetStation) {

        MrtMapLogic mrtMapLogic = new MrtMapLogic();

        String toShow = "\nTop Meeting Location: " + meetStation;
        for (int i = 0; i < peopleNames.size() && i < mrtStations.size(); i++) {
            toShow += "\n";
            String mrtStationName = mrtStations.get(i);
            int travelTime = mrtMapLogic.getTravelTime(mrtStationName, meetStation);
            String stringTime = Integer.toString(travelTime);
            String personName = peopleNames.get(i);
            String currLine = personName + " (from " + mrtStationName + ") requires "
                    + stringTime + " minutes.";
            toShow += currLine;
        }
        return toShow;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArrangeCommand // instanceof handles nulls
                && Arrays.equals(this.getSortedZeroBasedIndex(), ((ArrangeCommand) other).getSortedZeroBasedIndex()));
        // state check
    }
}
```
###### \java\seedu\address\logic\MrtMapLogic.java
``` java
/**
 * The java class that computes the list of mrt stations that reduces the total travelling
 * time. The timing is base on actual mrt data.
 */
public class MrtMapLogic {
    //require five minutes to transfer station
    private static final int INTERCHANGE_TRANSFER_TIME = 5;

    private ArrayList<MrtStation> mrtStations = new ArrayList<MrtStation>();
    private ArrayList<Integer> trainTimings = new ArrayList<Integer>();

    //the index of the mrt in the mrtList
    private HashMap<String, Integer> stationCodeToIndex = new HashMap<String, Integer>();
    private HashMap<String, Integer> nameToIndex = new HashMap<String, Integer>();

    //adjacency list for DijkStra's Algorithm
    private ArrayList<ArrayList<IntPair>> adjList = new ArrayList<ArrayList<IntPair>>();

    public MrtMapLogic() {
        initialise();
    }

    /**
     * MrtStation object represents a physical Mrt Station's information
     */
    class MrtStation {
        private String name;
        private String shortName;

        /*ne line can have more than one station code
        e.g. DhobyGhaut has NS24, NE6, CC1*/
        private String[] stationCodes;

        public MrtStation(String name, String shortName, String[] stationCodes) {
            this.name = name;
            this.shortName = shortName;
            this.stationCodes = new String[stationCodes.length];
            for (int i = 0; i < stationCodes.length; i++) {
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
            for (int i = 0; i < getNumInterchange(); i++) {
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
        protected int mrtIndex;
        protected int travelDuration;

        public IntPair(int mrtIndex, int travelInterval) {
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
        populateMrtStationsPartOne();
        populateMrtStationsPartTwo();
        populateMrtStationsPartThree();
    }

    /**
     * Split to three parts to reduce length of code for each metohd...
     */
    private void populateMrtStationsPartOne() {
        mrtStations.add(new MrtStation("Jurong East", "JUR", new String[] {"NS1", "EW24"}));
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
    }

    /**
     * Split to three parts to reduce length of code for each metohd...
     */
    private void populateMrtStationsPartTwo() {
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
    }

    /**
     * Split to three parts to reduce length of code for each metohd...
     */
    private void populateMrtStationsPartThree() {
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
    private void populateTrainTimings() {
        populateTrainTimingsPartOne();
        populateTrainTimingsPartTwo();
        populateTrainTimingsPartThree();
    }

    /**
     * Split the populateTrainTimings method into three
     * parts to avoid having "really long methods"
     */
    private void populateTrainTimingsPartOne() {
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
    }

    /**
     * Split the populateTrainTimings method into three
     * parts to avoid having "really long methods"
     */
    private void populateTrainTimingsPartTwo() {
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
    }

    /**
     * Split the populateTrainTimings method into three
     * parts to avoid having "really long methods"
     */
    private void populateTrainTimingsPartThree() {
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

    /**
     * Populate the hashmap populateStationCodeToIndex
     * Note that before this method is called, mrtStations must already be filled
     * i.e., populateMrtStations() must already be called;
     */
    private void populateStationCodeToIndex() {
        for (int i = 0; i < mrtStations.size(); i++) {
            MrtStation mrtStation = mrtStations.get(i);
            String stationCode = mrtStation.getStationCode(0);
            stationCodeToIndex.put(stationCode, i);
        }
    }

    /**
     * Populate the hashmap nameToIndex with relevant info.
     * This method only works properly after populatMrtStations() method
     */
    private void populateNameToIndex() {
        for (int i = 0; i < mrtStations.size(); i++) {
            MrtStation mrtStation = mrtStations.get(i);
            String name = mrtStation.getName();
            nameToIndex.put(name, i);
        }
    }

    /**
     * Generate the adjacency list to be used by Dijkstra's Algorithm
     */
    private void populateAdjList() {
        for (int i = 0; i < mrtStations.size(); i++) {
            addEdgeList(i);
        }
    }

    /**
     * addEdgeList is only used by populateAdjList() method
     * it fills up the information for all the edges of one particular
     * mrt station (vertex)
     * @param mrtStationIndex
     */
    private void addEdgeList(int mrtStationIndex) {
        MrtStation mrtStation = mrtStations.get(mrtStationIndex);
        int numInterchange = mrtStation.getNumInterchange();
        ArrayList<IntPair> edgeList = new ArrayList<IntPair>();

        for (int i = 0; i < numInterchange; i++) {
            String stationCode = mrtStation.getStationCode(i);
            //the index of the same MrtStation but in line specified by i.
            int stationIndex = stationCodeToIndex.get(stationCode);
            addNeighbourDetails(edgeList, stationIndex);
        }
        adjList.add(edgeList);
    }

    /**
     * Only used by addEdgeList
     * Not: addEdgeList is only used by populateAdjList() method
     * @param edgeList
     * @param stationIndex
     */
    private void addNeighbourDetails(ArrayList<IntPair> edgeList, int stationIndex) {

        //add neighbour whose index is lower if neighbour exist.
        if (stationIndex > 0) {
            int previousStationIndex = stationIndex - 1;
            addNeighbour(edgeList, stationIndex, previousStationIndex);
        }

        //add neighbour whose index is higher if neighbour exist.
        if (stationIndex < mrtStations.size() - 1) {
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
        if (!sourceLineName.equals(destLineName)) {
            //do nothing
            //these two MRT are not from the same line, hence they are not neighbour.
            return;
        } else {
            int sourceTiming = trainTimings.get(sourceIndex);
            int destTiming = trainTimings.get(destIndex);
            int duration = 0;
            if (sourceTiming > destTiming) {
                duration = sourceTiming - destTiming;
            } else { //destTiming >= sourceTiming;
                duration = destTiming - sourceTiming;
            }
            IntPair intPair = new IntPair(destIndex, duration);
            edgeList.add(intPair);
        }
    }

    /**
     * Prepare all the relevant information in order to run Dijkstra's Algoritm
     */
    private void initialise() {
        populateMrtStations();
        populateTrainTimings();
        populateStationCodeToIndex();
        populateNameToIndex();
        populateAdjList();
    }

    /**
     * Return the minimum time require to travel to all station
     * for the specified source station
     * @return
     */
    private int[] getMinTime(int sourceIndex) {
        ArrayList<IntPair> minTimeList = new ArrayList<IntPair>();
        int[] parentIndexPointer = new int[mrtStations.size()];

        for (int i = 0; i < mrtStations.size(); i++) {
            //set all travelDuration(weight) to largest value;
            int travelDuration = Integer.MAX_VALUE;
            IntPair intPair = new IntPair(i, travelDuration);
            minTimeList.add(intPair);

            //set parent pointer to -1;
            parentIndexPointer[i] = -1;
        }

        //create minHeap and populate it
        PriorityQueue<IntPair> minHeap = new PriorityQueue<IntPair>(1, new IntPair(0, 0));
        for (int i = 0; i < mrtStations.size(); i++) {
            minHeap.add(minTimeList.get(i));
        }

        minTimeList.get(sourceIndex).travelDuration = 0;
        MrtStation sourceMrt = mrtStations.get(sourceIndex);

        for (int i = 0; i < sourceMrt.getNumInterchange(); i++) {
            int mrtIndex = stationCodeToIndex.get(sourceMrt.getStationCode(i));
            minTimeList.get(mrtIndex).travelDuration = 0;
            minHeap.remove(minTimeList.get(mrtIndex));
            minHeap.add(minTimeList.get(mrtIndex));
        }
        //update all duplicate
        minHeap.remove(minTimeList.get(sourceIndex));
        minHeap.add(minTimeList.get(sourceIndex));

        //Dijkstra's algorithm
        while (minHeap.size() != 0) {
            IntPair currIntPair = minHeap.poll();
            int currIndex = currIntPair.mrtIndex;
            int currTravelDuration = currIntPair.travelDuration;

            //iterate through the neighbor, update their traveling time.
            ArrayList<IntPair> edgeList = adjList.get(currIndex);
            for (int i = 0; i < edgeList.size(); i++) {
                int destIndex = edgeList.get(i).mrtIndex;
                int travelInterval = edgeList.get(i).travelDuration;

                int oldTravelDuration = minTimeList.get(destIndex).travelDuration;
                int newTravelDuration = currTravelDuration + travelInterval;
                if (!isSameLine(parentIndexPointer[currIndex], destIndex)) {
                    newTravelDuration += INTERCHANGE_TRANSFER_TIME;
                }

                if (newTravelDuration < oldTravelDuration) {
                    MrtStation mrt = mrtStations.get(destIndex);
                    int numInterchange = mrt.getNumInterchange();

                    //update all duplicates of mrt for those that are interchange
                    for (int j = 0; j < numInterchange; j++) {
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
        for (int i = 0; i < minTimeList.size(); i++) {
            minDurations[i] = minTimeList.get(i).travelDuration;
        }

        return minDurations;
    }

    private int[] getMinTotalTime(int[] sourceIndexes) {
        int[] minTotalTimeList = new int[mrtStations.size()];
        for (int i = 0; i < sourceIndexes.length; i++) {
            int[] tempTimeList = getMinTime(sourceIndexes[i]);
            for (int j = 0; j < tempTimeList.length; j++) {
                minTotalTimeList[j] += tempTimeList[j];
            }
        }
        return minTotalTimeList;
    }

    /**
     * Given an array of mrtStationNames, return the minimum travelling
     * time for each station
     * @param mrtStationNames
     * @return
     */
    private int[] getMinTotalTime(String[] mrtStationNames) {
        int[] mrtStationIndexes = new int[mrtStationNames.length];
        for (int i = 0; i < mrtStationNames.length; i++) {
            String mrtStationName = mrtStationNames[i];
            mrtStationIndexes[i] = nameToIndex.get(mrtStationName);
        }
        return getMinTotalTime(mrtStationIndexes);
    }


    /**
     * The only public method in this entire code apart from the constructor
     * Return a list of sorted mrt stations base on total travelling time
     * from stations indicated in mrtStationNames.
     * @param mrtStationNames
     * @return
     */
    public ArrayList<String> getSortedMrtList(ArrayList<String> mrtStationNames) {
        ArrayList<String> validStationList = removeInvalidMrtStation(mrtStationNames);
        String[] mrtStations = new String[validStationList.size()];
        for (int i = 0; i < mrtStations.length; i++) {
            mrtStations[i] = validStationList.get(i);
        }
        return getSortedMrtList(mrtStations);
    }

    //Duplicates no longer in mrt string
    private ArrayList<String> getSortedMrtList(String[] mrtStationNames) {
        return getSortedMrtListFromTotalTimeList(getMinTotalTime(mrtStationNames));
    }

    //Return a sorted list of mrt Stations base on minMrtTimes array
    private ArrayList<String> getSortedMrtListFromTotalTimeList(int[] minMrtTimes) {
        ArrayList<String> sortedMrtStations = new ArrayList<String>();

        PriorityQueue<IntPair> minHeap = new PriorityQueue<IntPair>(1, new IntPair(0, 0));
        for (int i = 0; i < minMrtTimes.length; i++) {
            minHeap.add(new IntPair(i, minMrtTimes[i]));
        }

        while (minHeap.size() != 0) {
            IntPair intPair = minHeap.poll();
            int stationIndex = intPair.mrtIndex;
            MrtStation mrt = mrtStations.get(stationIndex);
            sortedMrtStations.add(mrt.getName());
        }
        return removeDuplicateMrt(sortedMrtStations);
    }

    /**
     * Take in the index of two mrt station. Return true if they belong
     * to the same line.
     * @param mrtIndexOne
     * @param mrtIndexTwo
     * @return
     */
    private boolean isSameLine(int mrtIndexOne, int mrtIndexTwo) {
        if (mrtIndexOne == -1 || mrtIndexTwo == -1) {
            return true;
        }
        MrtStation mrtOne = mrtStations.get(mrtIndexOne);
        int numInterchangeOne = mrtOne.getNumInterchange();

        MrtStation mrtTwo = mrtStations.get(mrtIndexTwo);
        int numInterchangeTwo = mrtTwo.getNumInterchange();

        for (int i = 0; i < numInterchangeOne; i++) {
            for (int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtOne.getMrtLine(i);
                String mrtLineTwo = mrtTwo.getMrtLine(j);
                if (mrtLineOne.equals(mrtLineTwo)) {
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
    private ArrayList<String> removeDuplicateMrt(ArrayList<String> mrtStations) {
        HashMap<String, Boolean> mrtList = new HashMap<String, Boolean>();
        ArrayList<String> noDuplicateMrtList = new ArrayList<String>();
        for (int i = 0; i < mrtStations.size(); i++) {
            String mrtName = mrtStations.get(i);
            if (!mrtList.containsKey(mrtName)) {
                mrtList.put(mrtName, true);
                noDuplicateMrtList.add(mrtName);
            }
        }
        return noDuplicateMrtList;
    }

    /**
     * Iterate through the array, remove duplicated mrt stationName from list
     * @param mrtStations
     * @return
     */
    private ArrayList<String> removeInvalidMrtStation(ArrayList<String> mrtStations) {
        ArrayList<String> validMrtStations = new ArrayList<String>();
        for (int i = 0; i < mrtStations.size(); i++) {
            if (nameToIndex.containsKey(mrtStations.get(i))) {
                validMrtStations.add(mrtStations.get(i));
            }
        }
        return validMrtStations;
    }

    //return the least travelling time between the two stations
    //specified by sourceIndex and destIndex
    private int getTravelTime(int sourceIndex, int destIndex) {
        int[] minTravelTime = getMinTime(sourceIndex);
        int travelTime = minTravelTime[destIndex];
        return travelTime;
    }

    //return the least travelling time between the two station in minutes
    public int getTravelTime(String sourceMrt, String destMrt) {
        if (!isValidMrt(sourceMrt) || !isValidMrt(destMrt)) {
            return -1;
        }
        int sourceIndex = nameToIndex.get(sourceMrt);
        int destIndex = nameToIndex.get(destMrt);
        return getTravelTime(sourceIndex, destIndex);
    }

    /**
     *
     * @return the station names of all the mrt stations
     */
    public ArrayList<String> getMrtStationNames () {
        ArrayList<String> stationNames = new ArrayList<String>();
        for (int i = 0; i < mrtStations.size(); i++) {
            stationNames.add(mrtStations.get(i).getName());
        }
        return stationNames;
    }

    /**
     *
     * @return the abbreviated name of all the mrt station
     */
    public ArrayList<String> getMrtStationShortNames () {
        ArrayList<String> stationShortNames = new ArrayList<String>();
        for (int i = 0; i < mrtStations.size(); i++) {
            stationShortNames.add(mrtStations.get(i).getShortName());
        }
        return stationShortNames;
    }

    /**
     *
     * @return the line names of all the stations
     */
    public ArrayList<ArrayList<String>> getMrtLineNames() {
        ArrayList<ArrayList<String>> stationLineNames = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < mrtStations.size(); i++) {
            MrtStation mrtStation = mrtStations.get(i);
            ArrayList<String> singleStationLines = new ArrayList<String>();
            for (int j = 0; j < mrtStation.getNumInterchange(); j++) {
                singleStationLines.add(mrtStation.getMrtLine(j));
            }
            stationLineNames.add(singleStationLines);
        }
        return stationLineNames;
    }

    /**
     *
     * @return the lineNumber of all the stations
     */
    public ArrayList<ArrayList<Integer>> getMrtLineNumbers() {
        ArrayList<ArrayList<Integer>> stationLineNumbers = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < mrtStations.size(); i++) {
            MrtStation mrtStation = mrtStations.get(i);
            ArrayList<Integer> singleStationLineNumbers = new ArrayList<Integer>();
            for (int j = 0; j < mrtStation.getNumInterchange(); j++) {
                singleStationLineNumbers.add(mrtStation.getStationNumber(j));
            }
            stationLineNumbers.add(singleStationLineNumbers);
        }
        return stationLineNumbers;
    }

    /**
     * Return true if mrtName is a valid mrt station, false otherwise
     * @param mrtName
     * @return
     */
    public boolean isValidMrt(String mrtName) {
        return nameToIndex.containsKey(mrtName);
    }
}
```
###### \java\seedu\address\logic\parser\LocateMrtCommandParser.java
``` java
public class LocateMrtCommandParser implements Parser<LocateMrtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateMrtCommand
     * and returns an LocateMrtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocateMrtCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocateMrtCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateMrtCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\MrtMapDisplay.java
``` java
/**
 * The java class displays the UI.
 */
public class MrtMapDisplay {
    //radius of circle use to visualise mrt stations in the graph
    private static final int DEFAULT_CIRCLE_RADIUS = 4;
    private static final int MAX_CIRCLE_RADIUS = 35;

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

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    /**
     * Initialise all relevant informations required for the running of the UI.
     */
    public MrtMapDisplay() {
        initialise();
    }

    /**
     * following values are hard coded;
     * the info inside point specify the location of the Mrt within the screen
     * as a percentage. e.g. new Point(20, 40) means that the point located at
     * the 20% of the width from the left, and 40 of the height from the bottom.
     */
    private void initialiseMrtToPointHashMap() {
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

        if (mrtToPoint.containsKey(mrtName)) {
            point = mrtToPoint.get(mrtName);
        } else {
            int previousIndex = index;
            boolean previousMrtFound = false;
            boolean nextMrtFound = false;
            Point previousMrtCoordinate = null;
            Point nextMrtCoordinate = null;
            while (!previousMrtFound) {
                previousIndex--;
                String previousMrtShortName = mrtStationShortNames.get(previousIndex);
                if (mrtToPoint.containsKey(previousMrtShortName)) {
                    previousMrtFound = true;
                    previousMrtCoordinate = mrtToPoint.get(previousMrtShortName);
                }
            }
            int nextIndex = index;
            while (!nextMrtFound) {
                nextIndex++;
                String nextMrtShortName = mrtStationShortNames.get(nextIndex);
                if (mrtToPoint.containsKey(nextMrtShortName)) {
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

            int xCor = (int) (previousX + xDistance * numStationFromCurrToPrevious / numStation);
            int yCor = (int) (previousY + yDistance * numStationFromCurrToPrevious / numStation);
            point = new Point(xCor, yCor);
        }
        x = point.getX() * canvassWidth / 100;
        y = point.getY() * canvassHeight / 100;
        return new Point(x, y);
    }

    /**
     * Draw all the mrt, including connecting lines.
     * The result will be roughly similar with the standard singapore
     * mrt maps.
     */
    private void drawMrtMap() {
        prepareCanvass();
        initialiseMrtToPointHashMap();
        Point currPoint = null;
        Point previousPoint = null;
        for (int i = 0; i < mrtStationNames.size(); i++) {
            currPoint = getMrtStationCanvassCoordinate(i);

            int currMrtIndex = i;
            int previousMrtIndex = i - 1;

            if (isNeighbour(previousMrtIndex,currMrtIndex)) {
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

    /**
     * Visualise all the mrt stations specified by mrtStations.
     * @param mrtStations
     */
    private void visualiseStations(ArrayList<String> mrtStations) {
        HashMap<String, Integer> mrtStationCount = new HashMap<String, Integer>();
        for (int i = 0; i < mrtStations.size(); i++) {
            String currStationName = mrtStations.get(i);
            boolean isExist = mrtStationCount.containsKey(currStationName);
            int mrtCount = 0;
            if (isExist) {
                mrtCount = mrtStationCount.get(currStationName);
                mrtCount++;
                mrtStationCount.put(currStationName, mrtCount);
            } else {
                mrtStationCount.put(currStationName, 1);
                mrtCount++;
            }
            int circleRadius = DEFAULT_CIRCLE_RADIUS + mrtCount * DEFAULT_CIRCLE_RADIUS;
            if (circleRadius > MAX_CIRCLE_RADIUS) {
                circleRadius = MAX_CIRCLE_RADIUS;
            }
            displayMrtPoint(currStationName, circleRadius);
        }
    }

    /**
     * display the mrt station that is specified by the mrt index
     * the size of the display circle is specified by circle radius.
     * If that mrt station belong to multiple line, i.e. an interchange,
     * the circle colour will consists of all the mrt line in that interchange.
     * @param mrtIndex
     * @param circleRadius
     */
    private void displayMrtPoint(int mrtIndex, int circleRadius) {
        Point point = getMrtStationCanvassCoordinate(mrtIndex);
        int numInterchange = mrtLineNames.get(mrtIndex).size();
        for (int i = 0; i < numInterchange; i++) {
            Color stationColor = getStationColor(mrtLineNames.get(mrtIndex).get(i));
            StdDraw.setPenColor(stationColor);
            int currCircleRadius = circleRadius - (circleRadius / numInterchange) * i;
            StdDraw.filledCircle(point.getX(), point.getY(), currCircleRadius);
        }

        //a black outline for visibility purposes;
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.circle(point.getX(), point.getY(), circleRadius);
    }

    private void displayMrtPoint(String mrtStationName, int circleRadius) {
        int mrtIndex = mrtNameToIndex.get(mrtStationName);
        displayMrtPoint(mrtIndex, circleRadius);
    }

    private void displayMrtName(String mrtStationName, int circleRadius) {
        int mrtIndex = mrtNameToIndex.get(mrtStationName);
        displayMrtName(mrtIndex, circleRadius);
    }

    private void displayMrtName(int mrtIndex, int circleRadius) {
        Point point = getMrtStationCanvassCoordinate(mrtIndex);
        //display the name right above the circle
        StdDraw.text(point.getX(), point.getY() + circleRadius, mrtStationNames.get(mrtIndex));
    }

    /**
     * Check if the mrt stations specfied by the two indexes are neighbour
     * @param mrtIndexOne
     * @param mrtIndexTwo
     * @return true if the two mrtstations are neighbour. False otherwise;
     */
    private boolean isNeighbour(int mrtIndexOne, int mrtIndexTwo) {
        if (mrtIndexOne < 0 || mrtIndexTwo < -1) {
            return false;
        }

        ArrayList<String> mrtLineNameOne = mrtLineNames.get(mrtIndexOne);
        ArrayList<Integer> mrtLineNumbersOne = mrtLineNumbers.get(mrtIndexOne);
        int numInterchangeOne = mrtLineNameOne.size();

        ArrayList<String> mrtLineNameTwo = mrtLineNames.get(mrtIndexTwo);
        ArrayList<Integer> mrtLineNumbersTwo = mrtLineNumbers.get(mrtIndexTwo);
        int numInterchangeTwo = mrtLineNameTwo.size();

        for (int i = 0; i < numInterchangeOne; i++) {
            for (int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtLineNameOne.get(i);
                String mrtLineTwo = mrtLineNameTwo.get(j);
                if (mrtLineOne.equals(mrtLineTwo)) {
                    int stationNumberOne = mrtLineNumbersOne.get(i);
                    int stationNumberTwo = mrtLineNumbersTwo.get(j);
                    int stationDiff = stationNumberOne - stationNumberTwo;
                    int maxMrtGap = 2; //in Singapore mrt, the maximum jump in station number observed is 2;
                    if (Math.abs(stationDiff) <= maxMrtGap) {
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

        for (int i = 0; i < numInterchangeOne; i++) {
            for (int j = 0; j < numInterchangeTwo; j++) {
                String mrtLineOne = mrtCodesOne.get(i);
                String mrtLineTwo = mrtCodesTwo.get(j);
                if (mrtLineOne.equals(mrtLineTwo)) {
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
        if ("EW".equals(lineName) || "CG".equals(lineName)) {
            //East West line which is Green in color in mrt map
            return Color.GREEN;

        } else if ("NS".equals(lineName)) {
            //NorthSouth Line which is red in color on Mrt Map
            return Color.RED;

        } else if ("CC".equals(lineName) || ("CE").equals(lineName)) {
            //Circle line, which is yellow in color in mrt map;
            return Color.YELLOW;

        } else if ("NE".equals(lineName)) {
            //Nourth East line which is purple in color in mrt map;
            return Color.MAGENTA;

        } else if ("DT".equals(lineName)) {
            //Down Town line is blue in color in the mrt map;
            return Color.BLUE;

        } else {
            //if reach here, the station lineName is wrong;
            //just return a black color anyway
            return Color.BLACK;
        }
    }

    /**
     * Fill up all the relevant lists with correct information.
     * These lists are needed for the UI to run
     */
    private void initialise() {
        MrtMapLogic mrtMapLogic = new MrtMapLogic();

        mrtStationNames = mrtMapLogic.getMrtStationNames();
        mrtStationShortNames = mrtMapLogic.getMrtStationShortNames();
        mrtLineNames = mrtMapLogic.getMrtLineNames();
        mrtLineNumbers = mrtMapLogic.getMrtLineNumbers();

        initialiseMrtToPointHashMap();
        generateMrtNameToIndexHashMap();
        drawMrtMap();
    }

    private void generateMrtNameToIndexHashMap() {
        for (int i = 0; i < mrtLineNames.size(); i++) {
            mrtNameToIndex.put(mrtStationNames.get(i), i);
        }
    }
}
```
