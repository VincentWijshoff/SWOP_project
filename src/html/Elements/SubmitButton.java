package html.Elements;

import html.ContentSpanVisitor;

public class SubmitButton extends ContentSpan{

    /**
     * constructor of the button
     */
    public SubmitButton(){
    }

    /**
     * Create the button
     * @param c The creator which will create the visible object
     */
    @Override
    public void accept(ContentSpanVisitor c){
        c.visitSubmitButton(this);
    }
}
