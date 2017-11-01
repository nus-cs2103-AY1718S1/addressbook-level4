//package seedu.address.model;
//
//import com.google.common.eventbus.Subscribe;
//
//import seedu.address.commons.core.EventsCenter;
//import seedu.address.commons.events.ui.ParcelPanelSelectionChangedEvent;
//
///**
// * SelectionListener listens for events that select a parcel card.
// */
//public class SelectionListener {
//
//    private final Model model;
//
//    /**
//     * Initializes a SelectionLister with the given model.
//     */
//    public SelectionListener(Model model) {
//        this.model = model;
//        registerAsAnEventHandler(this);
//    }
//
//    /**
//     * Registers the object as an event handler at the {@link EventsCenter}
//     * @param handler usually {@code this}
//     */
//    protected void registerAsAnEventHandler(Object handler) {
//        EventsCenter.getInstance().registerHandler(handler);
//    }
//
//    @Subscribe
//    public void handleParcelPanelSelectionChangedEvent(ParcelPanelSelectionChangedEvent ppsce) {
//        model.setPrevSelectedParcel(ppsce.getNewSelection().parcel);
//    }
//}
