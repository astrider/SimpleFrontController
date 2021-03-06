package com.astrider.sfc.app.command.internal;

import java.io.IOException;

import javax.servlet.ServletException;

import com.astrider.sfc.app.model.InternalModel;
import com.astrider.sfc.lib.Command;
import com.astrider.sfc.lib.helper.AuthUtils;

public class DailyBatchCommand extends Command {
    private final String authToken = "cc1f93c9fa2628492fc79a266abb2a40c61c4a91b6c824bf4d62f49d7b9f20b7efd"
            + "186854c4eda0363640318986b8e3683585bff769babd182560b52d1c7d1c898ffdb838a";

    @Override
    public void doGet() throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        if (id.equals("admin") && AuthUtils.verify(password, authToken)) {
            InternalModel model = new InternalModel();
            model.execDailyBatch();
        }
    }
}
