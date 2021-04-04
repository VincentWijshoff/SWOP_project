package events;

/**
 * An interface used so a event can be added as a key event
 */
public interface KeyEventListener {
    /**
     * Handle the key event
     * @param id        The id off the key pressed
     * @param keyCode   The code off the key pressed
     * @param keyChar   The char off the key pressed
     * @param modifier  The modifier on the pressed key
     * @return          True if a something special needs to happen else false. The speciality off the event when true
     *                  is returned is determined by the object that adds the key event
     */
    boolean handleKeyEvent(int id, int keyCode, char keyChar, int modifier);

}
