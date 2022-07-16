/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobs;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Bogdan
 */
public class JobResponse implements Serializable {

    private String Message;
    private List<Job> changes;
    private int retValue = 0;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<Job> getChanges() {
        return changes;
    }

    public void setChanges(List<Job> changes) {
        this.changes = changes;
    }

    public int getRetValue() {
        return retValue;
    }

    public void setRetValue(int retValue) {
        this.retValue = retValue;
    }

    
}
