package com.jccy.realmdemo.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by heyangyang on 2017/10/11.
 */

public class EventBusManager {
    private static EventBus eventBus;

    public static synchronized EventBus getEventBus(){
        if (eventBus == null){
            eventBus = EventBus.getDefault();
        }
        return eventBus;
    }


    public static class OnDeleteAllDataSuccessEvent{

    }
}
