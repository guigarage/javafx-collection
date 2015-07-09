package com.guigarage.responsive;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;


/**
 * Created by hansolo on 12.09.14.
 */
public class DeviceTypeEvent extends Event {
    public static final EventType<DeviceTypeEvent> DEVICE_TYPE_CHANGED = new EventType<>(ANY, "deviceTypeChanged");
    public              Node                       node;
    public              DeviceType                 deviceType;


    // ******************** Constructors **********************************
    public DeviceTypeEvent(final EventType<DeviceTypeEvent> EVENT_TYPE, final Node NODE, final DeviceType DEVICE_TYPE) {
        super(EVENT_TYPE);
        node       = NODE;
        deviceType = DEVICE_TYPE;
    }
}
