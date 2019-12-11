package org.github.legioth.buttonrenderer;

import java.util.stream.IntStream;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

@Route("")
public class DemoView extends Div {

    public static class Item {
        private int id;

        public Item(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getCaption() {
            return "Item " + id;
        }

        public boolean canBePromoted() {
            return id % 5 != 0;
        }

        public VaadinIcon getIcon() {
            VaadinIcon[] values = VaadinIcon.values();
            return values[getId() % values.length];
        }

        public void promote() {
            Notification.show("Promoting " + getCaption());
        }
    }

    public DemoView() {
        Grid<Item> grid = new Grid<>();

        grid.setItems(IntStream.range(0, 10000).mapToObj(Item::new));

        grid.addColumn(Item::getCaption).setHeader("Caption").setWidth("100px");

        grid.addColumn(ButtonRendererBuilder.create("Click me", item -> Notification.show("You clicked " + item)))
                .setHeader("Simple button").setWidth("150px");

        grid.addColumn(new ButtonRendererBuilder<>(Item::promote).withCaption("Promote")
                .withIconSuffix(VaadinIcon.LEVEL_UP_BOLD).withEnabledGenerator(Item::canBePromoted)
                .withStyle("width: 100%").build()).setHeader("Icon, styles, enabled generator").setWidth("250px");

        grid.addColumn(new ButtonRendererBuilder<Item>(item -> System.out.println(item.getIcon().name()))
                .withIconPrefix(item -> item.getIcon().create()).withCaption(item -> item.getIcon().name())
                .withStyle(item -> "--lumo-border-radius: " + 10 * (item.id % 3) + "px").build())
                .setHeader("Dynamic configuration").setWidth("250px");

        add(grid);
    }
}
