package com.astrider.sfc.command;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("存在しないページ")
public class UnknownCommand extends Command {

    @Override
    protected void doGet() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        render("/Unknown.jsp");
    }

}
