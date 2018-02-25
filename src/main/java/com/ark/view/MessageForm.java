package com.ark.view;

import com.ark.models.UserData;
import com.ark.store.DatabaseManager;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Input  form class
 */
public class MessageForm extends FormLayout {

    // Constants for validation
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static String TEXT_VALIDATION_REGEXP = "[^<>]+";
    private final static String HOMEPAGE_VALIDATION_REGEXP = "^$|^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    // Components of form
    private TextField username = new TextField();
    private TextField email = new TextField();
    private TextArea messageText = new TextArea();
    private TextField homePage = new TextField();
    private CaptchaComponent source = new CaptchaComponent();
    private Image captchaImg = new Image(null, new StreamResource(source, createFileName()));
    private TextField captchaAnswer = new TextField();
    private Button update = new Button(VaadinIcons.REFRESH);
    private Button save = new Button("Save");
    private Button hide = new Button("Hide");

    private HorizontalLayout tools = new HorizontalLayout();
    private HorizontalLayout buttons = new HorizontalLayout();
    private DatabaseManager manager = new DatabaseManager();
    private AppUI appUI;
    private UserData userData;
    private Binder<UserData> binder = new Binder<>(UserData.class);
    private WebBrowser browser;

    // Constructor
    public MessageForm(AppUI appUI) {
        this.appUI = appUI;
        makeUpForm();
        validate();
        save.addClickListener(e -> save());
        update.addClickListener(e -> updateCaptcha());
        hide.addClickListener(e ->  hideForm());
    }

    /**
     * Method add different visual effects to form components
     */
    private void makeUpForm() {
        username.setPlaceholder("Username");
        username.setSizeFull();
        username.setHeight("50");

        email.setPlaceholder("Email");
        email.setSizeFull();
        email.setHeight("50");

        messageText.setPlaceholder("Your text..");
        messageText.setSizeFull();

        homePage.setPlaceholder("Homepage");
        homePage.setSizeFull();
        homePage.setHeight("50");

        captchaAnswer.setPlaceholder("Answer");
        captchaAnswer.setSizeFull();
        captchaAnswer.setHeight("50");

        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);

        hide.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        hide.addStyleName(ValoTheme.BUTTON_DANGER);

        setWidth("300");
        tools.addComponents(captchaImg, update);
        update.setWidth("80");
        tools.setComponentAlignment(update, Alignment.MIDDLE_RIGHT);
        tools.setSizeFull();
        buttons.addComponents(save, hide);
        save.setWidth("100");
        hide.setWidth("100");
        buttons.setComponentAlignment(hide, Alignment.MIDDLE_RIGHT);
        buttons.setSizeFull();
        addComponents(username, email, homePage, tools, captchaAnswer, messageText, buttons);

    }

    /**
     * Method add validation to components of form
     */
    private void validate() {
        binder.forField(username)
                .asRequired("Empty username")
                .withValidator(new StringLengthValidator("To long name",1 , 70))
                .bind("username");
        binder.forField(email)
                .asRequired("Empty email")
                .withValidator(new EmailValidator("Incorrect email"))
                .withValidator(new StringLengthValidator("To long email",1 , 50))
                .bind("email");
        binder.forField(homePage)
                .withValidator(new RegexpValidator("Empty or www", HOMEPAGE_VALIDATION_REGEXP))
                .withValidator(new StringLengthValidator("To long homepage",0 , 70))
                .bind("homePage");
        binder.forField(captchaAnswer)
                .asRequired("Enter messageText from image")
                .withValidator(answer -> answer.equals(source.getCaptchaText()), "WRONG!!!")
                .bind("captchaAnswer");
        binder.forField(messageText)
                .asRequired("Empty messageText")
                .withValidator(new RegexpValidator("can't contain html tag", TEXT_VALIDATION_REGEXP))
                .bind("messageText");

    }

    /**
     * Method generate name for captcha Image file
     * @return name
     */
    private String createFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String name = dateFormat.format(new Date());
        return name + ".png";

    }

    /**
     * setter for UserData
     * @param userData instance
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
        binder.setBean(userData);
    }

    /**
     * Generate browser name
     * @return
     */
    private String browserName() {

        String name = "Undefine browser";
        if (browser.isFirefox())
            name = "Firefox";
        else if (browser.isOpera())
            name = "Opera";
        else if (browser.isEdge())
            name = "Edge";
        else if (browser.isChrome())
            name = "Chrome";
        else if (browser.isSafari())
            name = "Safari";
        return name;
    }

    /**
     * Save input information to database
     */
    private void save() {
        if (binder.isValid()) {
            // get information about browser
            browser = Page.getCurrent().getWebBrowser();
            // set it in userdata instance
            this.userData.setBrowser(browserName());
            this.userData.setIp(browser.getAddress());
            this.userData.setDate(DATE_FORMAT.format(new Date()));
            if (this.userData.getHomePage().equals("")) {
                this.userData.setHomePage("Not indicated");
            }
            manager.save(userData);
            // update form and grid
            appUI.updateGrid();
            appUI.updateForm();
            Notification.show("Saved successfully");
        }
        // else need to check input form
        else Notification.show("Check required fields");

    }

    /**
     * Update captcha
     */
    private void updateCaptcha() {
        source = new CaptchaComponent();
        Image newCaptchaImg = new Image(null, new StreamResource(source, createFileName()));
        tools.replaceComponent(this.captchaImg, newCaptchaImg);
        this.captchaImg = newCaptchaImg;
        captchaAnswer.clear();
    }

    /**
     * Hide the form
     */
    private void hideForm() {
        this.setVisible(false);
        appUI.leaveVisible(true);
    }
}

