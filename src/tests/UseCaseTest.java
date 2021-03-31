package tests;

import gui.GUILink;
import gui.GUIObject;
import gui.GUITable;
import gui.Window;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;
import static tests.TestUtil.*;

public class UseCaseTest {

    @Test
    void testUseCase() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        java.awt.EventQueue.invokeAndWait(window::show);
        FontMetrics fm = window.getFontMetrics();



        //2. Application shows a welcome document.
        assertTrue("UC_2.a", window.getAddress().equals("WelcomeDoc.html"));
        assertTrue("UC_2.b", window.getDocArea().getDrawnGUIObjects().size() == 8);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", window.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertEquals("UC_4.a", window.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        ArrayList<GUIObject> renderedObjects = window.getDocArea().getDrawnGUIObjects();
        assertTrue("UC_4.b", renderedObjects.size() == 11);
        assertTrue("UC_4.j", containsGUIStringWith(0, 0, "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWith(0, fm.getHeight(), "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, fm.getHeight(), "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWith(0, 2*fm.getHeight(), "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 2*fm.getHeight(), "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(0, 3*fm.getHeight(), "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 3*fm.getHeight(), "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(0, 4*fm.getHeight(), "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(fm.stringWidth("table") + GUITable.xMargin, 4*fm.getHeight(), "Table cells containing table data", renderedObjects));
        //This page has 4 GUILinks and 5 GUIStrings

        //5 User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : window.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUILink){
                //press a hyperlink
                obj.handleMouseEvent(0,fm.getHeight(), MouseEvent.MOUSE_PRESSED, 1);
                //all hyperlinks will navigate to a non-browsr webpage -> show error document
                href = ((GUILink) obj).getHref();
                break;
            }
        }
        //6 Application shows the desired webpage.
        assertEquals("UC_6.a", window.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/" + href);
        assertTrue("UC_6.b", window.getDocArea().getDrawnGUIObjects().size() == 3);
        //error document has 2 GUIStrings
        for(GUIObject obj : window.getDocArea().getDrawnGUIObjects()){
            assertFalse("UC_6.c", obj instanceof GUILink);
            //error document has no GUILinks
        }

    }
}
