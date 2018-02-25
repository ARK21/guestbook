package com.ark.view;

import com.ark.models.UserData;
import com.ark.store.DatabaseManager;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.*;
import org.vaadin.teemusa.gridextensions.paging.PagedDataProvider;
import org.vaadin.teemusa.gridextensions.paging.PagingControls;

/**
 * Grid with opportunity to set number of row on page.
 */
public class PagedGrid extends VerticalLayout {

    // instance of grid
    private Grid<UserData> grid = new Grid<>();
    // for access to database methods
    private DatabaseManager manager = new DatabaseManager();
    // instance of class for turn pages panel. Class from gridextensionpack library
    private PagingControls pagingControls;

    /**
     * Constructor
     */
    public PagedGrid() {
        addComponent(grid);
        configureGrid();
        updateGrid();
    }

    /**
     * Method configure grid columns.
     * For column "Message" added tooltip which show full text of message if message is too long.
     */
    private void configureGrid() {
        grid.addColumn(UserData::getUsername).
                setCaption("Username")
                .setExpandRatio(1)
                .setResizable(false);
        grid.addColumn(UserData::getMessageText)
                .setCaption("Message")
                .setWidth(550)
                .setDescriptionGenerator(UserData::getMessageText)
                .setResizable(false);
        grid.addColumn(UserData::getEmail).setCaption("Email")
                .setExpandRatio(1)
                .setResizable(false);
        grid.addColumn(UserData::getDate).setCaption("Date")
                .setExpandRatio(1).
                setResizable(false);
        grid.setSizeFull();

        // add panel to turn pages
        addTurnPagesPanel();
        setExpandRatio(grid, 1);

        // switch off margin
        setMargin(false);
    }

    /**
     * Method add turning buttons to grid.
     */
    private void addTurnPagesPanel() {
        HorizontalLayout panel = new HorizontalLayout();
        panel.addComponent(new Button("First", e -> pagingControls.setPageNumber(0)));
        panel.addComponent(new Button("Previous", e -> pagingControls.previousPage()));
        panel.addComponent(new Button("Next", e -> pagingControls.nextPage()));
        panel.addComponent(new Button("Last", e -> pagingControls.setPageNumber(pagingControls.getPageCount() - 1)));
        addComponent(panel);
        setComponentAlignment(panel, Alignment.BOTTOM_CENTER);
    }

    /**
     * Method update grid information
     */
    public void updateGrid() {
        // get information to set data
        PagedDataProvider<UserData, SerializablePredicate<UserData>> dataProvider =
                new PagedDataProvider<>(DataProvider.ofCollection(manager.values()));
        grid.setDataProvider(dataProvider);
        this.pagingControls = dataProvider.getPagingControls();
        // set number of row on page
        pagingControls.setPageLength(25);
    }
}
