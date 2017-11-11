package seedu.address.logic.commands.hints;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.parser.HintParser;

public class RadioCommandHint extends FixedArgumentsHint {

    public RadioCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
    }
    private final static String[] ACTION = new String[] {"play", "stop"};
    private final  static String[] GENRE = RadioCommand.genreList;


    @Override
    public void parse() {

        String[] args = arguments.trim().split("\\s+");

        if (args.length == 0) {
            String autoCorrectHint = (RadioCommand.isRadioPlaying()) ? "stop" : "play";
            offerHint(autoCorrectHint, "radio " + autoCorrectHint);
            return;
        }

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = HintParser.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (RadioCommand.isRadioPlaying()) ? "stop" : "play";
                offerHint((RadioCommand.isRadioPlaying()) ? "stop" : "play", "radio " + autoCorrectHint);
                return;
            } else {
                handleCompletingArg(actionArgument, autoCompletedArg, "radio " + ((RadioCommand.isRadioPlaying()) ? "stop" : "play"));
                return;
            }
        }

        if (args.length == 1) {
            //radio play|
            if (actionArgument.equals("play")) {
                offerHint("pop", "radio play pop");
            } else {
                //stop doesn't need any more args
                handleFinishedArgs(actionArgument);
            }
            return;
        }

        String genreArgument = args[1];

        if (!isValidFixedArg(genreArgument, GENRE) && actionArgument.equals("play")) {
            //completing an arg?
            String autoCompletedArg = HintParser.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "radio play pop");
                return;
            } else {
                handleCompletingArg(genreArgument, autoCompletedArg, (RadioCommand.isRadioPlaying()) ? "radio stop" : "radio play " + autoCompletedArg);
                return;
            }
        }

        handleNextArg(genreArgument, GENRE, "radio play");
    }

    @Override
    protected String descriptionFromArg(String arg) {
        switch (arg) {
        case "play":
            return " plays radio";
        case "stop":
            return " stops radio";
        case "pop":
            return " plays pop radio";
        case "classic":
            return " plays classic radio";
        case "chinese":
            return " plays chinese radio";
        case "comedy":
            return " plays comedy radio";
        case "news":
            return " plays news radio";
        case "country":
            return " plays country radio";
        default:
            return "";
        }
    }

}
