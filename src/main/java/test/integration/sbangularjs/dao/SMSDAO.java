package test.integration.sbangularjs.dao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Repository;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import test.integration.sbangularjs.model.SMS;
import test.integration.sbangularjs.model.SMSForm;

@Repository
public class SMSDAO {
	public static final String ACCOUNT_SID = "ACb27f33984782470cbd9db34f5831d948";
    public static final String AUTH_TOKEN = "9c245a8155e163f3b182b6c7893df762";
    
	private static final Map<String, SMS> msgMap = new HashMap<String, SMS>();
	
	public String generateCode() {
		// It will generate 6 digit random number from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // This will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public String sendMessage(String toPhone) {
		SMS msgData = this.getSMS(toPhone);
		System.out.println("(Service Side) Body: " + msgData.getBody() + ", Phone: " + toPhone); // Delete
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(toPhone), //to
                new PhoneNumber("+19093774602"), //from
                msgData.getBody())
            .create();

        return message.getBody();
	}
	
	public SMS getSMS(String phone) {
		return msgMap.get(phone);
	}
	
	public SMS validSMS(String phone, String code) {
		LocalDateTime newDate = LocalDateTime.now();
		LocalDateTime registrationDate = msgMap.get(phone).getDate();
		String registrationCode = msgMap.get(phone).getCode();
		
		LocalDateTime tempDateTime = LocalDateTime.from(registrationDate);
		
		long years = tempDateTime.until( newDate, ChronoUnit.YEARS );
		tempDateTime = tempDateTime.plusYears( years );

		long months = tempDateTime.until( newDate, ChronoUnit.MONTHS );
		tempDateTime = tempDateTime.plusMonths( months );

		long days = tempDateTime.until( newDate, ChronoUnit.DAYS );
		tempDateTime = tempDateTime.plusDays( days );


		long hours = tempDateTime.until( newDate, ChronoUnit.HOURS );
		tempDateTime = tempDateTime.plusHours( hours );

		long minutes = tempDateTime.until( newDate, ChronoUnit.MINUTES );
		tempDateTime = tempDateTime.plusMinutes( minutes );

		long seconds = tempDateTime.until( newDate, ChronoUnit.SECONDS );

		System.out.println( years + " years " + 
		        months + " months " + 
		        days + " days " +
		        hours + " hours " +
		        minutes + " minutes " +
		        seconds + " seconds."); // Delete
		
		System.out.println("(Service Side) new date: " + newDate + ", Date: " + registrationDate); // Delete
		System.out.println("(Service Side) Code entered: " + code + ", registered code: " + registrationCode); // Delete
		System.out.println("Minutes: " + minutes + ", Seconds: " + seconds); // Delete
		
		if (minutes >= 5) {
			if (seconds >= 0) {
				// Reply with message to frontend
				System.out.println("(Service Side) Invalid code, no longer current."); // Delete
				
				SMSForm msgForm = new SMSForm();
				msgForm.setPhone("ERROR_InvalidCode");
				msgForm.setCode(code);
				msgForm.setDate(newDate);
				msgForm.setBody("Invalid code, it has already expired. Generate a new code with your phone number.");
				SMS newMsg = new SMS(msgForm);
				msgMap.put("ERROR_InvalidCode", newMsg);
				
				return msgMap.get("ERROR_InvalidCode");
			}
		}
		
		if (registrationCode.compareTo(code) != 0) {
			// Reply with message to frontend
			System.out.println("(Service Side) The code is wrong, it does not match."); // Delete
			
			SMSForm msgForm = new SMSForm();
			msgForm.setPhone("ERROR_CodeIsWrong");
			msgForm.setCode(code);
			msgForm.setDate(newDate);
			msgForm.setBody("The code is wrong, it does not match.");
			SMS newMsg = new SMS(msgForm);
			msgMap.put("ERROR_CodeIsWrong", newMsg);
			
			return msgMap.get("ERROR_CodeIsWrong");
		}
		
		return msgMap.get(phone);
	}
	
	public SMS newCode(SMSForm msgForm) {
		String code = this.generateCode();
		LocalDateTime date = LocalDateTime.now();
		
		msgForm.setCode(code);
		msgForm.setDate(date);
		msgForm.setBody("Hello, your code is: " + code);
	    
		SMS newMsg = new SMS(msgForm);
		System.out.println("(Service Side) Phone: " + newMsg.getPhone()); // Delete
	    msgMap.put(newMsg.getPhone(), newMsg);
		
	    sendMessage(msgForm.getPhone());
	    return newMsg;
	}
	
	// The code after this line is not in use.
	public SMS updateCode(SMSForm msgForm) {
		SMS msg = this.getSMS(msgForm.getPhone());
		if(msg!=null) {
			msg.setCode(msgForm.getCode());
			msg.setDate(msgForm.getDate());
			msg.setBody(msgForm.getBody());
		}
		return msg;
	}
	
	public List<SMS> getAllSMS() {
		Collection<SMS> c = msgMap.values();
		List<SMS> list = new ArrayList<SMS>();
		list.addAll(c);
		return list;
	}
}
