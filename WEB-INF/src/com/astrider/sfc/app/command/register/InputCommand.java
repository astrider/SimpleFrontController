package com.astrider.sfc.app.command.register;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.annotation.Title;

@Title("新規登録")
public class InputCommand extends Command {
    /*
     * 新規登録フォーム表示
     */
    @Override
    public void doGet() throws ServletException, IOException {
        render();
    }
}
