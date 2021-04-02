package tests;

import gui.DefaultScreen.DefaultScreen;
import gui.DefaultScreen.DocumentArea;
import gui.Objects.GUILink;
import gui.Objects.GUIObject;
import gui.Objects.GUITable;
import gui.Window;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static tests.TestUtil.*;

public class UseCaseTest {

    @Test
    void testUseCase() throws InvocationTargetException, InterruptedException {
        //1. User starts a Browsr application.
        Window window = new Window("useCase");
        DefaultScreen screen = new DefaultScreen(window);
        java.awt.EventQueue.invokeAndWait(window::show);
        FontMetrics fm = window.getFontMetrics();
        DocumentArea docarea = screen.getDocArea();


        //2. Application shows a welcome document.
        assertTrue("UC_2.a", screen.getAddress().equals("WelcomeDoc.html"));
        assertTrue("UC_2.b", screen.getDocArea().getDrawnGUIObjects().size() == 8);
        //Welcome doc has 6 GUIStrings and 1 GUILink

        //3. User navigates to a desired webpage. (using AddressBar)
        window.handleMouseEvent(MouseEvent.MOUSE_PRESSED, 5, 25, 1, 1, 1024); // click on address bar
        assertTrue("UC_3.a", screen.getAddressBar().isInFocus());
        typeString(window, "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html\n");

        //4. Application shows the desired webpage.
        assertEquals("UC_4.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        ArrayList<GUIObject> renderedObjects = screen.getDocArea().getDrawnGUIObjects();
        assertTrue("UC_4.b", renderedObjects.size() == 11);
        assertTrue("UC_4.j", containsGUIStringWith(docarea.xOffset, docarea.getRelativeYPos(), "HTML elements partially supported by Browsr:", renderedObjects));

        assertTrue("UC_4.d", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), "a", "a.html", renderedObjects));
        assertTrue("UC_4.g", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + fm.getHeight(), "Hyperlink anchors", renderedObjects));

        assertTrue("UC_4.e", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 2*fm.getHeight(), "table", "table.html", renderedObjects));
        assertTrue("UC_4.c", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 2*fm.getHeight(), "Tables", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset, docarea.getRelativeYPos() + 3*fm.getHeight(), "tr", "tr.html", renderedObjects));
        assertTrue("UC_4.i", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 3*fm.getHeight(), "Table rows", renderedObjects));

        assertTrue("UC_4.h", containsGUILinkWith(docarea.xOffset,  docarea.getRelativeYPos()  + 4*fm.getHeight(), "td", "td.html", renderedObjects));
        assertTrue("UC_4.f", containsGUIStringWith(docarea.xOffset + fm.stringWidth("table") + GUITable.xMargin, docarea.getRelativeYPos() + 4*fm.getHeight(), "Table cells containing table data", renderedObjects));
        //This page has 4 GUILinks and 5 GUIStrings

        //5 User navigates to a desired webpage. (using hyperlink)
        String href = "";
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            if(obj instanceof GUILink){
                //press a hyperlink
                obj.handleMouseEvent(docarea.xOffset, docarea.getRelativeYPos() + fm.getHeight(), MouseEvent.MOUSE_PRESSED, 1);
                //all hyperlinks will navigate to a non-browsr webpage -> show error document
                href = ((GUILink) obj).getHref();
                break;
            }
        }
        //6 Application shows the desired webpage.
        assertEquals("UC_6.a", screen.getAddress(), "https://people.cs.kuleuven.be/~bart.jacobs/" + href);
        assertTrue("UC_6.b", screen.getDocArea().getDrawnGUIObjects().size() == 3);
        //error document has 2 GUIStrings
        for(GUIObject obj : screen.getDocArea().getDrawnGUIObjects()){
            assertFalse("UC_6.c", obj instanceof GUILink);
            //error document has no GUILinks
        }

    }
}
