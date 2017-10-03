package seedu.address.logic;
import javafx.scene.control.TextField;
import seedu.address.logic.commands.Command;
import seedu.address.ui.CommandBox;
import seedu.address.ui.MainWindow;

import javax.speech.*;
import javax.speech.recognition.*;
import javax.xml.soap.Text;

import java.io.FileReader;
import java.util.Locale;

public class SpeechToText extends ResultAdapter {
	static Recognizer rec;
	private Logic logic;
	public TextField commandTextField;

	public SpeechToText(Logic logic) {
		this.logic = logic;
	}
	public SpeechToText(TextField commandTextField){
		this.commandTextField = commandTextField;
	}

	// Receives RESULT_ACCEPTED event: print it, clean up, exit
	public void resultAccepted(ResultEvent e) {
		Result r = (Result)(e.getSource());
		ResultToken tokens[] = r.getBestTokens();
		String speechCommand="";
		for (int i = 0; i < tokens.length; i++){
			System.out.println(tokens[i].getSpokenText());
			/*
			CommandBox commandBox = new CommandBox(logic);
			commandBox.commandTextField.setText(tokens[i].getSpokenText());
			String oldtext = commandBox.commandTextField.getText();
			commandBox.commandTextField.setText(oldtext+" "+ tokens[i].getSpokenText() + " ");
			*/
			speechCommand+=tokens[i].getSpokenText() + " ";


		}
		commandTextField.setText(speechCommand);
		if(tokens[0].getSpokenText().equals("delete")){
			/*
			String oldtext = SubmitSurveyForm.getTb_usercomment().getText();
			 String[] temp1;
			 String newtext = "";
			  temp1 = oldtext.split("\\s+");
			  for(int i =0; i < temp1.length-2; i++){
				  newtext = newtext + " " +temp1[i] ;
			  }
			  //.setText(newtext);
			  */
		}


		// Deallocate the recognizer and exit
	
	}
}