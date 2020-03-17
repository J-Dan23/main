package life.calgo.ui;

import java.util.logging.Logger;

import life.calgo.commons.core.LogsCenter;
import life.calgo.model.food.ConsumedFood;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class DailyListPanel extends UiPart<Region> {

    private static final String FXML = "DailyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DailyListPanel.class);

    @FXML
    private ListView<ConsumedFood> dailyListView;

    public DailyListPanel(ObservableList<ConsumedFood> dailyList) {
        super(FXML);
        dailyListView.setItems(dailyList);
        dailyListView.setCellFactory(listView -> new DailyListPanel.DailyListViewCell());
    }


    class DailyListViewCell extends ListCell<ConsumedFood> {
        @Override
        protected void updateItem(ConsumedFood food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConsumedFoodCard(food, getIndex() + 1).getRoot());
            }
        }
    }

}