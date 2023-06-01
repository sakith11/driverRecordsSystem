package com.example.cw2_4_5;

import javafx.event.ActionEvent;

public class menuController {
    public void onCloseClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.positions);
    }

    public void onAddClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.edit);
    }

    public void onPosClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.positions);
    }

    public void onSimulateClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.simulate);
    }

    public void onInfoClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.information);}

    public void onConstructoClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.constructor);
    }

    public void onExportClick(ActionEvent event)
    {
        paneNavigator.loadPane(paneNavigator.Export);
    }
}
