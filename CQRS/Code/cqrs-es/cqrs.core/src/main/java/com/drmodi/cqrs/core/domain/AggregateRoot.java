package com.drmodi.cqrs.core.domain;

import com.drmodi.cqrs.core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    protected String id;
    private int version = -1;

    //list of changes that are made to aggregate form of events
    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId(){
        return this.id;
    }

    public int getVersion(){
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges(){
        return this.changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, Boolean isNewEvent){
        try{
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch (NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
        }catch (Exception ex){
            logger.log(Level.SEVERE, "Error applying event to aggregate ", ex);
        }finally {
            if(isNewEvent){
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event){
        applyChanges(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChanges(event, false));
    }


}
