package com.ark.view;

import javax.servlet.annotation.WebServlet;

import com.ark.models.UserData;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

/**
 * Main class. Start applacation
 */
@Theme("mytheme")
@Title("GuestBook")
public class AppUI extends UI {

    // Grid containing database information
    private PagedGrid grid = new PagedGrid();
    // Input form instance
    private MessageForm form = new MessageForm(this);
    // layout contains from and grid
    private HorizontalLayout main = new HorizontalLayout(grid, form);
    private Button leaveMessage = new Button("Leave a message");

    // method start application
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        main.setSizeFull();
        grid.setSizeFull();

        leaveMessage.addClickListener(event -> {

            form.setVisible(true);
            form.setUserData(new UserData());
            leaveMessage.setVisible(false);

        });
        VerticalLayout layout = new VerticalLayout();
        layout.setHeight("100%");

        layout.addComponents(leaveMessage, main);
        layout.setExpandRatio(main, 1);
        main.setExpandRatio(grid, 1);
        setContent(layout);
        // create new userdata
        form.setVisible(false);
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AppUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    // method update grid
    public void updateGrid() {
        leaveMessage.setVisible(true);
        grid.updateGrid();
    }

    // method update form
    public void updateForm() {
        MessageForm newForm = new MessageForm(this);
        main.replaceComponent(this.form, newForm);
        this.form = newForm;
        form.setVisible(false);

    }

    // set visible to leave button
    public void leaveVisible(boolean visible) {
        leaveMessage.setVisible(visible);
    }

}
