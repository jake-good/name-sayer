package UIscenes;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class SlideMenu {

    private AnchorPane _slideInMenu;
    private boolean _expanded;
    public SlideMenu(AnchorPane pane, boolean expanded) {
        _slideInMenu = pane;
        _expanded = expanded;
    }

    public boolean SlideMenuMake() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(250), _slideInMenu);
        slideIn.setFromY(-47);
        slideIn.setToY(0);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(250), _slideInMenu);
        slideOut.setFromY(0);
        slideOut.setToY(-47);

        if (!_expanded) {
            slideIn.setRate(1);
            slideIn.play();
            return true;
        } else {
            slideOut.setRate(1);
            slideOut.play();
            return false;
        }
    }
}
