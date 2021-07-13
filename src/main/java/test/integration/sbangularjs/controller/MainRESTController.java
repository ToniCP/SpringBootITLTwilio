package test.integration.sbangularjs.controller;

import java.util.List;

import test.integration.sbangularjs.dao.SMSDAO;
import test.integration.sbangularjs.model.SMS;
import test.integration.sbangularjs.model.SMSForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class MainRESTController {
	
    @Autowired
    private SMSDAO smsDAO;
    
    @RequestMapping(value = "/msg", //
            method = RequestMethod.POST, //
            produces = { MediaType.APPLICATION_JSON_VALUE, //
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public SMS newCode(@RequestBody SMSForm smsForm) {
        System.out.println("(Service Side) Creating sms with phone: " + smsForm.getPhone()); // Delete
        return smsDAO.newCode(smsForm);
    }
    
    @RequestMapping(value = "/msg/{phone}/{code}", //
            method = RequestMethod.GET, //
            produces = { MediaType.APPLICATION_JSON_VALUE, //
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public SMS getSMS(@PathVariable("phone") String phone, @PathVariable("code") String code) {
    	System.out.println("(Service Side) Getting information from the phone: " + phone + " and Code: " + code); // Delete
        return smsDAO.validSMS(phone, code);
    }
    
    // The code after this line is not in use.
    @RequestMapping(value = "/msg", //
            method = RequestMethod.PUT, //
            produces = { MediaType.APPLICATION_JSON_VALUE, //
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public SMS updateCode(@RequestBody SMSForm smsForm) {
        System.out.println("(Service Side) Editing sms with code: " + smsForm.getCode());
        return smsDAO.updateCode(smsForm);
    }

    @RequestMapping(value = "/msgs", //
            method = RequestMethod.GET, //
            produces = { MediaType.APPLICATION_JSON_VALUE, //
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<SMS> getEmployees() {
        List<SMS> list = smsDAO.getAllSMS();
        return list;
    }
}
