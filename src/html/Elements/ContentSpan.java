package html.Elements;

import html.ContentSpanVisitor;

/**
 * A content span object
 */
public class ContentSpan {

    /**
     * Create the visible equivalent off this object
     * @param c The creator which will create the visible object
     */
    public void accept(ContentSpanVisitor c){
    }

}
