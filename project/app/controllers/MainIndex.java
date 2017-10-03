package controllers;

import core.controller.HtmlControllerBase;

public final class MainIndex extends HtmlControllerBase {

    public static void index() {
        render("index");
    }

}